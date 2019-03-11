package cn.idongjia.divine.timer;

import cn.idongjia.task.common.task.ExcuteType;
import cn.idongjia.task.common.task.FaultType;
import cn.idongjia.task.common.task.Task;
import cn.idongjia.task.common.task.Type;

import java.util.Map;

/**
 * Created by zhang on 2017/6/5.
 * 封装定时任务接口
 */
public abstract class BaseTaskManager {


//    static final String TASK_EXECUTOR = "DivineTaskExecutor";
//
//
//    protected Task assembleRepeatTask(Map<String, String> params, String taskType, String taskId, Long timeStamp,
//            Long interval, FaultType faultType) {
//        Task task = new Task();
//        task.setExcuteType(ExcuteType.JAVA);
//        task.setExtParams(params);
//        task.setExuterName(TASK_EXECUTOR);
//        task.setFaultType(faultType);
//        task.setRepeatedCount(-1);
//        task.setTaskType(taskType);
//        interval = interval * 1000;
//        task.setTriggerTime(timeStamp);
//        task.setTaskId(taskId);
//        task.setType(Type.REPEAT);
//        task.setRepeatInterval(interval);
//        return task;
//    }



}
