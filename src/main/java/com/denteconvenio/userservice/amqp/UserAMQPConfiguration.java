package com.denteconvenio.userservice.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserAMQPConfiguration {

    @Bean
    public Queue filaConsultorioCriado(){
        return QueueBuilder.durable("consultorio.criado").build();
    }

    @Bean 
    public DirectExchange directExUserConsultorio() {
        return new DirectExchange("user-consultorio"); 
    } 
    
    @Bean 
    public Binding directBinding(Queue queue, DirectExchange exchange) {
	    return BindingBuilder.bind(filaConsultorioCriado()).to(directExUserConsultorio()).with("criado"); 
    }


    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter); //Setando novo conversor
        return  rabbitTemplate;
    }   


    @Bean 
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) { 
        // Inciailizando o RabbitAdmin com tudo que foi configurado
        return event -> rabbitAdmin.initialize(); 
    }
}
