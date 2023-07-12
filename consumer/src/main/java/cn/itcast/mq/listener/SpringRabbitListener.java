package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author nightmare
 * @date 2023/7/12 17:02
 */
@Component
public class SpringRabbitListener {


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
