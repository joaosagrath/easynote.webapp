package app.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.entity.Alunos;
import app.entity.Emprestimos;
import app.entity.Equipamentos;
import app.repository.AlunosRepository;
import app.repository.EmprestimosRepository;
import app.uniamerica.entity.AlunoUniamerica;
import app.uniamerica.service.AlunoUniamericaService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AlunosService {

	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private AlunoUniamericaService alunoUniamericaService;

	@Autowired
	private EmprestimosRepository emprestimosRepository;

	public String save(Alunos alunos) {
		alunos.setAtivo(true);
		Alunos alunoSalvo = this.alunosRepository.save(alunos);
		
		if(alunoSalvo != null) {
		  return "Aluno salvo com sucesso!";
		}else {
		  throw new RuntimeException("Erro ao salvar aluno!");
		}
		
	}

	public String update(Alunos alunos, long id) {
		alunos.setId(id);
		Alunos alunoAtualizado = this.alunosRepository.save(alunos);
		if(alunoAtualizado != null) {
			  return "Aluno atualizado com sucesso!";
			}else {
			  throw new RuntimeException("Erro ao atualizar aluno!");
			}
	}
	
	public Page<Alunos> findAllPage(Pageable pageable) {
	    return this.alunosRepository.findAll(pageable);
	}

	public Alunos findById(long id) {

		Optional<Alunos> optional = this.alunosRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else
			return null;

	}

	public List<Alunos> findAll() {

		return this.alunosRepository.findAll();

	}

	public String delete(String ra) {

		Alunos aluno = this.alunosRepository.findByRa(ra);
	    long id = aluno.getId();
		aluno.setId(id);
		Emprestimos emp = new Emprestimos();
		emp.setAluno(aluno);
		List<Emprestimos> lista = this.encontrarEmprestimoEmAndamentoPorAluno(emp);

		// Verifica se há empréstimos em andamento
		if (lista != null && !lista.isEmpty()) {

			throw new RuntimeException("Aluno possui empréstimo em andamento.");

		} else {
			int alunoDesativado = this.alunosRepository.desativarAlunos(id);
		    if (alunoDesativado > 0) {
		        return "Aluno desativado com sucesso!";
		    } else {
		        throw new RuntimeException("Erro ao desativar aluno!");
		    }

		}

	}


	private List<Emprestimos> encontrarEmprestimoEmAndamentoPorAluno(Emprestimos emp) {
		Alunos aluno = new Alunos();
		aluno.setId(emp.getAluno().getId());
		List<Emprestimos> lista = this.emprestimosRepository.findByEmprestimosByAlunoAtivo(aluno);
		
		
		
		return lista;
	}

	
	public Alunos findByRa(String ra) {
	    AlunoUniamerica alunoUniamerica = this.alunoUniamericaService.findByRA(ra);
	    Alunos alunoLocal = alunosRepository.findByRa(ra.trim());
	    
	    if (alunoLocal == null) {
	    	Alunos novoAluno = new Alunos();
	        
	        novoAluno.setAtivo(true);
	        novoAluno.setCelular("(45) 11111-1111");
	        novoAluno.setCpf("008.398.349-00");
	        novoAluno.setCurso(alunoUniamerica.getCurso());

	        // Define a data de nascimento usando SimpleDateFormat
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date dataNascimento = null;
			try {
				dataNascimento = sdf.parse("2024-01-01");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        novoAluno.setDataNascimento(dataNascimento);
	        
	        novoAluno.setEmail("fulano@email.com");
	        novoAluno.setRa(alunoUniamerica.getRa());
	        novoAluno.setNome(alunoUniamerica.getNome());
	        novoAluno.setSenha("123");
	        novoAluno.setUsuario(alunoUniamerica.getNome());
	        
	        
	        novoAluno = this.alunosRepository.save(novoAluno);
	        
	        return novoAluno;
	        
	    } else {
	    	return alunoLocal; 
	    }
	}
	
	public List<Alunos> findByFilter(String ra, String nome, String curso) {
	    return this.alunosRepository.findByFilter(ra, nome, curso);
	}
	
	public Alunos findByCpf(String cpf) {
		return this.alunosRepository.findByCpf(cpf);
	}

	public List<Alunos> findByNome(String nome) {
	    return this.alunosRepository.findByNomeContains(nome);
	}
	

}