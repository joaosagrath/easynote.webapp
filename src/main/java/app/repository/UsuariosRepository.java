package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

	public List<Usuarios> findByNomeContains(String nome);
	
	public Usuarios findByCpf(String cpf);
}
