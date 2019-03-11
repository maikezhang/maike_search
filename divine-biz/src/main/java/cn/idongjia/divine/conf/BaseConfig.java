package cn.idongjia.divine.conf;

import cn.idongjia.divine.annotation.ElapsedHandler;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import com.alibaba.dubbo.remoting.http.servlet.BootstrapListener;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Configuration
public class BaseConfig {

    private static final Log logger = LogFactory.getLog(BaseConfig.class);

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }

    @Bean
    public BootstrapListener getBootstrapListener() {
        return new BootstrapListener();
    }


    @Bean
    public ElapsedHandler elapsedHandler() {
        return new ElapsedHandler();
    }

}

