package app.auth;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.config.JwtServiceGenerator;

@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;

	public String logar(Login login) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsuario(),
						login.getSenha()
						)
				);
		Usuarios user = usuariosRepository.findByLogin(login.getUsuario()).get();
		String jwtToken = jwtService.generateToken(user);
		
		return jwtToken;
	}
	

	public String save (Usuarios usuarios) {
		usuarios.setAtivo(true);
		
		usuarios.setSenha(this.bCryptEncoder.encode(usuarios.getSenha()));
		
		this.usuariosRepository.save(usuarios);
		return "Usuário cadastrado com sucesso!";
	}
	
	public String update (Usuarios usuarios, long id) {
		usuarios.setId(id);
		this.usuariosRepository.save(usuarios);
		return "Usuário atualizado com sucesso!";
	}
	
	public Usuarios findById (long id) {
		
		Optional<Usuarios> optional = this.usuariosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
		  throw new RuntimeException("Usuário não encontrado");
		
	}
	
	public List<Usuarios> findAll () {
		
		return this.usuariosRepository.findAll();
		
	}
	
	public String delete(long id) {
	    Optional<Usuarios> usuario = usuariosRepository.findById(id);
	    if (!usuario.isPresent()) {
	        throw new RuntimeException("Usuário não encontrado");
	    }
	    usuariosRepository.desativarUsuarios(id);
	    return "Usuário desativado com sucesso!";
	}
	
	
	public Usuarios findByCpf(String cpf) {
		return this.usuariosRepository.findByCpf(cpf);
	}
	
	/*public Usuarios findByLogin(String login) {
		return this.usuariosRepository.findByLogin(login);
	}*/
	
	public List<Usuarios> findByNome(String nome){
		 return usuariosRepository.findByNomeContains(nome);
	}
	


	
}