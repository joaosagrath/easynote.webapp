package app.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Equipamentos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long patrimonio;
	private String marca;
	private String modelo;
	private Date dataAquisicao;
	private String observacao;
	private String situacao;
	private int ativo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties("equipamentos")
	private Emprestimos emprestimos;
	
	// getters & setters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(long patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Date getDataAquisicao() {
		return dataAquisicao;
	}
	public void setDataAquisicao(Date dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	
	
	
}
