
package com.darian.dubbo.remote;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.apache.dubbo.rpc.cluster.support.FailfastCluster;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("remote")
public class DubboRemoteConsumerService {

    @DubboReference(
            interfaceClass = DubboRemoteInterface.class,
            protocol = DubboProtocol.NAME,
            loadbalance = RandomLoadBalance.NAME,
            mock = "com.darian.dubbo.remote.MockService",
            timeout = 500,
            cluster = FailfastCluster.NAME,
            check = false,
            methods = {
                    @Method(name = "say", loadbalance = RandomLoadBalance.NAME)
            },
            retries = 2,
            injvm = false
    )
    private DubboRemoteInterface dubboRemoteInterface;

    public String say() {
        return dubboRemoteInterface.say();
    }
}

