package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Alunos;
import app.repository.AlunosRepository;

@Service
public class AlunosService {
	
	@Autowired
	private AlunosRepository alunosRepository;
	

	public String save (Alunos alunos) {
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
		this.alunosRepository.deleteById(id);
		return "Aluno deletado com sucesso!";
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