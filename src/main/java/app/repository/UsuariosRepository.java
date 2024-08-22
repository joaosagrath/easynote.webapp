package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.entity.Usuarios;
import jakarta.transaction.Transactional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

	public List<Usuarios> findByNomeContains(String nome);
	
	public Usuarios findByCpf(String cpf);
	
	@Modifying
	@Transactional
	@Query("UPDATE Usuarios u SET u.ativo = false WHERE u.id = :id")
	public String desativarUsuarios(long id);
}
