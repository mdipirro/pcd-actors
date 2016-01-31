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

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.Map;
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
	private final ExecutorService executor;

	/**
	 * A map used for associate an ActorRef to the Future used for wait his termination.
	 */
	private final Map<ActorRef<?>, Future<?>> futures;

	public ConcreteActorSystem () {
		executor = Executors.newCachedThreadPool();
		futures = new ConcurrentHashMap<ActorRef<?>, Future<?>>();
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
		AbsActor<?> stoppedActor = (AbsActor) getActorByRef(actor);
		Future<?> future = futures.remove(actor);
		remove(actor);
		if (stoppedActor == null || future == null) {
			throw new NoSuchActorException("The Actor has already been stopped!");
		}
		waitTermination(stoppedActor, future);
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
	 * Executes the task and initialize a Future for it.
	 * @param task The task which will be executed.
	 * @param actor The ActorRef associated with the task.
     */
	void execute(Callable<Void> task, ActorRef<?> actor) {
		futures.put(actor, executor.submit(task));
	}

	/**
	 * Stop the Actor and wait his termination.
	 * @param actor The Actor to stop.
	 * @param future The Future used to wait the termination of the Actor.
     */
	private void waitTermination(final AbsActor<?> actor, final Future<?> future) {
		actor.stop();
		try {
			if (future != null && !future.isDone()) {
				future.get(); // Let's wait the termination
			}
		} catch (InterruptedException | ExecutionException exc) {
			exc.printStackTrace();
		}
	}

	/*
     * (non-Javadoc)
     * @see Object#finalize()
     */
	@Override
	protected void finalize() throws Throwable {
		executor.shutdown();
		stop();
		super.finalize();
	}
}
