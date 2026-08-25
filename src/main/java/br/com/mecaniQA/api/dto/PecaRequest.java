package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.CategoriaPeca;
import java.math.BigDecimal;

public record PecaRequest(
		String codigoBarras,
		String fornecedorMarca,
		Integer quantidadeEstoque,
		BigDecimal precoCusto,
		BigDecimal precoVenda,
		String tamanho,
		String cor,
		CategoriaPeca categoria) {
}
