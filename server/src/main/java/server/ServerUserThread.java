package server;

import client.Message;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ServerUserThread extends Thread
{
    private String userName;
    private Socket user;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectInputStream ObjectIn;
    private String serverMessage;

    /**
     * Constructor with nickname initialization
     * @param user socket
     * @param server server socket
     */
    ServerUserThread (Socket user, Server server) throws IOException
    {
        this.user = user;
        this.server = server;

        InputStream input = user.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8));
        ObjectIn = new ObjectInputStream(user.getInputStream());
        writer = new PrintWriter(new OutputStreamWriter(user.getOutputStream(), StandardCharsets.UTF_8),true);

        while(true){
            userName = reader.readLine();
            if (server.getUserNames().contains(userName)){
                writer.println("0");
            } else {
                writer.println("1");
                break;
            }
        }
        server.addUserName(userName);
        System.out.println("User " + userName + " is connected.");
        server.messageHistory(this);
        serverMessage = "[" + server.getServerTime() + "]" + " New user connected: " + userName;
        server.broadcast(serverMessage, this);
    }

    /**
     * Method to receive client's messages
     */
    public void run()
    {
        try {
            String[] words;
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");

            while(true){
                try{
                    Message message = (Message) ObjectIn.readObject();
                    if (message.getMessage().equals("/exit")){
                        break;
                    }
                    if ((message.getMessage().charAt(0) == '/')&&(message.getMessage().charAt(1) == 'w')){
                        words = message.getMessage().split(" ");
                            if (server.getUserNames().contains(words[1])){
                            StringBuilder builder = new StringBuilder(message.getMessage());
                            message.setMessage(builder.substring(4 + words[1].length()));
                            serverMessage = "[" + dateFormat.format(message.getDate()) + "]" + "[" + userName + "][Private]: " + message.getMessage();
                            server.sendPrivate(serverMessage, words[1]);
                        } else {
                            writer.println("User with that nickname does not exist");
                        }
                    } else {
                        serverMessage = "[" + dateFormat.format(message.getDate()) + "]" + "[" + userName + "]: " + message.getMessage();
                        server.broadcast(serverMessage, this);
                    }
                } catch (ClassNotFoundException ex){
                    System.out.println("Class not found");
                }


            }
            server.removeUser(userName, this);
            user.close();
            serverMessage = "[" + server.getServerTime() + "] " + userName + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            server.removeUser(userName, this);
            serverMessage = "[" + server.getServerTime() + "] " + userName + " has quitted.";
            server.broadcast(serverMessage, this);
        }
    }

    /**
     * @return string user name
     */
    String getUserName()
    {
        return userName;
    }

    /**
     * Sends a message to the client.
     */
    public void sendMessage(String message)
    {
        writer.println(message);
    }
}
