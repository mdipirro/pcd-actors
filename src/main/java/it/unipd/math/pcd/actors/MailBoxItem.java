package it.unipd.math.pcd.actors;

/**
 * Created by matteo on 14/01/16.
 */
public interface MailBoxItem<T extends Message> {
    /**
     * Return the message stored in the Mail.
     * @return The message.
     */
    public T getMessage();

    /**
     * Returns the sender of the message.
     * @return The sender.
     */
    public ActorRef<T> getSender();
}
