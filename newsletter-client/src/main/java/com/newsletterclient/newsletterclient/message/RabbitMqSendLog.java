package com.newsletterclient.newsletterclient.message;

import com.newsletterclient.newsletterclient.models.dtos.LogDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSendLog {
    @Value("${news.rabbitmq.exchange}")
    private String exchange;

    @Value("${news.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${news.rabbitmq.queue}")
    private String queue;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSendLog(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLog(LogDTO logDTO) {
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare(exchange, "direct", true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, routingKey);
            return null;
        });
        rabbitTemplate.convertAndSend(exchange, routingKey, logDTO);
    }
}
