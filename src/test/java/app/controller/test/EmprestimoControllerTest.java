package app.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.EmprestimosController;
import app.entity.Alunos;
import app.entity.Emprestimos;
import app.entity.Equipamentos;
import app.entity.Usuarios;
import app.repository.EmprestimosRepository;
import app.service.EmprestimosService;

@SpringBootTest
public class EmprestimoControllerTest {

    @Autowired
    EmprestimosController emprestimoController;

    @MockBean
    EmprestimosService emprestimoService;

    @MockBean
    EmprestimosRepository emprestimoRepository;

    Emprestimos emprestimo;
    Emprestimos emprestimoAtualizado;

    @BeforeEach
    void setup() {
        SimpleDateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            Date dataNascimento = formato1.parse("20/03/2003");
            LocalDate dtAquisicao = LocalDate.parse("06/09/2024", formato2);
            LocalDateTime dataRetirada = LocalDateTime.now();
            LocalDateTime dataDevolucao = dataRetirada.plusHours(2);

            Alunos aluno = new Alunos(1, "Beatriz Schindler", dataNascimento, "115.822.819-80", "biaschindler@gmail.com", "(45) 99999-9999", "bea-schin", "123", "505233", "Engenharia de Software", true, null);

            Equipamentos equipamento = new Equipamentos(1, "123456", "Dell", "AX05", dtAquisicao, "OK", "Disponível", true, null);

            Usuarios usuario = new Usuarios(1, "João Girardi", "008.398.349-00", "joao-girardi", "123", true, null);

            emprestimo = new Emprestimos(1, dataRetirada, null, "Em Andamento", "", aluno, equipamento, usuario);
            emprestimoAtualizado = new Emprestimos(1, dataRetirada, dataDevolucao, "Encerrado", "", aluno, equipamento, usuario);

            Mockito.when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimo);
            Mockito.when(emprestimoRepository.findAll()).thenReturn(List.of(emprestimo));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Salvar empréstimo de forma correta")
    void salvarEmprestimoCorreto() {
        ResponseEntity<String> response = emprestimoController.save(emprestimo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Salvar empréstimo caindo no catch")
    void salvarEmprestimoCaindoNoCatch() {
        Mockito.when(emprestimoRepository.save(emprestimo))
                .thenThrow(new RuntimeException("Erro ao salvar empréstimo"));
        ResponseEntity<String> response = emprestimoController.save(emprestimo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("FindById - ID Inválido")
    void findByIdInvalido() {
        Mockito.when(emprestimoRepository.findById(99L)).thenReturn(Optional.empty());
        ResponseEntity<Emprestimos> response = emprestimoController.findById(99);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
	
	@Test
	@DisplayName("FindById - ID Válido")
	void findByIdValido() {
		Mockito.when(emprestimoRepository.findById(1L)).thenReturn(null);
		ResponseEntity<Emprestimos> lista = emprestimoController.findById(99);
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindAll")
	void findAll() {
		Mockito.when(emprestimoRepository.findAll()).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findAll();
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindAll - Lista vazia")
	void findAllListaVazia() {
		Mockito.when(emprestimoRepository.findAll()).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findAll();
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Situação Correta")
	void situacaoCorreta() {
		Mockito.when(emprestimoRepository.findBySituacao("Em Andamento")).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findBySituacao("Em Andamento");
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Situação incorreta")
	void situacaoInexistente() {
		Mockito.when(emprestimoRepository.findBySituacao("Em Aberto")).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findBySituacao("Em Aberto");
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - RA Aluno Correto")
	void raCorreto() {
		Mockito.when(emprestimoRepository.findByAlunoRa("505233")).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByAluno("505233");
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - RA Aluno Incorreto")
	void raIncorreto() {
		Mockito.when(emprestimoRepository.findByAlunoRa("555555")).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByAluno("555555");
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Patrimonio Equipamento Correto")
	void patrimonioCorreto() {
		Mockito.when(emprestimoRepository.findByEquipamentoPatrimonio("123456")).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByEquipamento("123456");
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Patrimonio Equipamento Incorreto")
	void patrimonioIncorreto() {
		Mockito.when(emprestimoRepository.findByEquipamentoPatrimonio("987654")).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByEquipamento("987654");
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Data Retirada Válida")
	void dataRetValida() {
		LocalDateTime dataRetirada = LocalDateTime.now();
		LocalDateTime dataDevolucao = dataRetirada.plusHours(2);

		Mockito.when(emprestimoRepository.findByDataRetirada(dataRetirada, dataDevolucao)).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByDataRetirada(dataRetirada, dataDevolucao);
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Data Retirada Inválida")
	void dataRetInvalida() {
		LocalDateTime dataRetirada = LocalDateTime.of(2024, 8, 5, 20, 55);
		LocalDateTime dataDevolucao = dataRetirada.plusHours(2);
		Mockito.when(emprestimoRepository.findByDataRetirada(dataRetirada, dataDevolucao)).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByDataRetirada(dataRetirada, dataDevolucao);
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	

	@Test
	@DisplayName("FindBy - Data Devolucao Válida")
	void dataDevValida() {
		LocalDateTime dataRetirada = LocalDateTime.now();
		LocalDateTime dataDevolucao = dataRetirada.plusHours(2);

		Mockito.when(emprestimoRepository.findByDataDevolucao(dataRetirada, dataDevolucao)).thenReturn(List.of(emprestimo));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByDataDevolucao(dataRetirada, dataDevolucao);
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindBy - Data Devolucao Inválida")
	void dataDevInvalida() {
		LocalDateTime dataRetirada = LocalDateTime.of(2024, 8, 5, 20, 55);
		LocalDateTime dataDevolucao = dataRetirada.plusHours(2);
		Mockito.when(emprestimoRepository.findByDataDevolucao(dataRetirada, dataDevolucao)).thenThrow(new RuntimeException("Erro ao retornar empréstimos!"));
		ResponseEntity<List<Emprestimos>> lista = emprestimoController.findByDataDevolucao(dataRetirada, dataDevolucao);
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("Encerrar Empréstimo Válido")
	void encerrar() {
		
		//Mockito.when(emprestimoRepository.encerrarEmprestimos(1, LocalDateTime.now(), "Encerrado")).thenReturn("Empréstimo encerrado com sucesso");
		ResponseEntity<String> lista = emprestimoController.encerrar(1);
		assertEquals(HttpStatus.OK, lista.getStatusCode());
		assertEquals("Empréstimo encerrado com sucesso", lista.getBody());
	}

}
