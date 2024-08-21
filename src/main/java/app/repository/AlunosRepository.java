package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Alunos;

public interface AlunosRepository extends JpaRepository<Alunos, Long>{

	public Alunos findByRa(String ra);
	
	public List<Alunos> findByNomeContains(String nome);
	
	public Alunos findByCpf(String cpf);
	
}
