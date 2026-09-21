package br.com.FarmaSync.API.controller;

import br.com.FarmaSync.API.models.pedidos;
import br.com.FarmaSync.API.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class pedidosControler {


    @Autowired
    private PedidosRepository pedidosRepository;

    @GetMapping("/")
    public List<pedidos> getPedidos(){
        return pedidosRepository.findAll();
    }




























}

