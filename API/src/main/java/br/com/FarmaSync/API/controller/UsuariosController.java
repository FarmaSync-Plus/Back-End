package br.com.FarmaSync.API.controller;


import br.com.FarmaSync.API.Records.Login;
import br.com.FarmaSync.API.models.Usuarios;
import br.com.FarmaSync.API.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
record LoginResponse(String nomeUsuario, String cpf,String senhaUsuario,String emailUsuario) {}
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
//TODO:FAZER AS TRATIVAS PARA LOGIN DE CONTA
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Login login){
    System.out.println("====== Tentativa de Login ======");
    System.out.println("Identificador recebido : " +login.cpf());
    System.out.println("Senha recebido : " +login.senhaUsuario());

    var loginOpt = usuariosRepository.findByCpfOrEmailUsuario(login.cpf(), login.emailUsuario());
    if(loginOpt.isEmpty()){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senhas incorretos");

    }
    var usuario =  loginOpt.get();
    System.out.println("CPF do Usuario recebido : " +usuario.getCpf() + "| Email no banco : " +usuario.getEmailUsuario());
    System.out.println("Senha recebido : " +usuario.getSenhaUsuario());
    boolean senhaBate =  passwordEncoder.matches(login.senhaUsuario(),usuario.getSenhaUsuario());
    System.out.println("A senha Bate com  a senha criptografada  : " +senhaBate);
 if(!senhaBate){
     System.out.println("Resultado : Senha incorreta");
     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos!");
 }
 var resposta = new LoginResponse(usuario.getNomeUsuario(),usuario.getCpf(), usuario.getSenhaUsuario(),usuario.getEmailUsuario());
    System.out.println("Resultados: Login com sucesso para : " +usuario.getNomeUsuario());
    return ResponseEntity.ok(resposta);

}

@DeleteMapping(value = "{id}", produces = "application/json")
public ResponseEntity<?> deletar(@PathVariable Long id){
Usuarios usuario = usuariosRepository.findById(id).get();
usuariosRepository.delete(usuario);
    System.out.println("Usuário Removido com sucesso !");
    return ResponseEntity.ok().build();
}

@PutMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Usuarios usuario){
    Usuarios usuarioBanco = usuariosRepository.findById(id).get();
    usuarioBanco.setNomeUsuario(usuario.getNomeUsuario());
    usuarioBanco.setCpf(usuario.getCpf());
    usuarioBanco.setSenhaUsuario(usuario.getSenhaUsuario());
    usuarioBanco.setEmailUsuario(usuario.getEmailUsuario());
    usuarioBanco.setBairro(usuario.getBairro());
    usuarioBanco.setLogradouro(usuario.getLogradouro());
    usuarioBanco.setEstado(usuario.getEstado());
    usuarioBanco.setCidade(usuario.getCidade());
    usuarioBanco.setGenero(usuario.getGenero());
    usuarioBanco.setCep(usuario.getCep());
    if(usuario.getSenhaUsuario()!=null &&  !usuario.getSenhaUsuario().trim().isEmpty()){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenhaUsuario());
        usuario.setSenhaUsuario(senhaCriptografada);
    }
usuariosRepository.save(usuarioBanco);
    System.out.println("Usuario atualizado com sucesso !");
    return ResponseEntity.ok().build();
}


}
