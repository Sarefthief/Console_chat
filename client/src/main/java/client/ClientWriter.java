package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

public class ClientWriter extends Thread
{
    private Socket socket;
    private Client client;
    private ObjectOutputStream ObjectOut;

    /**
     * Constructor with nickname initialization
     * @param socket socket
     * @param client client
     */
    public ClientWriter(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            ObjectOut = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8));
            Scanner input = new Scanner(System.in,StandardCharsets.UTF_8);
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

        Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);
        String text;
        do {
            System.out.print("\r[" + client.getUserName() + "]: ");
            text = input.nextLine();
            try{
                if (!text.equals("")){
                    Message message = new Message(client.getUserName(), text, new Date());
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
