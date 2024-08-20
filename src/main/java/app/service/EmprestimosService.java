package app.service;

import java.util.Date;
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
		
		if(lista != null && !lista.isEmpty()) {
			throw new RuntimeException("Aluno já possui empréstimo em andamento!");
		}
		
		List<Emprestimos> listaEquip = this.encontrarEmprestimoEmAndamentoPorEquip(emprestimos);
		
		if(listaEquip != null && !listaEquip.isEmpty()) {
			throw new RuntimeException("Equipamento já possui empréstimo em andamento!");
		}
		
		this.emprestimosRepository.save(emprestimos);
		return "Emprestimos cadastrado com sucesso";
	}
	
	private List<Emprestimos> encontrarEmprestimoEmAndamentoPorAluno(Emprestimos emp){
		Alunos aluno = new Alunos();
		aluno.setId(emp.getAluno().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByAlunoAtivo(aluno);
		return lista;
	}
	
	private List<Emprestimos> encontrarEmprestimoEmAndamentoPorEquip(Emprestimos emp){
		Equipamentos equipamento = new Equipamentos();
		equipamento.setId(emp.getEquipamento().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByEquipamentoAtivo(equipamento);
		return lista;
	}
	
	public String update (Emprestimos emprestimos, long id) {
		emprestimos.setId(id);
		this.emprestimosRepository.save(emprestimos);
		return "Atualizado com sucesso";
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
	
	public String delete (long id) {
		this.emprestimosRepository.deleteById(id);
		return "Veículo deletado com sucesso!";
	}
	
	public List<Emprestimos> findBySituacao(String situacao){
		return this.emprestimosRepository.findBySituacao(situacao);
	}
	
	public List<Emprestimos> findByAluno(String ra){
		return this.emprestimosRepository.findByAlunoRa(ra);
	}
	
	public List<Emprestimos> findByEquipamento(String equipamento){
		return this.emprestimosRepository.findByEquipamentoPatrimonio(equipamento);
	}
	
	public List<Emprestimos> findByDataRetirada(Date data1, Date data2){
		return this.emprestimosRepository.findByDataRetirada(data1, data2);
	}
	
	public List<Emprestimos> findByDataDevolucao(Date data1, Date data2){
		return this.emprestimosRepository.findByDataDevolucao(data1, data2);
	}
	
}