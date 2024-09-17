package app.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import app.service.test.EmprestimoServiceTest;

@SpringBootTest
public class EmprestimoControllerTest {

	@Autowired
	EmprestimosController emprestimoController;

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
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	/*@Test
	@DisplayName("Update Empréstimo Corretamente")
	void updateEmprestimo() {
		Mockito.when(emprestimoRepository.save(emprestimoAtualizado)).thenReturn(emprestimoAtualizado);
		Mockito.when(emprestimoService.update(emprestimoAtualizado, 1)).thenReturn("Empréstimo atualizado com sucesso!");

		ResponseEntity<String> response = emprestimoController.update(emprestimoAtualizado, 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Empréstimo atualizado com sucesso!", response.getBody());
	}*/
	
	@Test
	@DisplayName("FindById - ID Válido")
	void findByIdValido() {
		Mockito.when(emprestimoRepository.findById(1L)).thenReturn(null);
		ResponseEntity<Emprestimos> lista = emprestimoController.findById(99);
		assertEquals(HttpStatus.BAD_REQUEST, lista.getStatusCode());
	}
	
	@Test
	@DisplayName("FindById - ID Válido")
	void findByIdInvalido() {
		Mockito.when(emprestimoRepository.findById(99L)).thenReturn(Optional.of(emprestimo));
		ResponseEntity<Emprestimos> lista = emprestimoController.findById(1);
		assertEquals(HttpStatus.OK, lista.getStatusCode());
	}

}
