package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Alunos;
import app.entity.Equipamentos;
import jakarta.transaction.Transactional;

public interface AlunosRepository extends JpaRepository<Alunos, Long>{

	public Alunos findByRa(String ra);
	
	public List<Alunos> findByNomeContains(String nome);
	
	public Alunos findByCpf(String cpf);
	
	public List<Alunos> findByAtivoTrue();
	
	public List<Alunos> findByAtivoFalse();
	
	@Modifying
	@Transactional
	@Query("UPDATE Alunos a SET a.ativo = false WHERE a.id = :id")
	public int desativarAlunos(long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Alunos a SET a.ativo = true WHERE a.id = :id")
	public int reativarAlunos(long id);
	
	@Query("FROM Alunos a WHERE " +
		       "( :ra = '' or a.ra LIKE CONCAT('%', :ra, '%') ) AND " +
		       "( :curso = '' or a.curso LIKE CONCAT('%', :curso, '%') ) AND " +
		       "( :nome = '' or a.nome LIKE CONCAT('%', :nome, '%') )")
		public List<Alunos> findByFilter(
		       @Param("ra") String ra,
		       @Param("nome") String nome,
		       @Param("curso") String curso);
}
