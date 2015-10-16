package commands;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queue of commands.
 * @author Bart
 *
 */
public final class CommandQueue {
	

	private static volatile CommandQueue cq;
	private Queue<Command> queue;
	
	/**
	 * Return instance of RND.
	 * @return	the instance
	 */
	public static CommandQueue getInstance() {
	 	if (cq == null) {
	 		synchronized (CommandQueue.class) {
	 			if (cq == null) {
	 				cq = new CommandQueue();
	 			}
	 		}
	 	}
	 	
	 	return cq;
	}

	/**
	 * 
	 */
	private CommandQueue() {
		queue = new ConcurrentLinkedQueue<Command>();
	}
	
	/**
	 * Add a command to the queue.
	 * @param command	the command to add
	 */
	public synchronized void addCommand(Command command) {
		queue.add(command);
	}
	
	/**
	 * Execute all commands in the queue.
	 */
	public synchronized void executeQueue() {
		while (!queue.isEmpty()) {
			queue.poll().execute();
		}
	}
}
