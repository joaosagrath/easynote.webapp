package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.base2.AlunosUniamerica;
import app.service.AlunosUniamericaService;

@Validated
@RestController
@RequestMapping("/api/alunosunimaerica")
@CrossOrigin("*")
public class AlunosUniamericaController {
	
	@Autowired
	private AlunosUniamericaService alunosUniamericaService;
	
	@GetMapping("/findByRa")
	public ResponseEntity<AlunosUniamerica> findByRa(@RequestParam Long ra) { // Altere para Long
		try {
			AlunosUniamerica aluno = this.alunosUniamericaService.findByRa(ra);
			if (aluno != null) {
				return new ResponseEntity<>(aluno, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Retorna NOT_FOUND se não encontrar
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
