package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${app.agent.email}")
    private String agentEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendHtmlMail(OrderDto orderDto) {
        try {
            final Context ctx = new Context();
            ctx.setVariable("order", orderDto);

            final String htmlContent = this.templateEngine.process("new_order.html", ctx);

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(agentEmail);
            mailMessage.setText(htmlContent);
            mailMessage.setSubject("A new Order arrived from the client");

            javaMailSender.send(mailMessage);
            //            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            e.printStackTrace();
            //            return "Error while Sending Mail";
        }
    }

}