package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Equipamentos;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

}