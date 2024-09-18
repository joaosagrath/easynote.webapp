package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Alunos;
import app.entity.Usuarios;
import app.repository.UsuariosRepository;

@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	

	public String save (Usuarios usuarios) {
		usuarios.setAtivo(true);
		this.usuariosRepository.save(usuarios);
		return "Usuário cadastrado com sucesso!";
	}
	
	//esse do usuario nao trata excecao
	/*public String update (Usuarios usuarios, long id) {
		usuarios.setId(id);
		this.usuariosRepository.save(usuarios);
		return "Usuário atualizado com sucesso!";
	}*/
	
	// esse novo trata e verifica se ele existe
	public String update(Usuarios usuarios, long id) {
	    // Verificar se o usuário existe antes de atualizar
	    Optional<Usuarios> usuarioExistente = this.usuariosRepository.findById(id);
	    
	    ///Optional é um contêiner que pode conter um valor não nulo ou estar vazio (sem valor).
	    // Ele ajuda a evitar o uso de null e o risco de NullPointerException, promovendo um código
	    //mais seguro e legível.
	    if (usuarioExistente.isPresent()) {
	        usuarios.setId(id);
	        this.usuariosRepository.save(usuarios);
	        return "Usuário atualizado com sucesso!";
	    } else {
	        throw new RuntimeException("Usuário não encontrado");
	    }
	}
	
	public Usuarios findById (long id) {
		
		Optional<Usuarios> optional = this.usuariosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			//return null;
			throw new RuntimeException("Usuário não encontrado");
		
	}
	
	public List<Usuarios> findAll () {
		
		return this.usuariosRepository.findAll();
		
	}
	// o anterior nao tinha verificacao
	
	/*public String delete (long id) {
		this.usuariosRepository.desativarUsuarios(id);
		return "Usuário desativado com sucesso!";
	}*/
	
	
	// o novo verifica se existe e desativa
	public String delete(long id) {
	    // Verifica se o usuário existe
	    Optional<Usuarios> usuario = usuariosRepository.findById(id);
	    
	    if (usuario.isEmpty()) { //se estiver vazio ou nao existir
	    	
	        throw new RuntimeException("Usuário não encontrado");
	    }
	    //caso exista
	    try {
	        // Tenta desativar o usuário
	        usuariosRepository.desativarUsuarios(id);
	        
	        //se der merda
	    } catch (Exception e) {
	        // Captura qualquer exceção e retorna uma mensagem de erro
	        throw new RuntimeException("Erro ao desativar usuário: " + e.getMessage());
	    }
	    
	    return "Usuário desativado com sucesso!";
	}
	
	
	public Usuarios findByCpf(String cpf) {
		return this.usuariosRepository.findByCpf(cpf);
	}
	
	//O anterior esta dando no teste recursao infinita  
	//pois o método chama a si mesmo, o que resultará em um erro de stack overflow (que esta acontecendo no teste.
	/*public List<Usuarios> findByNome(String nome){
		return this.findByNome(nome);
	}*/ 
	
	//o novo ja vai pro repositorio direto
	//sujeito a revisão
	public List<Usuarios> findByNome(String nome) {
	    return usuariosRepository.findByNomeContains(nome);
	}
	
}