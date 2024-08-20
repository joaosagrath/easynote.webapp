package app.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Emprestimos;

public interface EmprestimosRepository extends JpaRepository<Emprestimos, Long>{
	
	//public List<Emprestimos> findByAlunos_Ra(String ra);
	
	public List<Emprestimos> findBySituacao(String situacao);
	
	public List<Emprestimos> findByAlunoRa(String ra);
	
	public List<Emprestimos> findByEquipamentoPatrimonio(String patrimonio);
	

	@Query("FROM Emprestimos e WHERE e.dataRetirada BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataRetirada(Date data1, Date data2);
	
	@Query("FROM Emprestimos e WHERE e.dataDevolucao BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataDevolucao(Date data1, Date data2);

}
