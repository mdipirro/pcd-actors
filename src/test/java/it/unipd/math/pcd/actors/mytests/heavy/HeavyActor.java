package it.unipd.math.pcd.actors.mytests.heavy;

import it.unipd.math.pcd.actors.AbsActor;

/**
 * Created by matteo on 15/01/16.
 */
public class HeavyActor extends AbsActor<HeavyMessage> {

    protected int counter = 0;

    @Override
    public void receive(HeavyMessage message) {
        int c = ++counter;
        try {
            System.out.println("Begin" + c);
            Thread.sleep(2);
            System.out.println("End" + c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
