package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Emprestimos;

public interface EmprestimosRepository extends JpaRepository<Emprestimos, Long>{

}
