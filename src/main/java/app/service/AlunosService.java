package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Alunos;
import app.entity.Emprestimos;
import app.repository.AlunosRepository;
import app.repository.EmprestimosRepository;

@Service
public class AlunosService {
	
	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private EmprestimosRepository emprestimosRepository;
	

	public String save (Alunos alunos) {
		alunos.setAtivo(true);
		this.alunosRepository.save(alunos);
		return "Aluno cadastrado com sucesso!";
	}
	
	public String update (Alunos alunos, long id) {
		alunos.setId(id);
		this.alunosRepository.save(alunos);
		return "Aluno atualizado com sucesso!";
	}
	
	public Alunos findById (long id) {
		
		Optional<Alunos> optional = this.alunosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
		
	}
	
	public List<Alunos> findAll () {
		
		return this.alunosRepository.findAll();
		
	}
	
	public String delete (long id) {
		
		Alunos aluno = new Alunos();
		aluno.setId(id);
		Emprestimos emp = new Emprestimos();
		emp.setAluno(aluno);
		List<Emprestimos> lista = this.encontrarEmprestimoEmAndamentoPorAluno(emp);

		if (lista != null && !lista.isEmpty()) {
			throw new RuntimeException("Aluno possui empréstimo em andamento!");
		}
		
		aluno.setAtivo(false);
		this.alunosRepository.save(aluno);
		return "Aluno deletado com sucesso!";
	}
	
	private List<Emprestimos> encontrarEmprestimoEmAndamentoPorAluno(Emprestimos emp){
		Alunos aluno = new Alunos();
		aluno.setId(emp.getAluno().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByAlunoAtivo(aluno);
		return lista;
	}
	
	public Alunos findByRa(String ra) {
		return this.alunosRepository.findByRa(ra);
	}
	
	public Alunos findByCpf(String cpf) {
		return this.alunosRepository.findByCpf(cpf);
	}
	
	public List<Alunos> findByNome(String nome){
		return this.findByNome(nome);
	}
	
}