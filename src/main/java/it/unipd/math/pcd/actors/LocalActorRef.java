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
 * @author Matteo Di Pirro
 * @version 1.0
 * @since 1.0
 */

package it.unipd.math.pcd.actors;

import java.util.concurrent.ThreadFactory;

/**
 * LocalActorRef represents a reference to a local Actor.
 * @author Matteo Di Pirro
 * @version 1.0
 *
 * @param <T> Generic type that extends Message. It represents the type of the
 *            message that the actor can receive.
 */
public class LocalActorRef<T extends Message> implements ActorRef<T>{
	/**
	 * The ActorSystem that stores the {@link ActorRef}s/{@link Actor}s pairs.
	 */
	private final AbsActorSystem actorSystem;
	
	/**
	 * Constructor that creates a reference to a ActorMode.LOCAL actor.
	 * @param actorSystem A reference to the ActorSystem which manage the actor.
	 */
	public LocalActorRef(final AbsActorSystem actorSystem) {
		this.actorSystem = actorSystem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public final int compareTo(final ActorRef actorRef) {
		return this.toString().compareTo(actorRef.toString());
	}

	/* (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.ActorRef#send(it.unipd.math.pcd.actors.Message, it.unipd.math.pcd.actors.ActorRef)
	 */
	@Override
	public final void send(final T message, final ActorRef to) {
		final Actor<?> actor = actorSystem.getActorByRef(to);
		if (actor instanceof AbsActor<?>) {
			((AbsActor<T>) actor).inbox(message, (LocalActorRef<T>) this);
		}
	}

	protected final void execute(final Runnable task) {
		if (actorSystem instanceof ConcreteActorSystem) {
			((ConcreteActorSystem) actorSystem).execute(task);
		}
	}
}
