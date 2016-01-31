/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;

import java.util.concurrent.Callable;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 * @param <T> Generic type that extends Message. It represents the type of the
 *            message that the actor can receive.
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {
	/**
	 * This enum represents the internal state of the AbsActor.
	 */
	private enum ActorState {
		/**
		 * CREATED is used to indicate that the Actor has benn created,
		 * but no message has been sent to him.
		 */
		CREATED,
		/**
		 * RUNNING is used to indicate that the Actor is up and
		 * running.
		 * In other words, the message processing system has been
		 * started.
		 */
		RUNNING,
		/**
		 * STOPPED is used to indicate that the Actor has been stopped.
		 */
		STOPPED
	}

    /**
     * Self-reference of the actor.
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message.
     */
    protected ActorRef<T> sender;

	/**
     * The mailbox.
     */
    private final MailBox<T> mailbox;

	/**
	 * The internal state of the Actor.
	 */
	private volatile ActorState actorState;

	/**
	 * Constructor which starts the message receiving process.
	 */
	public AbsActor() {
		mailbox 	= new FIFOMailBox<T>();
		actorState 	= ActorState.CREATED;
		// other fields are set to null by the default initialization
	}
	
    /**
     * Sets the self-reference.
     *
     * @param self The reference to itself.
     * @return The actor.
     */
    protected final Actor<T> setSelf(final ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * Adds the message to the mailbox.
	 *
     * @param message The message which will be added.
     * @param sender The sender of the message.
	 * @throws NoSuchActorException If the Actor has already been stopped.
     */
    protected final void inbox(final T message, final ActorRef<T> sender) {
		synchronized (this) {
			// If the Actor has already been stopped throw a NoSuchActorException
			if (actorState == ActorState.STOPPED) {
				throw new NoSuchActorException("This Actor has been stopped!");
			}
			mailbox.addMessage(new Mail<T>(message, sender));
			// If the Actor isn't running, let's start it!
			if (actorState == ActorState.CREATED) {
				((LocalActorRef<T>) self).execute(new MessageManager());
				actorState = ActorState.RUNNING;
			}
		}
    }

    /**
     * Stops the message processing process.
	 *
	 * @throws NoSuchActorException If the Actor has already been stopped.
     */
    protected final void stop() {
		synchronized (this) {
			// If the Actor has already been stopped throws a NoSuchActorException
			if (actorState == ActorState.STOPPED) {
				throw new NoSuchActorException("This Actor has already been stopped!");
			}
			// Set the Actor's state to STOPPED.
			actorState = ActorState.STOPPED;
		}
	}

    /**
     * @author 	Matteo Di Pirro
     * @version 1.0
     *
     * Manages the message processing system.
     *
     */
    private class MessageManager implements Callable<Void> {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Callable#call()
		 */
    	@Override
    	public Void call() throws Exception{
			// The Actor is processing his messages.
			while (actorState == ActorState.RUNNING) {
				processMessage(mailbox.getMessage());
			}
			// The Actor has been stopped, so he's processing all his remaining messages.
			while (!mailbox.isEmpty()) {
				processMessage(mailbox.getMessage());
			}
			return null;
		}

		/**
		 * Processes a mail taken from the mailbox.
		 *
		 * @param mail The mail to process.
		 * @throws UnsupportedMessageException If the message is not supported by the Actor.
         */
		public final void processMessage (final MailBoxItem<T> mail) {
			synchronized (this) {
				sender = mail.getSender();
				receive(mail.getMessage());
			}
		}
    }
}
