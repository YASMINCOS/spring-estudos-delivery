package com.delivery.delivery.service;


import com.delivery.delivery.domain.Cliente;
import com.delivery.delivery.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClienteRepositoryTest {

    @Autowired
    ClienteRepository repository;

    @Test
    public void buscaClientesCadastrados() {
        Page<Cliente> clientes = this.repository.findAll(PageRequest.of(0, 10));
        assertThat(clientes.getTotalElements()).isGreaterThan(1L);
    }

    @Test
    public void buscaClienteFernando() {
        var clienteNaoEncontrado = this.repository.findByNome("Fernando");
        assertThat(clienteNaoEncontrado).isNull();

        var cliente = this.repository.findByNome("Fernando Boaglio");
        assertThat(cliente).isNotNull();
        assertThat(cliente.getNome()).isEqualTo("Fernando Boaglio");
        assertThat(cliente.getEndereco()).isEqualTo("Sampa");
    }

}
