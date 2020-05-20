package com.example.finalsemprojectbuyer.Data;

public class IndividualMessage
{
    private String sender;
    private String receiver;
    private String message;
    private Boolean isSeen;

    public IndividualMessage(String sender, String receiver, String message, Boolean isSeen)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isSeen = isSeen;
    }

    public IndividualMessage()
    {

    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Boolean getisSeen()
    {
        return isSeen;
    }

    public void setisSeen(Boolean seen)
    {
        isSeen = seen;
    }
}
