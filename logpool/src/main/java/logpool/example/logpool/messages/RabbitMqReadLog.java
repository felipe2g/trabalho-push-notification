package logpool.example.logpool.messages;

import logpool.example.logpool.models.dto.LogDTO;
import logpool.example.logpool.services.LogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReadLog {
    @Value("${news.rabbitmq.exchange}")
    private String exchange;

    @Value("${news.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${news.rabbitmq.queue}")
    private String queue;

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    private LogService logService;

    @RabbitListener(queues = "${news.rabbitmq.queue}")
    public void receiveLog(@Payload LogDTO logDTO) {
        createQueue();
        if(logDTO != null)
            logService.save(logDTO);
    }

    public void createQueue() {
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare(exchange, "direct", true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, routingKey);
            return null;
        });
    }
}