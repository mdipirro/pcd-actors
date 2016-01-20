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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A map-based implementation of the actor system.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActorSystem implements ActorSystem {

    /**
     * Associates every Actor created with an identifier.
     */
    private final Map<ActorRef<?>, Actor<?>> actors;
    
    /**
     * Lock which manages the access to the Actor's map.
     */
    private final Lock lock;

    /**
     * Creates an ActorSystem.
     */
    public AbsActorSystem() {
    	actors 	= new ConcurrentHashMap<ActorRef<?>, Actor<?>>();
    	lock	= new ReentrantLock();
    }
    
    /**
     * Create an instance of {@code actor} returning a
     * {@link ActorRef reference} to it using the given {@code mode}.
     *
     * @param actor The type of actor that has to be created
     * @param mode The mode of the actor requested
     *
     * @return A reference to the actor
     */
    public final ActorRef<? extends Message> actorOf(final Class<? extends Actor> actor, final  ActorMode mode) {

        // ActorRef instance
        ActorRef<?> reference;
        try {
            // Create the reference to the actor
            reference = this.createActorReference(mode);
            // Create the new instance of the actor
            final Actor actorInstance = ((AbsActor) actor.newInstance()).setSelf(reference);
            // Associate the reference to the actor
            actors.put(reference, actorInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new NoSuchActorException(e);
        }
        return reference;
    }

    /*
     * (non-Javadoc)
     * @see it.unipd.math.pcd.actors.ActorSystem#actorOf(java.lang.Class)
     */
    @Override
    public final ActorRef<? extends Message> actorOf(final Class<? extends Actor> actor) {
        return this.actorOf(actor, ActorMode.LOCAL);
    }

    /**
     * Creates an ActorRef which references an Actor. 
     * @param mode The ActorMode of the Actor which will be created.
     * @return A reference ({@link ActorRef}) to the Actor.
     */
    protected abstract ActorRef<?> createActorReference(ActorMode mode);
    
    /**
     * Takes an ActorRef in input and returns the Actor referenced by the ActorRef.
     * @param reference The ActorRef reference.
     * @return The Actor which is referenced by the ActorRef.
     */
    public final Actor<? extends Message> getActorByRef(final ActorRef<?> reference) {
    	Actor actor = actors.get(reference);
		if (actor != null) {
			return actor;
		}
		throw new NoSuchActorException("This ActorRef doesn't correspond to an Actor.");
    }

    /**
     * Return all the references stored by the Actor System.
     * @return A Set that contains all the ActorRefs.
     */
    protected final Set<ActorRef<?>> getReferences() {
    	return actors.keySet();
	}

    protected final void remove (ActorRef actor) {
        actors.remove(actor);
    }
}
