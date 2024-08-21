package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.entity.Alunos;
import jakarta.transaction.Transactional;

public interface AlunosRepository extends JpaRepository<Alunos, Long>{

	public Alunos findByRa(String ra);
	
	public List<Alunos> findByNomeContains(String nome);
	
	public Alunos findByCpf(String cpf);
	
	@Modifying
	@Transactional
	@Query("UPDATE Alunos a SET a.ativo = false WHERE a.id = :id")
	public void desativarAlunos(long id);
	
}
