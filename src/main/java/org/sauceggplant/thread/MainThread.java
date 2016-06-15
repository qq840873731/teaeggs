package org.sauceggplant.thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**主线程*/
public class MainThread extends Thread {

    //线程列表
    private List threadList = new ArrayList();

    //任务列表
    private List TaskList = new LinkedList();

    //任务ID列表，标识执行中任务
    private List TaskIdList = new ArrayList();

    //每次轮询增加任务数量
    public static final long EACH_ADD_TASK_COUNT = 100;

    //任务数量小于多少时，轮询线程添加任务
    public static final int LOW_NEED_ADD_TASK_COUNT = 10;

    //消费线程数量
    public static final int CONSUMER_THREAD_COUNT = 10;

    //生产者线程
    private ProducerThread producerThread;

    //消费者线程列表
    private List consumerThreads = new ArrayList();

    //主线程停止标识
    private boolean flag = false;

    //任务游标
    public static long cursor = 0;

    public MainThread(boolean flag){
        this.flag = flag;
    }

    public void run(){
        try{

            //生产者线程初始化
            producerThread = new ProducerThread(this);
            producerThread.join();
            producerThread.start();
            //消费者线程初始化
            for(int i=0;i<CONSUMER_THREAD_COUNT;i++){
                ConsumerThread consumerThread = new ConsumerThread(this,i);
                consumerThreads.add(consumerThread);
                consumerThread.join();
                consumerThread.start();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //setters and getters
    public List getThreadList() {
        return threadList;
    }

    public void setThreadList(List threadList) {
        this.threadList = threadList;
    }

    public List getTaskList() {
        return TaskList;
    }

    public void setTaskList(List taskList) {
        TaskList = taskList;
    }

    public List getTaskIdList() {
        return TaskIdList;
    }

    public void setTaskIdList(List taskIdList) {
        TaskIdList = taskIdList;
    }

    public ProducerThread getProducerThread() {
        return producerThread;
    }

    public void setProducerThread(ProducerThread producerThread) {
        this.producerThread = producerThread;
    }

    public List getConsumerThreads() {
        return consumerThreads;
    }

    public void setConsumerThreads(List consumerThreads) {
        this.consumerThreads = consumerThreads;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}