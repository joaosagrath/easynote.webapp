package app.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Equipamentos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	@NotNull(message = "Por favor, informe o número do patrimônio do equipamento")
	private long patrimonio;
	
	@NotBlank(message= "Por favor, informe a marca do equipamento")
	private String marca;
	
	@NotBlank(message= "Por favor, informe o modelo do equipamento")
	private String modelo;
	
	@NotNull(message= "Por favor, informe a data de aquisição do equipamento")
	private Date dataAquisicao;
	
	private String observacao;
	
	private String situacao;
	
	private int ativo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties("equipamentos")
	private List<Emprestimos> emprestimos;
	
	
}
