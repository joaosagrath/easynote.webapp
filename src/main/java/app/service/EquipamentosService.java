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
import app.repository.EquipamentosRepository;

@Service
public class EquipamentosService {

	@Autowired
	private EquipamentosRepository equipamentosRepository;

	@Autowired
	private EmprestimosRepository emprestimosRepository;

	public String save(Equipamentos equipamentos) {
		equipamentos.setAtivo(true);
		this.equipamentosRepository.save(equipamentos);
		return "Equipamentos cadastrado com sucesso";
	}

	public String update(Equipamentos equipamentos, long id) {
		equipamentos.setId(id);
		this.equipamentosRepository.save(equipamentos);
		return "Atualizado com sucesso";
	}

	public Equipamentos findById(long id) {

		Optional<Equipamentos> optional = this.equipamentosRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else
			return null;

	}

	public List<Equipamentos> findAll() {

		return this.equipamentosRepository.findAll();

	}

	public String delete(String patrimonio) {
		
		Equipamentos equipamento = this.equipamentosRepository.findByPatrimonio(patrimonio);
		long id = equipamento.getId();
		equipamento.setId(id);
		Emprestimos emp = new Emprestimos();
		emp.setEquipamento(equipamento);

		List<Emprestimos> lista = this.encontrarEmprestimoEmAndamentoPorEquip(emp);

		if (lista != null && !lista.isEmpty()) {
			throw new RuntimeException("Equipamento possui empréstimo em andamento!");
		}else {
			
			int equipDesativado = this.equipamentosRepository.desativarEquipamentos(id);
		    if (equipDesativado > 0) {
		        return "Equipamento desativado com sucesso!";
		    } else {
		        return "Falha ao desativar equipamento.";
		    }
		}
		
		
	}

	private List<Emprestimos> encontrarEmprestimoEmAndamentoPorEquip(Emprestimos emp){
		Equipamentos equipamento = new Equipamentos();
		equipamento.setId(emp.getEquipamento().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByEquipamentoAtivo(equipamento);
		return lista;
	}
	
	public String reativarEquipamento(String patrimonio) {
	    Equipamentos equipamento = this.equipamentosRepository.findByPatrimonio(patrimonio);
	    long id = equipamento.getId();
	    int equipamentoReativado = this.equipamentosRepository.reativarEquipamentos(id);
	    if (equipamentoReativado > 0) {
	        return "Equipamento reativado com sucesso!";
	    } else {
	        return "Falha ao reativar equipamento.";
	    }
	}
	

	public Equipamentos findByPatrimonio(String patrimonio) {
		return this.equipamentosRepository.findByPatrimonio(patrimonio);
	}

	public List<Equipamentos> findByMarca(String marca) {
		return this.equipamentosRepository.findByMarca(marca);
	}

	public List<Equipamentos> findByModelo(String modelo) {
		return this.equipamentosRepository.findByModeloContains(modelo);
	}

	public List<Equipamentos> findByDataAquisicao(Date data1, Date data2) {
		return this.equipamentosRepository.findByDataAquisicao(data1, data2);
	}

	public List<Equipamentos> findBySituacao(String situacao) {
		return this.findBySituacao(situacao);
	}
	
	public List<Equipamentos> findEquipamentosAtivos(){
		return this.equipamentosRepository.findByAtivoTrue();
	}
	
	public List<Equipamentos> findEquipamentosInativos(){
		return this.equipamentosRepository.findByAtivoFalse();
	}

}