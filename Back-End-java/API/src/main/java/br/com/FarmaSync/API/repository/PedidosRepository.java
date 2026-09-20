package br.com.FarmaSync.API.repository;

import br.com.FarmaSync.API.models.pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidosRepository extends JpaRepository <pedidos,Long>{
}
