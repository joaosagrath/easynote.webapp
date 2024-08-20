package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Usuarios;
import app.repository.UsuariosRepository;

@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	

	public String save (Usuarios usuarios) {
		this.usuariosRepository.save(usuarios);
		return "Aluno cadastrado com sucesso!";
	}
	
	public String update (Usuarios usuarios, long id) {
		usuarios.setId(id);
		this.usuariosRepository.save(usuarios);
		return "Aluno atualizado com sucesso!";
	}
	
	public Usuarios findById (long id) {
		
		Optional<Usuarios> optional = this.usuariosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
		
	}
	
	public List<Usuarios> findAll () {
		
		return this.usuariosRepository.findAll();
		
	}
	
	public String delete (long id) {
		this.usuariosRepository.deleteById(id);
		return "Aluno deletado com sucesso!";
	}
	
	
	public Usuarios findByCpf(String cpf) {
		return this.usuariosRepository.findByCpf(cpf);
	}
	
	public List<Usuarios> findByNome(String nome){
		return this.findByNome(nome);
	}
	
}