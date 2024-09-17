package app.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.entity.Alunos;
import app.entity.Emprestimos;
import app.entity.Equipamentos;
import app.entity.Usuarios;
import app.repository.EmprestimosRepository;
import app.service.EmprestimosService;

@SpringBootTest
public class EmprestimoServiceTest {
	
	@Autowired
	EmprestimosService emprestimoService;
	
	@MockBean
	EmprestimosRepository emprestimoRepository;
	
	Emprestimos emprestimo;
	Emprestimos emprestimoAtualizado;
	
	@BeforeEach
	void setup() {
		// Formato de data para java.util.Date
		SimpleDateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
		// Formato de data para LocalDate
		DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			// Convertendo a String para java.util.Date
			Date dataNascimento = formato1.parse("20/03/2003");

			// Convertendo a String para LocalDate corretamente
			LocalDate dtAquisicao = LocalDate.parse("06/09/2024", formato2);

			// Pegando a data e hora atuais para LocalDateTime
			LocalDateTime dataRetirada = LocalDateTime.now();
			LocalDateTime dataDevolucao = dataRetirada.plusHours(2);

			// Criando a instância de Alunos
			Alunos aluno = new Alunos(1, "Beatriz Schindler", dataNascimento, "115.822.819-80",
					"biaschindler@gmail.com", "(45) 99999-9999", "bea-schin", "123", "505233", "Engenharia de Software",
					true, null);

			// Criando a instância de Equipamentos
			Equipamentos equipamento = new Equipamentos(1, "123456", "Dell", "AX05", dtAquisicao, "OK", "Disponível",
					true, null);

			// Criando a instância de Usuarios
			Usuarios usuario = new Usuarios(1, "João Girardi", "008.398.349-00", "joao-girardi", "123", true, null);

			// Criando as instâncias de Emprestimos
			emprestimo = new Emprestimos(1, dataRetirada, null, "Em Andamento", "", aluno, equipamento, usuario);
			emprestimoAtualizado = new Emprestimos(1, dataRetirada, dataDevolucao, "Encerrado", "", aluno, equipamento,
					usuario);

			// Mockando os métodos do repository
			Mockito.when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimo);
			Mockito.when(emprestimoRepository.findAll()).thenReturn(List.of(emprestimo));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Save empréstimo corretamente")
	void salvarEmpCorreto() {
	    Mockito.when(emprestimoRepository.findByEmprestimosByAlunoAtivo(Mockito.any(Alunos.class)))
	           .thenReturn(Collections.emptyList());
	    
	    Mockito.when(emprestimoRepository.findByEmprestimosByEquipamentoAtivo(Mockito.any(Equipamentos.class)))
	           .thenReturn(Collections.emptyList());
	    
	    Mockito.when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimo);
	    
	    String response = this.emprestimoService.save(emprestimo);
	    assertEquals("Empréstimo salvo com sucesso!", response);
	}
	
	@Test
	@DisplayName("Aluno já possui empréstimo em andamento")
	void salvarEmprestimoAlunoComEmprestimoAtivo() {
	    List<Emprestimos> emprestimosAtivos = List.of(emprestimo);
	    
	    Mockito.when(emprestimoRepository.findByEmprestimosByAlunoAtivo(Mockito.any(Alunos.class)))
	           .thenReturn(emprestimosAtivos);
	    
	    assertThrows(RuntimeException.class, () -> {
	        emprestimoService.save(emprestimo);
	    });
	}
	
	@Test
	@DisplayName("Equipamento já possui empréstimo em andamento")
	void salvarEmprestimoEquipamentoComEmprestimoAtivo() {
	    List<Emprestimos> emprestimosAtivos = List.of(emprestimo);
	    
	    Mockito.when(emprestimoRepository.findByEmprestimosByEquipamentoAtivo(Mockito.any(Equipamentos.class)))
	           .thenReturn(emprestimosAtivos);
	    
	    assertThrows(RuntimeException.class, () -> {
	        emprestimoService.save(emprestimo);
	    });
	    
	}
	
	@Test
	@DisplayName("Update com Sucesso")
    void updateComSucesso() {
        // Mocking the repository save call to return the updated object
        Mockito.when(emprestimoRepository.save(Mockito.any(Emprestimos.class))).thenReturn(emprestimo);

        // Call the update method
        String result = emprestimoService.update(emprestimo, 1);

        // Assertions
        assertEquals("Empréstimo atualizado com sucesso!", result);
    }

    @Test
    @DisplayName("Update com Erro")
    void updateComErro() {
        // Simulate a repository failure by returning null
        Mockito.when(emprestimoRepository.save(Mockito.any(Emprestimos.class))).thenReturn(null);

        // Call the update method and assert that an exception is thrown
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            emprestimoService.update(emprestimo, 1);
        });

        assertEquals("Erro ao atualizar empréstimo!", thrown.getMessage());

    }
	
}
