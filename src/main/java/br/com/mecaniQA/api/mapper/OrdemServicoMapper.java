package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.CriarOrdemServicoRequest;
import br.com.mecaniQA.api.dto.OrdemServicoResponse;
import br.com.mecaniQA.api.model.OrdemDeServico;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.model.Servico;
import java.util.List;

public final class OrdemServicoMapper {
  private OrdemServicoMapper() {}

  public static OrdemDeServico toEntity(
      CriarOrdemServicoRequest dto, List<Peca> pecas, List<Servico> servicos) {
    return OrdemDeServico.builder()
        .cliente(dto.getCliente())
        .veiculo(dto.getVeiculo())
        .descricao(dto.getDescricao())
        .pecas(pecas)
        .servicos(servicos)
        .build();
  }

  public static OrdemServicoResponse toResponse(OrdemDeServico ordem) {
    return new OrdemServicoResponse(
        ordem.getCodigo(),
        ordem.getCliente(),
        ordem.getVeiculo(),
        ordem.getDescricao(),
        ordem.getStatus(),
        ordem.getPecas().stream().map(PecaMapper::toResponse).toList(),
        ordem.getServicos().stream().map(ServicoMapper::toResponse).toList(),
        ordem.getDataCriacao(),
        ordem.getDataUltimaAtualizacao());
  }
}
