package Backend.P2PServer;


import java.io.Serializable;

/**
 * A Plain Old Java Object (POJO) for facilitating serializing and de-serializing a message object.
 */

public class Message implements Serializable {
    /**
     * The ID of the originator of the message
     */
    private String senderId;

    /**
     * The actual message to be delivered
     */
    private Object message;

    /**
     * No-argument constructor required for serialization and deserialization operations
     */
    public Message() {
    }


    /**
     * Creates a message object
     * @param senderId The originating node's ID
     * @param message The message content
     */
    public Message(String senderId, Object message) {
        this.senderId = senderId;
        this.message = message;
    }

    /**
     * Gets the sending node's ID
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Gets the message object
     * @return The message
     */
    public Object getMessage() {
        return message;
    }

    /**
     * The node ID of the receiver
     * @return Node ID
     */

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", message=" + message.toString() +
                '}';
    }
}
