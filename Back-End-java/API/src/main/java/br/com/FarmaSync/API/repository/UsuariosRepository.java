package br.com.FarmaSync.API.repository;

import br.com.FarmaSync.API.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {
}
