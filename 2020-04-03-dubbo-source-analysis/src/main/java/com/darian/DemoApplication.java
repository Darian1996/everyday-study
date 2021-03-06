package com.darian;

import com.darian.dubbo.injvm.DubboInJvmConsumerService;
import com.darian.dubbo.onlyconsumer.DubboOnlyConsumerService;
import com.darian.dubbo.protocols.DubboProtocolsRemoteConsumerService;
import com.darian.dubbo.registries.DubboRegistriesConsumerService;
import com.darian.dubbo.remote.DubboRemoteConsumerService;
import lombok.Data;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableDubbo
@SpringBootApplication
@PropertySource("classpath:META-INF/spring/demo-context.properties")
@ImportResource({"classpath:META-INF/spring/*.xml"})
public class DemoApplication implements InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    static ScheduledExecutorService SCHEDULE = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

        SCHEDULE.scheduleAtFixedRate(() -> {
            try {
                DubboInJvmConsumerService dubboInJvmConsumerService = getBean(run, DubboInJvmConsumerService.class);

                if (Objects.nonNull(dubboInJvmConsumerService)) {
                    LOGGER.info(dubboInJvmConsumerService.say());
                }

                DubboRemoteConsumerService dubboRemoteConsumerService = getBean(run, DubboRemoteConsumerService.class);
                if (Objects.nonNull(dubboRemoteConsumerService)) {
                    LOGGER.info(dubboRemoteConsumerService.say());
                }

                DubboRegistriesConsumerService dubboRegistriesConsumerService = getBean(run, DubboRegistriesConsumerService.class);
                if (Objects.nonNull(dubboRegistriesConsumerService)) {
                    LOGGER.info(dubboRegistriesConsumerService.say());
                }

                DubboOnlyConsumerService dubboOnlyConsumerService = getBean(run, DubboOnlyConsumerService.class);
                if (Objects.nonNull(dubboOnlyConsumerService)) {
                    LOGGER.info(dubboOnlyConsumerService.say());
                }

                DubboProtocolsRemoteConsumerService dubboProtocolsRemoteConsumerService = getBean(run,
                        DubboProtocolsRemoteConsumerService.class);
                if (Objects.nonNull(dubboProtocolsRemoteConsumerService)) {
                    LOGGER.info(dubboProtocolsRemoteConsumerService.say());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);

    }

    private static <T> T getBean(ConfigurableApplicationContext applicationContext, Class<T> tClazz) {
        try {
            return applicationContext.getBean(tClazz);
        } catch (Exception e) {
            return null;
        }
    }

    @Value("${project.user.name}")
    private String name;

    @Resource
    private UserInfo userInfo;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info(userInfo.toString());
        LOGGER.info(name);
    }

    @Data
    public static class UserInfo {
        private String name;
    }
}

