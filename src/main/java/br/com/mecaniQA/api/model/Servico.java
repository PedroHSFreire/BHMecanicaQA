package br.com.mecaniQA.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Servico {
	private Long codigo;
	private String nomeServico;
	private Integer tempoEstimadoDuracaoMinutos;
	private BigDecimal custoTabelado;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataUltimaAtualizacao;

	public Servico() {
	}

	public Servico(Long codigo, String nomeServico, Integer tempoEstimadoDuracaoMinutos, BigDecimal custoTabelado,
			LocalDateTime dataCriacao, LocalDateTime dataUltimaAtualizacao) {
		this.codigo = codigo;
		this.nomeServico = nomeServico;
		this.tempoEstimadoDuracaoMinutos = tempoEstimadoDuracaoMinutos;
		this.custoTabelado = custoTabelado;
		this.dataCriacao = dataCriacao;
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Servico(Servico outro) {
		this(
				outro.codigo,
				outro.nomeServico,
				outro.tempoEstimadoDuracaoMinutos,
				outro.custoTabelado,
				outro.dataCriacao,
				outro.dataUltimaAtualizacao);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public Integer getTempoEstimadoDuracaoMinutos() {
		return tempoEstimadoDuracaoMinutos;
	}

	public void setTempoEstimadoDuracaoMinutos(Integer tempoEstimadoDuracaoMinutos) {
		this.tempoEstimadoDuracaoMinutos = tempoEstimadoDuracaoMinutos;
	}

	public BigDecimal getCustoTabelado() {
		return custoTabelado;
	}

	public void setCustoTabelado(BigDecimal custoTabelado) {
		this.custoTabelado = custoTabelado;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
}
