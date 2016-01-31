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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 	Matteo Di Pirro
 * @version 1.0
 * Queue based container that stores al the messages of an actor.
 * @param <T> Generic type that extends Message. It represents the type of the
 *            message that the actor can receive.
 */
public final class FIFOMailBox<T extends Message> implements MailBox {
	/**
	 * Mailbox which contains non-also-processed messages. 
	 */
	private final BlockingQueue<MailBoxItem<T>> mailbox = new LinkedBlockingQueue<MailBoxItem<T>>();
	
	/*
	 * (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.MailBox#addMessage(it.unipd.math.pcd.actors.MailBox.MailBoxItem)
	 */
	@Override
	public final void addMessage(final MailBoxItem message) {
		try {
			mailbox.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.MailBox#getMessag()
	 */
	@Override
	public final MailBoxItem getMessage() {
		try {
			return mailbox.take();
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.unipd.math.pcd.actors.MailBox#isEmpty()
	 */
	@Override
	public final boolean isEmpty() {
		return mailbox.isEmpty();
	}
}
