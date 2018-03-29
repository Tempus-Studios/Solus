package com.tempus.solus.util.thread;

import com.tempus.solus.util.thread.IDAssignment;
import com.tempus.solus.util.thread.PooledThread;

import java.util.List;
import java.util.LinkedList;

public class ThreadPool extends ThreadGroup {

    private boolean threadPoolAlive;
    private List<Runnable> taskQueue;
    private static IDAssignment poolID = new IDAssignment(1);
    private int id;

    public ThreadPool(int numThreads) {
        super("ThreadPool");
        this.id = poolID.getCurrentID();
        threadPoolAlive = true;
        setDaemon(true);
        taskQueue = new LinkedList<Runnable>();
        for (int i = 0; i < numThreads; i++) {
            new PooledThread(this).start();
        }
    }

    public synchronized void runTask(Runnable task) {
        if (!threadPoolAlive) {
            throw new IllegalStateException("Thread Pool-" + id + " is dead");
        }
        if (task != null) {
            taskQueue.add(task);
            notify();
        }
    }

    public synchronized void close() {
        if(!threadPoolAlive) {
            return;
        }
        threadPoolAlive = false;
        taskQueue.clear();
        interrupt();
    }

    public void join() {
        synchronized (this) {
            threadPoolAlive = false;
            notifyAll();
        }
        Thread[] threads = new Thread[activeCount()];
        int threadCount = enumerate(threads);

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected synchronized Runnable getTask() throws InterruptedException {
        while(taskQueue.size() == 0) {
            if(!threadPoolAlive) {
                return null;
            }
            wait();
        }
        return taskQueue.remove(0);
    }
}
