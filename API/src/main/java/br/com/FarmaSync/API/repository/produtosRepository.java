package br.com.FarmaSync.API.repository;

import br.com.FarmaSync.API.models.produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface produtosRepository extends JpaRepository<produtos, Long> {
}
