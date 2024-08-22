package app.repository;

import java.time.LocalDate;
import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.entity.Alunos;
import app.entity.Emprestimos;
import app.entity.Equipamentos;
import jakarta.transaction.Transactional;

public interface EmprestimosRepository extends JpaRepository<Emprestimos, Long>{
	
	//public List<Emprestimos> findByAlunos_Ra(String ra);
	
	public List<Emprestimos> findBySituacao(String situacao);
	
	public List<Emprestimos> findByAlunoRa(String ra);
	
	public List<Emprestimos> findByEquipamentoPatrimonio(String patrimonio);
	

	@Query("FROM Emprestimos e WHERE e.dataRetirada BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataRetirada(Date data1, Date data2);
	
	@Query("FROM Emprestimos e WHERE e.dataDevolucao BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataDevolucao(Date data1, Date data2);
	
	@Query("FROM Emprestimos e WHERE e.aluno = :aluno AND e.situacao = 'Em Andamento'")
	public List<Emprestimos> findByEmprestimosByAlunoAtivo(Alunos aluno);
	
	@Query("FROM Emprestimos e WHERE e.equipamento = :equipamento AND e.situacao = 'Em Andamento'")
	public List<Emprestimos> findByEmprestimosByEquipamentoAtivo(Equipamentos equipamento);
	
	@Modifying
	@Transactional
	@Query("UPDATE Emprestimos e SET e.dataDevolucao = :date, e.situacao = :situacao WHERE e.id = :id")
	public void encerrarEmprestimos(long id, LocalDate date, String situacao);


}
