package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    private String hostName;
    private int port;
    private String userName = "";
    /**
     * client.Client main method
     */
    public void start() {
        try {
            Socket socket = new Socket(hostName, port);
            ClientWriter writer = new ClientWriter(socket, this);
            writer.start();
            new ClientReader(socket, this).start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Server is unavailable, please wait or try to change server name and port number inside clientConfig.yml file.");
        }
    }

    /**
     * @param userName string name of user
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return string name of user
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * @return int port number
     */
    public int getPort()
    {
        return port;
    }

    /**
     * @param port int port number
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return string name of host
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * @param hostName string name of host
     */
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }
}
