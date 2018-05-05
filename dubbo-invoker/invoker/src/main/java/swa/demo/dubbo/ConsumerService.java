package swa.demo.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swa.ProviderService;


public class ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        // obtain proxy object for remote invocation
        ProviderService providerService = (ProviderService) context.getBean("providerService");
        // execute remote invocation
        providerService.doJob();
        // show the result
    }
}
