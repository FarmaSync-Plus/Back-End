package br.com.FarmaSync.API.controler;

import br.com.FarmaSync.API.models.Usuarios;
import br.com.FarmaSync.API.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuariosController {



    @Autowired
    private UsuariosRepository usuariosRepository;
//TODO: Aqui coloque o @Autowired do Password encoder


    @GetMapping("/")
    public List<Usuarios> findAll(){
        return usuariosRepository.findAll();
    }

















}
