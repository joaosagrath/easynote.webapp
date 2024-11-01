package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.base2.AlunosUniamerica;

public interface AlunosUniamericaRepository extends JpaRepository<AlunosUniamerica, Long> {

    AlunosUniamerica findByRa(Long ra);

    

}
