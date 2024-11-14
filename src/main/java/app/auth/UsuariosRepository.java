package app.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

	public List<Usuarios> findByNomeContains(String nome);
	
	public Usuarios findByCpf(String cpf);
	
	public Optional<Usuarios> findByLogin(String login);
	
	@Modifying
	@Transactional
	@Query("UPDATE Usuarios u SET u.ativo = false WHERE u.id = :id")
	public String desativarUsuarios(long id);
}
