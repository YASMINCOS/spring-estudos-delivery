package com.delivery.delivery.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.delivery.delivery.domain.Item;
import com.delivery.delivery.domain.Pedido;
import com.delivery.delivery.dto.RespostaDTO;
import com.delivery.delivery.repository.ClienteRepository;
import com.delivery.delivery.repository.ItemRepository;
import com.delivery.delivery.util.EnviaNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoPedidoController {

    @Autowired
    public NovoPedidoController(ClienteRepository clienteRepository, ItemRepository itemRepository) {
        this.clienteRepository =clienteRepository;
        this.itemRepository=itemRepository;
       // this.enviaNotificacao = enviaNotificacao;
    }

    private final ClienteRepository clienteRepository;
    private final ItemRepository itemRepository;
    //private final EnviaNotificacao enviaNotificacao;

    @GetMapping("/rest/pedido/novo/{clienteId}/{listaDeItens}")
    public RespostaDTO novo(@PathVariable("clienteId") Long clienteId,@PathVariable("listaDeItens") String listaDeItens) {

        RespostaDTO dto ;
        double valorTotal = 0;
        long ultimoPedido = 0;

        try {

            var clienteOpt = clienteRepository.findById(clienteId);
            var c = clienteOpt.get();
            var listaDeItensID = listaDeItens.split(",");
            var  pedido = new Pedido();
            var itensPedidos = new ArrayList<Item>();

            for (String itemId : listaDeItensID) {

                var itemOpt = itemRepository.findById(Long.parseLong(itemId));
                var item = itemOpt.get();

                itensPedidos.add(item);
                valorTotal += item.getPreco();
            }
            pedido.setItens(itensPedidos);
            pedido.setValorTotal(valorTotal);
            pedido.setData(new Date());
            pedido.setCliente(c);
            c.getPedidos().add(pedido);

            this.clienteRepository.saveAndFlush(c);

           // enviaNotificacao.enviaEmail(c,pedido);

            var pedidosID = new ArrayList<Long>();
            for (var p : c.getPedidos()) {
                pedidosID.add(p.getId());
            }

            ultimoPedido = Collections.max(pedidosID);

            dto = new RespostaDTO(valorTotal,ultimoPedido,"Pedido efetuado com sucesso");

        } catch (Exception e) {
            dto = new RespostaDTO(valorTotal,ultimoPedido,"Erro: " + e.getMessage());
        }
        return dto;
    }

}
