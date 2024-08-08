package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Emprestimos;
import app.repository.EmprestimosRepository;

@Service
public class EmprestimosService {
	
	@Autowired
	private EmprestimosRepository emprestimosRepository;
	

	public String save (Emprestimos emprestimos) {
		this.emprestimosRepository.save(emprestimos);
		return "Emprestimos cadastrado com sucesso";
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
	
}