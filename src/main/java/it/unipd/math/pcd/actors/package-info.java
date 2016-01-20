/**
 * The main abstract types of the system are the following:
 * <ul>
 *      <li>Actor: this type represents an actor, which can receive a
 *          message and react accordingly.</li>
 *      <li>Message: the message actors can send each others. A message
 *          should contain a reference to the sender actor.</li>
 *      <li>ActorRef: a reference to an instance of an actor. Using this
 *          abstraction it is possible to treat in the same way local actors
 *          and actors that execute remotely.</li>
 *      <li>ActorSystem: an actor system provides the utilities to create new
 *          instances of actors and to locate them.</li>
 * </ul>
 * This package contains these abstract types.
*/
package it.unipd.math.pcd.actors;
