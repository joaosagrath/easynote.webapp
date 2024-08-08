package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Equipamentos;
import app.repository.EquipamentosRepository;

@Service
public class EquipamentosService {
	
	@Autowired
	private EquipamentosRepository equipamentosRepository;
	

	public String save (Equipamentos equipamentos) {
		this.equipamentosRepository.save(equipamentos);
		return "Equipamentos cadastrado com sucesso";
	}
	
	public String update (Equipamentos equipamentos, long id) {
		equipamentos.setId(id);
		this.equipamentosRepository.save(equipamentos);
		return "Atualizado com sucesso";
	}
	
	public Equipamentos findById (long id) {
		
		Optional<Equipamentos> optional = this.equipamentosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
		
	}
	
	public List<Equipamentos> findAll () {
		
		return this.equipamentosRepository.findAll();
		
	}
	
	public String delete (long id) {
		this.equipamentosRepository.deleteById(id);
		return "Veículo deletado com sucesso!";
	}
	
}