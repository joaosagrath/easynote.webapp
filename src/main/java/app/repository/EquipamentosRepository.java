package app.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.entity.Equipamentos;
import jakarta.transaction.Transactional;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

	public Equipamentos findByPatrimonio(String patrimonio);
	
	public List<Equipamentos> findByMarca(String marca);
	
	public List<Equipamentos> findByModeloContains(String modelo);
	
	public List<Equipamentos> findBySituacao(String situacao);
	
	public List<Equipamentos> findByAtivoTrue();
	
	public List<Equipamentos> findByAtivoFalse();
	
	@Query("FROM Equipamentos e WHERE e.dataAquisicao BETWEEN :data1 AND :data2")
	public List<Equipamentos> findByDataAquisicao(LocalDate data1, LocalDate data2);
	
	@Modifying
	@Transactional
	@Query("UPDATE Equipamentos e SET e.ativo = false WHERE e.id = :id")
	public int desativarEquipamentos(long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Equipamentos e SET e.ativo = true WHERE e.id = :id")
	public int reativarEquipamentos(long id);
}