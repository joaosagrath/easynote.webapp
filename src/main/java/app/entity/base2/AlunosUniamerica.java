package app.entity.base2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wiew_alunos_ativos", schema = "dash")
public class AlunosUniamerica {

    @Id
    private Long ra; // A chave primária é o RA

    private String nome;
    private String curso;
}
