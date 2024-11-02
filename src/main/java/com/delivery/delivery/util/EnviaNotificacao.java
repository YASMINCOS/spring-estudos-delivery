package com.delivery.delivery.util;

import com.delivery.delivery.domain.Cliente;
import com.delivery.delivery.domain.Pedido;
import com.delivery.delivery.dto.Notificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnviaNotificacao {

    @Autowired
    Notificacao notificacao;

    Logger logger = LoggerFactory.getLogger(EnviaNotificacao.class.getSimpleName());

    public void enviaEmail(Cliente cliente, Pedido pedido) {

        logger.info("Enviar notificacao para "+cliente.getNome() + " - pedido $"+pedido.getValorTotal());

        if (notificacao.envioAtivo()) {

            logger.info("Notificacao enviada!");

        } else {

            logger.info("Notificacao desligada!");

        }
    }


}
