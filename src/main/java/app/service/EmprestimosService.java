package app.service;

import java.util.Date;
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
		
		
		List<Emprestimos> alunoEmp = this.emprestimosRepository.findByAlunoRa(emprestimos.getAluno().getRa());
		
		for (Emprestimos emp : alunoEmp) {
		    if (emp.getSituacao().equals("Em andamento")) {
		       return null;
		    }
		}
		
		List<Emprestimos> equipEmp = this.emprestimosRepository.findByEquipamentoPatrimonio(emprestimos.getEquipamento().getPatrimonio());
		
		for(Emprestimos emp : equipEmp) {
			if(emp.getSituacao().equals("Em andamento")) {
				return null;
			}
		}
		
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