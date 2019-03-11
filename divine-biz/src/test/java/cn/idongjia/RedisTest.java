package cn.idongjia;

import cn.idongjia.divine.DivineBizApplication;
import cn.idongjia.divine.factor.UserFavorFactor;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author   单红宇(365384722)
 * @myblog  http://blog.csdn.net/catoop/
 * @create    2016年2月23日
 */
@SpringBootTest(classes = DivineBizApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)

public class RedisTest {

    @Autowired
    private UserFavorFactor userFavorFactor;

    @Test
    public void likeName() {
        UserFavor userFavor = userFavorFactor.getUserFavor(839280L,null,1);
        System.out.println("userFavor==》"+userFavor);
    }

}
