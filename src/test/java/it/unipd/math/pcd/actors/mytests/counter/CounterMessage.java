package it.unipd.math.pcd.actors.mytests.counter;

import it.unipd.math.pcd.actors.Message;

/**
 * Created by matteo on 15/01/16.
 */
public class CounterMessage implements Message{}

class Increment extends CounterMessage {}

class Decrement extends CounterMessage {}