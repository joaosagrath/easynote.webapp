package app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Alunos;
import app.entity.Emprestimos;
import jakarta.transaction.Transactional;

public interface EmprestimosRepository extends JpaRepository<Emprestimos, Long>{
	
	//public List<Emprestimos> findByAlunos_Ra(String ra);
	
	public List<Emprestimos> findBySituacao(String situacao);
	
	public List<Emprestimos> findByAlunoRa(String ra);
	
	public List<Emprestimos> findByEquipamentoPatrimonio(String patrimonio);
	
	public List<Emprestimos> findByUsuarioNomeContains(String usuarioNome);
	

	@Query("FROM Emprestimos e WHERE e.dataRetirada BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataRetirada(LocalDateTime data1, LocalDateTime data2);
	
	@Query("FROM Emprestimos e WHERE e.dataDevolucao BETWEEN :data1 AND :data2")
	public List<Emprestimos> findByDataDevolucao(LocalDateTime data1, LocalDateTime data2);
	
	@Query("FROM Emprestimos e WHERE e.aluno = :aluno AND e.situacao = 'Em Andamento'")
	public List<Emprestimos> findByEmprestimosByAlunoAtivo(Alunos aluno);
	
	/*@Query("FROM Emprestimos e WHERE e.equipamento = :equipamento AND e.situacao = 'Em Andamento'")
	public List<Emprestimos> findByEmprestimosByEquipamentoAtivo(Equipamentos equipamento);*/
	
	@Query("FROM Emprestimos e WHERE e.equipamento.patrimonio = :patrimonio AND e.situacao = 'Em Andamento'")
	public Optional<Emprestimos> findByEmprestimosByEquipamentoAtivo(String patrimonio);
	
	@Query("FROM Emprestimos e WHERE " +
		       "( :dataRetirada is null or e.dataRetirada >= :dataRetirada ) AND " +
		       "( :dataDevolucao is null or e.dataDevolucao <= :dataDevolucao ) AND " +
		       "( :situacao = '' or e.situacao = :situacao ) AND " +
		       "( :ra = '' or e.aluno.ra = :ra ) AND " +
		       "( :usuario = '' or e.usuario.nome LIKE CONCAT('%', :usuario, '%') ) AND " +
		       "( :patrimonio = '' or e.equipamento.patrimonio = :patrimonio )")
		public List<Emprestimos> findByFilter(
		       @Param("dataRetirada") LocalDateTime dataRetirada,
		       @Param("dataDevolucao") LocalDateTime dataDevolucao,
		       @Param("situacao") String situacao,
		       @Param("ra") String ra,
		       @Param("usuario") String usuario,
		       @Param("patrimonio") String patrimonio);
	
	@Modifying
	@Transactional
	@Query("UPDATE Emprestimos e SET e.dataDevolucao = :date, e.situacao = :situacao WHERE e.id = :id")
	public void encerrarEmprestimos(long id, LocalDateTime date, String situacao);


}
