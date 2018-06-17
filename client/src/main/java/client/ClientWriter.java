package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriter extends Thread
{
    private Socket socket;
    private Client client;
    private ObjectOutputStream ObjectOut;
    private PrintStream ps;

    /**
     * Constructor with nickname initialization
     * @param socket socket
     * @param client client
     */
    public ClientWriter(Socket socket, Client client) throws UnsupportedEncodingException {
        this.socket = socket;
        this.client = client;

        try {
            ObjectOut = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            Scanner input = new Scanner(System.in,"UTF-8");
            ps = new PrintStream(System.out, true, "UTF-8");
            String userName;
            while(true){
                System.out.print("Enter your nickname: ");
                userName = input.nextLine();
                writer.println(userName);
                String check = reader.readLine();
                if (check.equals("0")){
                    System.out.println("That nickname is occupied.");
                } else {
                    break;
                }
            }
            client.setUserName(userName);
            System.out.println("To send private message, use the following example: \"/w Nickname YourText\".");
            System.out.println("To exit chat type \"/exit\".");
            System.out.println("__________________________");
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
        }
    }

    /**
     * Method to write messages
     */
    public void run() {
        Scanner input = new Scanner(System.in,"UTF-8");
        String text;
        do {
            ps.print("\r[" + client.getUserName() + "]: ");
            text = input.nextLine();
            try{
                if (!text.equals("")){
                    Message message = new Message(client.getUserName(), text);
                    ObjectOut.writeObject(message);
                }
            } catch (IOException ex){
                System.out.println("\rChat is closed");
            }
        } while ((!text.equals("/exit"))&&(!socket.isClosed()));
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("\rChat is closed");
        }
    }
}
