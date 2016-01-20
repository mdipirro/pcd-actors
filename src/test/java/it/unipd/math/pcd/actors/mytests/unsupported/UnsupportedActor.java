package it.unipd.math.pcd.actors.mytests.unsupported;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;
import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;

/**
 * Created by matteo on 15/01/16.
 */
public class UnsupportedActor extends AbsActor<Message> {
    @Override
    public void receive(Message message) {
        throw new UnsupportedMessageException(message);
    }
}
