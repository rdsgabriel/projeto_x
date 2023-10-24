package com.ophis.projectx.producers;

import com.ophis.projectx.dto.EmailDTO;
import com.ophis.projectx.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducer {

    private final RabbitTemplate template;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    @Value("${broker.welcome.msg}")
    private String welcome;

    public void  publishMessageEmail(User user){
        var emailDto = new EmailDTO();
        emailDto.setId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Cadastro Realizado com sucesso.");
        emailDto.setText(user.getName() + welcome);

        template.convertAndSend("", routingKey, emailDto);
    }
}
