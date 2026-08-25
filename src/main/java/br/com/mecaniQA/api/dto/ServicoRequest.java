package br.com.mecaniQA.api.dto;

import java.math.BigDecimal;

public record ServicoRequest(
		String nomeServico,
		Integer tempoEstimadoDuracaoMinutos,
		BigDecimal custoTabelado) {
}
