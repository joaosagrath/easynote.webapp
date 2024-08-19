package app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Equipamentos;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

	public Equipamentos findByPatrimonio(String patrimonio);
	
	public List<Equipamentos> findByMarca(String marca);
	
	public List<Equipamentos> findByModeloContains(String modelo);
	
	public List<Equipamentos> findBySituacao(String situacao);
	
	@Query("FROM Equipamentos e WHERE e.dataAquisicao BETWEEN :data1 AND :data2")
	public List<Equipamentos> findByDataAquisicao(Date data1, Date data2);
}