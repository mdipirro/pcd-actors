package it.unipd.math.pcd.actors.mytests.counter;

import it.unipd.math.pcd.actors.AbsActor;

/**
 * Created by matteo on 15/01/16.
 */
public class CounterActor extends AbsActor<CounterMessage> {

    private int counter = 0;

    @Override
    public void receive(CounterMessage message) {
        if (message instanceof Increment) {
            counter++;
        } else if (message instanceof Decrement) {
            counter--;
        }
    }

    public int getCounter() {
        return counter;
    }
}
