package it.unipd.math.pcd.actors.mytests.counter;

import it.unipd.math.pcd.actors.ActorSystem;
import it.unipd.math.pcd.actors.ConcreteActorSystem;
import it.unipd.math.pcd.actors.TestActorRef;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matteo on 15/01/16.
 */
public class CounterTest {
    @Test
    public void shouldCompleteTheMessageProcessingProcess() {
        ActorSystem actorSystem = new ConcreteActorSystem();
        final TestActorRef ref = new TestActorRef(actorSystem.actorOf(CounterActor.class));
        CounterActor actor = (CounterActor) ref.getUnderlyingActor(actorSystem);
        for (int i=0; i<40000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ref.send(new Increment(), ref);
                }
            }).start();
        }
        for (int i=0; i<40000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ref.send(new Decrement(), ref);
                }
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {}
        Assert.assertTrue(actor.getCounter() == 0);
    }
}
