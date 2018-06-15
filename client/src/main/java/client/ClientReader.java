package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientReader extends Thread
{
    private BufferedReader reader;
    private Client client;
    private Socket socket;

    /**
     * @param socket socket
     * @param client client
     */
    public ClientReader(Socket socket, Client client) {
        this.client = client;
        this.socket = socket;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
        }
    }

    /**
     * Method to receive messages from server
     */
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\r" + response);

                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("\r[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("\rChat is closed");
                try{
                    socket.close();
                } catch (IOException ex2) {
                    System.out.println("IO exception");
                }
                break;
            }
        }
    }
}