package client;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

    private static final long serialVersionUID = 1L;
    private String username;
    private String message;
    private Date date;


    public Message(String username, String message){
        this.message = message;
        this.username = username;
    }

    public Message(String username, String message, Date date){
        this.message = message;
        this.username = username;
        this.date = date;
    }

    public String getUsername(){
        return username;
    }

    public String getMessage(){
        return message;
    }

    public Date getDate(){
        return date;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
