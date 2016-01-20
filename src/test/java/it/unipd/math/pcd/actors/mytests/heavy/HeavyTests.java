package it.unipd.math.pcd.actors.mytests.heavy;

import it.unipd.math.pcd.actors.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matteo on 15/01/16.
 */
public class HeavyTests {
    @Test
    public void shouldCompleteTheMessageProcessingProcess() {
        ActorSystem actorSystem = new ConcreteActorSystem();
        final TestActorRef ref = new TestActorRef(actorSystem.actorOf(HeavyActor.class));
        HeavyActor actor = (HeavyActor) ref.getUnderlyingActor(actorSystem);
        for (int i=0; i<10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ref.send(new HeavyMessage(), ref);
                }
            }).start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actorSystem.stop(ref);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.out.println(actor.counter);
        Assert.assertTrue(actor.counter == 10000);

        Assert.assertTrue(actor.getMailboxSize() == 0);
    }
}
