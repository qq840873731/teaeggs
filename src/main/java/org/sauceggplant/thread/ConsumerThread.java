package org.sauceggplant.thread;

import org.sauceggplant.entity.Task;

/** 消费线程 */
public class ConsumerThread extends Thread {

    // 线程号
    private int number;

    // 是否饥饿
    private boolean hungry = true;

    private MainThread mainThread;

    public ConsumerThread(MainThread mainThread, int number) {
        this.mainThread = mainThread;
        this.number = number;
    }

    public void run() {
        while (true) {
            while (this.mainThread.isFlag()) {// 任务是否执行
                if (hungry) {// 线程饿了
                    String taskId = null;
                    synchronized (this.mainThread.getTaskIdList()) {
                        if (this.mainThread.getTaskList().size() > 0
                                && this.mainThread.getTaskList().get(0) != null) {
                            if (!this.mainThread.getTaskIdList().contains(
                                    ((Task) (this.mainThread.getTaskList()
                                            .get(0))).getId())) {
                                hungry = false;
                                System.out.println("Comsumer:\t"
                                        + number
                                        + "\tTaskNum:\t"
                                        + ((Task) (this.mainThread
                                        .getTaskList().get(0)))
                                        .getNum() + "\tTime:\t"
                                        + System.currentTimeMillis());
                                taskId = ((Task) (this.mainThread.getTaskList()
                                        .get(0))).getId();
                                this.mainThread.getTaskIdList().add(taskId);
                                this.mainThread.getTaskList().remove(0);
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                    try {
                        Thread.sleep(5);// 模拟执行任务
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.mainThread.getTaskIdList().remove(taskId);
                    hungry = true;
                } else {
                    try {
                        Thread.sleep(5);// 没有吃的就等一会
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(5);// 每次点击继续执行程序等待5秒钟时间
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}