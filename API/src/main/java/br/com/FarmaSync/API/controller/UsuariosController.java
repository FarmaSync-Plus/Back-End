package br.com.FarmaSync.API.controller;


import br.com.FarmaSync.API.models.Usuarios;
import br.com.FarmaSync.API.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuariosController {



@Autowired
private UsuariosRepository usuariosRepository;
//TODO: Aqui coloque o @Autowired do Password encoder
@Autowired
private PasswordEncoder passwordEncoder;

@GetMapping("/")
    public List<Usuarios> findAll(){
    return usuariosRepository.findAll();
}

@PostMapping(value ="save",consumes = "application/json",produces = "application/json")
    public  Usuarios save(@RequestBody Usuarios usuarios){

    if(usuariosRepository.existsByCpf(usuarios.getCpf())){
        throw new RuntimeException("Cpf já cadastrado");
    }
    String senhaCriptografada = passwordEncoder.encode(usuarios.getSenhaUsuario());
    usuarios.setSenhaUsuario(senhaCriptografada);

    return usuariosRepository.save(usuarios);
}















}
