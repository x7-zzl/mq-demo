package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author nightmare
 * @date 2023/7/12 17:02
 */
@Component
public class SpringRabbitListener {

    //路由选择，DirectExchange交换机
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "itcast.direct",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}  //路由选择，绑定red,blue
    ))
    public void listenDirectQueue1(String msg){
        System.out.println("消费者接收到direct.queue1的消息：["+msg+"]");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "itcast.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}  //路由选择，绑定red,yellow
    ))
    public void listenDirectQueue2(String msg){
        System.out.println("消费者接收到direct.queue2的消息：["+msg+"]");
    }

    //广播
    //Fanout,发布订阅，一个交换机绑定两个队列
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者接收到fanout.queue1的消息：["+msg+"]");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者接收到fanout.queue2的消息：["+msg+"]");
    }


    //消费者1和2共同接收消息
    //工作队列，消费者1
    @RabbitListener(queues = "simple2.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException{
        System.out.println("消费者zzl接收到simple.queue的消息：["+msg+"]"+LocalDateTime.now());
        Thread.sleep(20);
    }

    //工作队列，消费者2
    @RabbitListener(queues = "simple2.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException{
        //红色打印
        System.err.println("消费者nightmare接收到simple.queue的消息：["+msg+"]"+ LocalDateTime.now());
        Thread.sleep(70);
    }

//    @RabbitListener(queues = "simple3.queue")
//    public void listenSimpleQueue(String msg) throws InterruptedException{
//        System.out.println("消费者接收到simple.queue的消息：["+msg+"]");
//    }
}
