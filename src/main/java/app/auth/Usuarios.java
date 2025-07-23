package app.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.entity.Auditable;
import app.entity.Emprestimos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuarios extends Auditable<String> implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Por favor, informe o nome do usuario")
	private String nome;
	
	@NotBlank(message="Por favor, informe o papel do usuario")
	private String role;
	
	@Column(unique=true)
	@NotBlank(message="Por favor, informe o CPF do usuario")
	@CPF(message="CPF inválido")
	private String cpf;
	
	@Column(unique=true)
	@NotBlank(message="Por favor, informe o LOGIN do usuario")
	private String login;
	
	//@NotBlank(message="Por favor, informe o SENHA do usuario")
	private String senha;
	
	
	private boolean ativo;
	
	@OneToMany(mappedBy = "usuario")
	@JsonIgnoreProperties({"usuario", "aluno"})
	private List<Emprestimos> emprestimos;
	
	
	
	// AUTH

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority(this.role));
	    return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}
	
	@Override
	public boolean isEnabled() {
		return ativo;
	}
	
	
}
