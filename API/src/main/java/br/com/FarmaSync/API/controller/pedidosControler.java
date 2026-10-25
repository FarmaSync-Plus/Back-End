package br.com.FarmaSync.API.controller;

import br.com.FarmaSync.API.models.pedidos;
import br.com.FarmaSync.API.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pedidos")
public class pedidosControler {


    @Autowired
    private PedidosRepository pedidosRepository;


    @GetMapping
    public List<pedidos> getPedidos(){
        return pedidosRepository.findAll();
    }

  //  @GetMapping("/")
    //public List<Usuarios> findAll(){
     //   return usuariosRepository.findAll();
    //}



@PostMapping("/finalizar")
    public ResponseEntity<?> finalizar(@RequestBody Map<String,Object> body){
try{

String pagamento = body.get("pagamento").toString();
Double valorTotal = Double.valueOf(body.get("valor_total").toString());
Integer quantidadeTotal = Integer.valueOf(body.get("quantidade_total").toString());
String produtoJson = body.get("produto_json").toString();


pedidos novos = new pedidos();


    novos.setPagamento(pagamento);
    novos.setValorTotal(valorTotal);
    novos.setQuantidadeTotal(quantidadeTotal);
    novos.setDataPedido(java.time.LocalDateTime.now());

    novos.setProdutoJson(produtoJson);
    return ResponseEntity.ok().body(pedidosRepository.save(novos));
} catch (Exception e ){

    return ResponseEntity.badRequest().body("Erro ao salvar: "+e.getMessage());
}



}























}

