package br.com.FarmaSync.API.repository;
//A repository guarda os métodos de acesso para a classe que estiver na controller
import br.com.FarmaSync.API.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {

boolean existsByCpf(String cpf);


}
