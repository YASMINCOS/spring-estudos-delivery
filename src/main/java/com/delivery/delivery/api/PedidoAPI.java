package com.delivery.delivery.api;

import com.delivery.delivery.domain.Item;
import com.delivery.delivery.domain.Pedido;
import com.delivery.delivery.repository.ClienteRepository;
import com.delivery.delivery.repository.ItemRepository;
import com.delivery.delivery.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PedidoAPI {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemRepository itemRepository;

    public PedidoAPI(PedidoRepository pedidoRepository,ClienteRepository clienteRepository,ItemRepository itemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/pedido")
    public Pedido fazPedido(@RequestBody NovoPedido novoPedido) {

        var pedido = new Pedido();
        var clienteOpt = clienteRepository.findById(novoPedido.getIdCliente());
        pedido.setCliente(clienteOpt.get());
        pedido.setData(new Date());
        pedido.setValorTotal(novoPedido.getValorTotal());

        var itens = new ArrayList<Item>();
        for (var idItem : novoPedido.getItensId()) {
            var itemOpt = itemRepository.findById(idItem);
            var item = itemOpt.get();
            itens.add(item);
        }
        pedido.setItens(itens);
        pedido.setStatus(FluxoPedido.CHEGOU_NA_COZINHA.name());

        pedidoRepository.save(pedido);

        return pedido;
    }

    @GetMapping("/pedido/{id}")
    public Pedido buscaPedido(@PathVariable("id") Long id) {

        var pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            return pedidoOpt.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,FluxoPedido.NAO_ENCONTRADO.name());
        }
    }

    @DeleteMapping("/pedido/{id}")
    public void cancelaPedido(@PathVariable Long id) {

        var pedidoOpt = pedidoRepository.findById(id);
        Pedido pedido;
        if (pedidoOpt.isPresent()) {
            pedido = pedidoOpt.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, FluxoPedido.NAO_ENCONTRADO.name());
        }

        pedido.setStatus(FluxoPedido.CANCELADO.name());
        pedidoRepository.save(pedido);
        pedidoRepository.flush();

    }

    @GetMapping("/pedido/all")
    public List<Pedido> buscaTudo() {

        var pedidoLista = pedidoRepository.findAll();
        if (pedidoLista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return pedidoLista;
    }

}
