package app.entity;

import java.util.Date;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	private Date dataRetirada;
	private Date dataDevolucao;
	
	private String situacao;
	
	@OneToMany(mappedBy = "emprestimos", cascade = CascadeType.ALL)
	private Alunos aluno;
	
	@OneToMany(mappedBy = "emprestimos", cascade = CascadeType.ALL)
	private Equipamentos equipamento;
	
}
