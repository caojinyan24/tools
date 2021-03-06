package swa.invoker.dubbo.invoke;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.invoker.dubbo.subscribe.RegistryContainer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class InvokerService {
    private static final Logger logger = LoggerFactory.getLogger(InvokerService.class);
    @Resource
    private RegistryContainer registryContainer;

    @PostConstruct
    public void setUp() {
        logger.info("consumers:{}", registryContainer.getServiceConsumers());
        logger.info("providers:{}", registryContainer.getServiceProviders());

    }

    public void invoke(String serviceName, String methodName, String param) throws ClassNotFoundException {
        //根据servicename从Container中获取
        //远程调用
        List<URL> urls = RegistryContainer.getInstance().getProvidersByService(serviceName);
        Protocol protocol = DubboProtocol.getDubboProtocol();
        for (URL url : urls) {
            Invoker invoker = protocol.refer(Class.forName(serviceName).getClass(), url);
            invoker.invoke(new RpcInvocation());
        }

    }
}
