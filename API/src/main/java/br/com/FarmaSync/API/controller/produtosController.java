package br.com.FarmaSync.API.controller;


import br.com.FarmaSync.API.models.produtos;
import br.com.FarmaSync.API.repository.produtosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/produtos")

public class produtosController {


@Autowired
private produtosRepository produtosRepository;

@GetMapping
public List<produtos> getProdutos(){return produtosRepository.findAll();}

}
