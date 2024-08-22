package app.entity;

import java.time.LocalDate;
import java.util.Date;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Emprestimos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalDate dataRetirada;
	private LocalDate dataDevolucao;
	
	private String situacao;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnoreProperties("emprestimos")
	private Alunos aluno;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnoreProperties("emprestimos")
	private Equipamentos equipamento;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnoreProperties("emprestimos")
	private Usuarios usuario;
	
}
