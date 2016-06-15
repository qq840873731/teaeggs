package org.sauceggplant.thread;

import org.sauceggplant.entity.Task;

import java.util.LinkedList;
import java.util.List;

/** 生产线程   */
public class ProducerThread extends Thread {

    private MainThread mainThread;

    public ProducerThread(MainThread mainThread){
        this.mainThread = mainThread;
    }

    public void run(){
        while(true){
            while(this.mainThread.isFlag()){
                if(this.mainThread.getTaskList().size()<MainThread.LOW_NEED_ADD_TASK_COUNT){
                    addTask();
                }else{
                    try {
                        Thread.sleep(100);//每隔1秒时间判断食物（任务）过少，需要添加
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(10);//暂停任务后继续的等待时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //添加任务,模拟从数据库或者文件取数据
    public void addTask(){
        List taskList = new LinkedList();
        for(int i=0;i<MainThread.EACH_ADD_TASK_COUNT;i++){
            Task task = new Task();
            task.setId(""+this.mainThread.cursor++);
            task.setNum(this.mainThread.cursor);
            taskList.add(task);
        }
        this.mainThread.getTaskList().addAll(taskList);
    }
}
