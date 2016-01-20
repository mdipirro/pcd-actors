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

/**
 * Class that represents an item stored by the MailBox.
 * @author 	Matteo Di Pirro
 * @version 1.0
 */
public final class Mail<T extends Message> implements MailBoxItem<T> {
	/**
	 * The message which will be processed by the Actor.
	 */
	private final T message;

	/**
	 * The Actor which sent the message.
	 */
	private final ActorRef<T> sender;

	/**
	 * Creates an item stored in the Mailbox.
	 * @param message The message which will be processed by the Actor.
	 * @param sender The Actor which sent the message.
	 */
	public Mail(final T message, final ActorRef<T> sender) {
		this.message 	= message;
		this.sender		= sender;
	}

	/*
	 * (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.MailBoxItem#getMessage(it.unipd.math.pcd.actors.MailBoxItem)
	 */
	@Override
	public T getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.MailBoxItem#getSender(it.unipd.math.pcd.actors.MailBoxItem)
	 */
	@Override
	public ActorRef<T> getSender() {
		return sender;
	}
}