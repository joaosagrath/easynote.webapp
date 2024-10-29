package app.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Equipamentos;
import jakarta.transaction.Transactional;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

	public Equipamentos findByPatrimonio(String patrimonio);
	
	public List<Equipamentos> findByMarcaContains(String marca);
	
	public List<Equipamentos> findByModeloContains(String modelo);
	
	public List<Equipamentos> findBySituacao(String situacao);
	
	public List<Equipamentos> findByAtivoTrue();
	
	public List<Equipamentos> findByAtivoFalse();
	
	@Query("FROM Equipamentos e WHERE " +
		       "( :modelo = '' or e.modelo = :modelo ) AND " +
		       "( :marca = '' or e.marca = :marca ) AND " +
		       "( :situacao = '' or e.situacao = :situacao ) AND " +
		       "( :patrimonio = '' or e.patrimonio = :patrimonio )")
		public List<Equipamentos> findByFilter(
		       @Param("situacao") String situacao,
		       @Param("patrimonio") String patrimonio,
		       @Param("modelo") String modelo,
		       @Param("marca") String marca);
	
	@Query("FROM Equipamentos e WHERE e.dataAquisicao BETWEEN :data1 AND :data2")
	public List<Equipamentos> findByDataAquisicao(LocalDate data1, LocalDate data2);
	
	@Modifying
	@Transactional
	@Query("UPDATE Equipamentos e SET e.ativo = false, e.situacao = :situacao WHERE e.id = :id")
	public int desativarEquipamentos(long id, String situacao);
	
	@Modifying
	@Transactional
	@Query("UPDATE Equipamentos e SET e.ativo = true, e.situacao = :situacao WHERE e.id = :id")
	public int reativarEquipamentos(long id, String situacao);
}