package cn.itcast.mq.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringAMQPTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送对象，消息转换器
    @Test
    public void testObjectMessage() {
        String queueName = "objectMap.queue";
        Map<Object,String> message=new HashMap<>();
        message.put("姓名","玉");
        message.put("年龄","20");
        message.put("umr","xm");
        rabbitTemplate.convertAndSend(queueName, message);
    }


    //Topic主题交换机
    //使用通配符选择路由策略
    @Test
    public void testSendTopicExchange(){
        //交换机名称
        String exchangeName="itcast.topic";

        String  routingKey="china.news";
//        String  routingKey="japan.news";
        //消息
        String message="hello,"+routingKey;
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
    }

    //Direct路由交换机
    //一次发送，工具路由key选择队列接收
    @Test
    public void testSendDirectExchange(){
        //交换机名称
        String exchangeName="itcast.direct";

//        String  routingKey="yellow";
        String  routingKey="red";
        //消息
        String message="hello,"+routingKey;
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
    }

    //Fanout交换机，广播，一个交换就绑定多个队列
    //一次发送，多个队列共同接收
    @Test
    public void testSendFanoutExchange(){
        //交换机名称
        String exchangeName="itcast.fanout";
        //消息
        String message="hello,everyone";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,"",message);
    }


    //工作消息队列
    @Test
    public void testSendWorkQueueMessage() throws InterruptedException {
        String queueName = "simple2.queue";
        String message = "hello, spring amqp";

        for (int i = 0; i < 50; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, message+i);
            Thread.sleep(20);
        }

    }

//    @Autowired(required = false)
//    private RabbitAdmin rabbitAdmin;

    //基本消息队列
    @Test
    public void testSendMessage2() {
        String queueName = "simple2.name";
        String message = "hello, spring amqp";

        // 声明队列
//        rabbitAdmin.declareQueue(new Queue(queueName));

        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }



}