package com.tempus.solus.util.thread;

public class PooledThread extends Thread {
    private ThreadPool pool;
    private static IDAssignment threadID = new IDAssignment(1);
    public PooledThread(ThreadPool pool) {
        super(pool, "PooledThread" + threadID.next());
        this.pool = pool;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            Runnable task = null;
            try {
                 task = pool.getTask();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (task == null) {
                return;
            }
            try {
                task.run();
            } catch (Throwable throwable) {
                pool.uncaughtException(this, throwable);
            }
        }
    }
}
