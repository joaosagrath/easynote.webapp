package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.base2.AlunosUniamerica;
import app.repository.AlunosUniamericaRepository;

@Service // Anotação que indica que essa classe é um serviço geren
public class AlunosUniamericaService {
	
	@Autowired
	private AlunosUniamericaRepository alunosUniamericaRepository;
	
	public AlunosUniamerica findByRa(Long ra) { // Altere para Long
		return this.alunosUniamericaRepository.findByRa(ra);
	}

}
