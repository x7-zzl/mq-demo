package cn.itcast.mq.helloworld;

/**
 * @author nightmare
 * @date 2023/7/12 16:23
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerEndpointRegistry endpointRegistry;

    private static final String QUEUE_NAME = "myQueue";



    @Test
    public void testSendMessage() {
        String message = "Hello, RabbitMQ!";
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        // 可以添加一些断言来验证消息发送是否成功
    }

    @Test
    public void testReceiveMessage() {
        // 发送消息到队列
        String message = "Hello, RabbitMQ!";
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        // 接收消息，并在一定时间内进行断言验证
        String receivedMessage = (String) rabbitTemplate.receiveAndConvert(QUEUE_NAME);
        // 添加断言来验证接收到的消息是否正确
    }

    @Test
    public void testMessageListener() throws Exception {
        // 在消息监听器中异步接收消息，并在一定时间内进行断言验证
    }

    @Test
    public void testStopMessageListener() {
        // 停止消息监听器
        endpointRegistry.getListenerContainer("myMessageListener").stop();
    }
}