package cn.idongjia;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:disconf.xml","classpath*:mybatis.xml",
                  "classpath*:consumer.xml","classpath*:provider.xml","classpath*:elastic.xml","classpath:kafka.xml","classpath:redis.xml","classpath:task.xml"})
@MapperScan(basePackages="cn.idongjia.divine.biz.db.mybatis.mapper")
@EnableAspectJAutoProxy
@Slf4j
public class TestDivineBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDivineBizApplication.class,args);
		log.info("启动成功");
		log.info("divine started");
    }


}
