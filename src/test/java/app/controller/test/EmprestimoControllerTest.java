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
        Mockito.when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));
        ResponseEntity<Emprestimos> response = emprestimoController.findById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
