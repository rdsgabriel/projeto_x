package com.ophis.projectx.consumers;

import com.ophis.projectx.dto.EmailRecordDTO;
import com.ophis.projectx.entities.Email;
import com.ophis.projectx.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService service;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO emailDTO){
        var email= new Email();
        BeanUtils.copyProperties(emailDTO, email);
        service.sendEmail(email);
    }
}
