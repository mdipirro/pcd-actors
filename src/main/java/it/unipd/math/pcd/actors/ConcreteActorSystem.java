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

import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;

import java.util.concurrent.*;

/**
 * @author 	Matteo Di Pirro
 * @version 1.0
 *
 */
public final class ConcreteActorSystem extends AbsActorSystem {

	/**
	 * The Executor which manages the Actors.
	 */
	private Executor executor;

	public ConcreteActorSystem () {
		executor = Executors.newCachedThreadPool();
	}

	/* (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.AbsActorSystem#createActorReference(it.unipd.math.pcd.actors.ActorSystem.ActorMode)
	 */
	@Override
	protected ActorRef createActorReference(final ActorMode mode) {
		if (mode == ActorMode.LOCAL) {
			return new LocalActorRef(this);
		}
		throw new IllegalArgumentException();
	}

	/*
     * (non-Javadoc)
     * @see it.unipd.math.pcd.actors.ActorSystem#stop(java.lang.Class)
     */
	@Override
	public void stop(final ActorRef<?> actor) {
		((AbsActor) getActorByRef(actor)).stop();
		remove(actor);
		System.out.println("Actor removed");
	}

	/*
     * (non-Javadoc)
     * @see it.unipd.math.pcd.actors.ActorSystem#stop()
     */
	@Override
	public void stop() {
		for (ActorRef actor : getReferences()) {
			stop(actor);
		}
	}

	/**
	 * Return the ThreadFactory that
	 * @return
     */
	protected void execute(final Runnable task) {
		executor.execute(task);
	}
}
