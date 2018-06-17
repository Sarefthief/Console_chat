package client;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClientReader extends Thread
{
    private Client client;
    private Socket socket;
    private PrintStream ps;
    private ObjectInputStream ObjectIn;

    /**
     * @param socket socket
     * @param client client
     */
    public ClientReader(Socket socket, Client client) {
        this.client = client;
        this.socket = socket;

        try {
            ps = new PrintStream(System.out, true,"UTF-8");
            ObjectIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to receive messages from server
     */
    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        while (true) {
            try {
                Message message = (Message) ObjectIn.readObject();
                String response = "[" + dateFormat.format(message.getDate()) + "]" + "[" + message.getUsername() + "]: " + message.getMessage();
                ps.println("\r" + response);
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    ps.print("\r[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("\rChat is closed");
                    try{
                    socket.close();
                } catch (IOException ex2) {
                    System.out.println("IO exception");
                }
                break;
            } catch (ClassNotFoundException ex2){
                System.out.println("Class not found");
                break;
            }
        }
    }
}