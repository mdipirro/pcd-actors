package it.unipd.math.pcd.actors.mytests.unsupported;

import it.unipd.math.pcd.actors.ActorSystem;
import it.unipd.math.pcd.actors.ConcreteActorSystem;
import it.unipd.math.pcd.actors.TestActorRef;
import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matteo on 15/01/16.
 */
public class UnsupportedTest {
    @Test (expected = UnsupportedMessageException.class)
    public void shouldStopTheActorAndThrowUnsupportedMessageException() {
        ActorSystem actorSystem = new ConcreteActorSystem();
        final TestActorRef ref = new TestActorRef(actorSystem.actorOf(UnsupportedActor.class));
        UnsupportedActor actor = (UnsupportedActor) ref.getUnderlyingActor(actorSystem);
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
