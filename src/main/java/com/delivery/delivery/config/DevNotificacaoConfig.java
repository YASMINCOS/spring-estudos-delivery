package com.delivery.delivery.config;

import com.delivery.delivery.dto.Notificacao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevNotificacaoConfig implements Notificacao {

    public DevNotificacaoConfig() {
        System.out.println("Instanciando DevNotificacaoConfig");
    }

    @Override
    public boolean envioAtivo() {
        return true;
    }
}
