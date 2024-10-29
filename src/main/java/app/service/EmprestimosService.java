package app.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Alunos;
import app.entity.Emprestimos;
import app.entity.Equipamentos;
import app.repository.EmprestimosRepository;

@Service
public class EmprestimosService {
	
	@Autowired
	private EmprestimosRepository emprestimosRepository;
	

	public String save (Emprestimos emprestimos) {
		
		List<Emprestimos> lista = this.encontrarEmprestimoEmAndamentoPorAluno(emprestimos);
		Optional<Emprestimos> equip = this.encontrarEmprestimoEmAndamentoPorEquip(emprestimos.getEquipamento().getPatrimonio());
		
		if(lista != null && !lista.isEmpty()) {
			throw new RuntimeException("Aluno já possui empréstimo em andamento!");
		}else if(equip.isPresent()) {
			throw new RuntimeException("Equipamento já possui empréstimo em andamento!");
		}
		
		emprestimos.setDataRetirada(LocalDateTime.now());
		emprestimos.setSituacao("Em Andamento");
		Emprestimos emp = this.emprestimosRepository.save(emprestimos);
		 
		if(emp != null) {
			return "Empréstimo salvo com sucesso!";
		}else {
			throw new RuntimeException("Erro ao salvar empréstimo!");
		}
	
	}
	
	public List<Emprestimos> encontrarEmprestimoEmAndamentoPorAluno(Emprestimos emp){
		Alunos aluno = emp.getAluno();
		//aluno.setId(emp.getAluno().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByAlunoAtivo(aluno);
		return lista;
	}
	
	public Optional<Emprestimos> encontrarEmprestimoEmAndamentoPorEquip(String patrimonio){
		Equipamentos equipamento = new Equipamentos();
		equipamento.setPatrimonio(patrimonio);
		//equipamento.setId(emp.getEquipamento().getId());
		Optional<Emprestimos> emprestimo = this.emprestimosRepository.findByEmprestimosByEquipamentoAtivo(equipamento.getPatrimonio());
		return emprestimo;
	}
	
	public String update (Emprestimos emprestimos, long id) {
		emprestimos.setId(id);
		Emprestimos emp = this.emprestimosRepository.save(emprestimos);
		if(emp != null) {
			return "Empréstimo atualizado com sucesso!";
		}else {
			throw new RuntimeException("Erro ao atualizar empréstimo!");
		}
	}
	
	public Emprestimos findById (long id) {
		
		Optional<Emprestimos> optional = this.emprestimosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
		
	}
	
	public List<Emprestimos> findAll () {
		
		return this.emprestimosRepository.findAll();
		
	}

	public String encerrarEmprestimo(long id) {
		LocalDateTime data = LocalDateTime.now();
		String situacao = "Encerrado";
		this.emprestimosRepository.encerrarEmprestimos(id, data, situacao);
		return "Empréstimo encerrado com sucesso";
	}
	
	public String tempodeUso(Emprestimos emprestimo) {
	    LocalDateTime dataRetirada = emprestimo.getDataRetirada();
	    LocalDateTime dataDevolucao = emprestimo.getDataDevolucao();

	    if (dataDevolucao != null && dataRetirada != null) {
	        Duration duracao = Duration.between(dataRetirada, dataDevolucao);
	        
	        long dias = duracao.toDays();
	        long horas = duracao.toHours() % 24; // Resto para calcular horas além dos dias
	        long minutos = duracao.toMinutes() % 60; // Resto para calcular minutos além das horas

	        return String.format("%d dias, %d horas, %d minutos", dias, horas, minutos);
	    } else {
	        return "Datas inválidas";
	    }
	}
	
	public List<Emprestimos> findByFilter(LocalDateTime dataRetirada, LocalDateTime dataDevolucao,
			String situacao, String ra, String usuario, String patrimonio) {
	    return this.emprestimosRepository.findByFilter(dataRetirada, dataDevolucao, situacao, ra,
	    		usuario, patrimonio);
	}
	
	public List<Emprestimos> findBySituacao(String situacao){
		return this.emprestimosRepository.findBySituacao(situacao);
	}
	
	public List<Emprestimos> findByUsuario(String usuarioNome){
		return this.emprestimosRepository.findByUsuarioNomeContains(usuarioNome);
	}
	
	public List<Emprestimos> findByAluno(String ra){
		return this.emprestimosRepository.findByAlunoRa(ra);
	}
	
	public List<Emprestimos> findByEquipamento(String equipamento){
		return this.emprestimosRepository.findByEquipamentoPatrimonio(equipamento);
	}
	
	public List<Emprestimos> findByDataRetirada(LocalDateTime data1, LocalDateTime data2){
		return this.emprestimosRepository.findByDataRetirada(data1, data2);
	}
	
	public List<Emprestimos> findByDataDevolucao(LocalDateTime data1, LocalDateTime data2){
		return this.emprestimosRepository.findByDataDevolucao(data1, data2);
	}
	
}