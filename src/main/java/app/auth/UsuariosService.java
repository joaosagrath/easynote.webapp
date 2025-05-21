package app.auth;

import org.springframework.http.MediaType;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	//@Autowired
	//private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;
	
	/*
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
	*/
	
	@Autowired
    private RestTemplate restTemplate;

    private final String keycloakTokenUrl = "https://backend.local.easynote.com.br:8443/realms/easynote/protocol/openid-connect/token";
    private final String clientId = "easynote";
    private final String clientSecret = "IW26TemJOqXLIP3tzaVMcSl1HYENSd8I"; // ou remova se o client não exige

    public ResponseEntity<Map<String, Object>> logar(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("username", username);
        formData.add("password", password);
        formData.add("grant_type", "password");
        formData.add("client_secret", clientSecret); // remova esta linha se não precisar

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
        	ResponseEntity<String> response = restTemplate.postForEntity(keycloakTokenUrl, request, String.class);
        	ObjectMapper mapper = new ObjectMapper();
        	JsonNode jsonNode = mapper.readTree(response.getBody());
        	String accessToken = jsonNode.get("access_token").asText();
        	int expiresIn = jsonNode.get("expires_in").asInt();
        	List<String> roles = new ArrayList<>();

        	Map<String, Object> result = new HashMap<>();
        	result.put("token", accessToken);
        	result.put("expiresIn", expiresIn);
        	
        	// Decodificar o token JWT para extrair os roles
            String[] tokenParts = accessToken.split("\\.");
            if (tokenParts.length == 3) {
                String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]));
                JsonNode payloadNode = mapper.readTree(payload);  

                // Extrair roles de realm_access
                JsonNode realmAccess = payloadNode.get("realm_access");
                if (realmAccess != null && realmAccess.has("roles")) {
                    for (JsonNode roleNode : realmAccess.get("roles")) {
                        roles.add(roleNode.asText());
                    }
                }

                // Extrair roles de resource_access > easynote
                JsonNode resourceAccess = payloadNode.get("resource_access");
                if (resourceAccess != null && resourceAccess.has("easynote")) {
                    JsonNode easynoteRoles = resourceAccess.get("easynote").get("roles");
                    if (easynoteRoles != null) {
                        for (JsonNode roleNode : easynoteRoles) {
                            roles.add(roleNode.asText());
                        }
                    }
                }

                // Adicionar lista de roles ao resultado
                result.put("roles", roles);
                
                if (!roles.contains("Easynote")) {
                	throw new RuntimeException("Usuário não autorizado!"); 
                } 
            }
        	
        	//System.out.println("Login :" + username);
        	
        	 //2. Verifica se existe no banco local
            Optional<Usuarios> optionalUsuario = usuariosRepository.findByLogin(username);
        	 
        	
            if (!optionalUsuario.isPresent()) {
            	
            	Usuarios novoUser = new Usuarios();
            	novoUser.setLogin(username);
            	novoUser.setNome(username);
            	novoUser.setAtivo(true);
        		novoUser.setSenha(this.bCryptEncoder.encode(password));
            	
            	
            	if(roles.contains("Admin")) {
            		novoUser.setRole("Admin");
            	}else if (roles.contains("Colaborador")) {
            		novoUser.setRole("Colaborador");
            	}
            	
            	this.usuariosRepository.save(novoUser);
            	System.out.println("NovoUser salvo!");
            
            } else {
                System.out.println("Usuário já existe no banco da aplicação: " + username);
            }
            
        	return ResponseEntity.ok(result);
        
        } catch (Exception e) {
        	// Retornando um Map no catch com uma mensagem de erro
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("error", "Usuário ou senha inválidos");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResult);
        }
    }

	public String save (Usuarios usuarios) {
		usuarios.setAtivo(true);
		
		usuarios.setSenha(this.bCryptEncoder.encode(usuarios.getSenha()));
		
		this.usuariosRepository.save(usuarios);
		return "Usuário cadastrado com sucesso!";
	}
	
	public String update(Usuarios usuarios, long id) {
	    // Busca o usuário existente
	    Usuarios existingUser = this.findByIdLocal(id);
	    if (existingUser == null) {
	        return "Usuário não encontrado!";
	    }

	    // Define o ID do usuário a ser atualizado
	    usuarios.setId(id);

	    // Verifica e mantém a senha existente, se necessário
	    if (usuarios.getSenha() == null || usuarios.getSenha().isEmpty()) {
	        usuarios.setSenha(existingUser.getSenha());
	    } else {
	        usuarios.setSenha(this.bCryptEncoder.encode(usuarios.getSenha()));
	    }

	    // Preserva outros atributos do usuário original, se necessário
	    usuarios.setAtivo(existingUser.isAtivo()); // Exemplo para preservar o estado ativo

	    // Atualiza o usuário no banco
	    this.usuariosRepository.save(usuarios);

	    return "Usuário atualizado com sucesso!";
	}

	
	public Usuarios findById (long id) {
		
		Optional<Usuarios> optional = this.usuariosRepository.findById(id);
		if(optional.isPresent()) {
			optional.get().setSenha("");
			return optional.get();
		}else
		  throw new RuntimeException("Usuário não encontrado");
		
	}
	
	private Usuarios findByIdLocal (long id) {
		
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
	
	public Optional<Usuarios> findByLogin(String login) {
		return this.usuariosRepository.findByLogin(login);
	}
	
	public List<Usuarios> findByNome(String nome){
		 return usuariosRepository.findByNomeContains(nome);
	}
	


	
}