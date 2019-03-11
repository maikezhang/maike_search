package cn.idongjia.divine.task.taskRunner;

import cn.idongjia.divine.timer.LiveUVUpdateTimer;
import cn.idongjia.divine.timer.ZooMessageUpdateTimer;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.task.core.domain.Task;
import cn.idongjia.task.executor.annotation.TaskRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/2
 * Time: 上午11:17
 */
@Component
@TaskRunner
public class TaskRunnerManager {


    private static final Log LOGGER = LogFactory.getLog(TaskRunnerManager.class);

    private static final String AUTO_UPDATE_ZOO_MESSAGE = "auto_update_zoo_message";
    private static final String AUTO_UPDATE_LIVE_UV = "auto_update_live_uv";

    @Resource
    private ZooMessageUpdateTimer zooMessageUpdateTimer;

    @Resource
    private LiveUVUpdateTimer liveUVUpdateTimer;


    @TaskRunner(taskType = AUTO_UPDATE_ZOO_MESSAGE)
    public void updateZooMessage(Task task) {
        LOGGER.info("executor update zooMessage .....");
        zooMessageUpdateTimer.update();
    }

    @TaskRunner(taskType = AUTO_UPDATE_LIVE_UV)
    public void updateLiveUV(Task task) {
        LOGGER.info("executor update live uv .....");
        liveUVUpdateTimer.update();
    }

}
