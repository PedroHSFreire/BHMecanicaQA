package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.CriarServicoRequest;
import br.com.mecaniQA.api.dto.ServicoResponse;
import br.com.mecaniQA.api.model.Servico;

public final class ServicoMapper {
  private ServicoMapper() {}

  public static Servico toEntity(CriarServicoRequest dto) {
    return new Servico(dto.getNome(), dto.getDuracaoEstimadaMinutos(), dto.getCustoTabelado());
  }

  public static ServicoResponse toResponse(Servico servico) {
    return new ServicoResponse(
        servico.getCodigo(),
        servico.getNome(),
        servico.getDuracaoEstimadaMinutos(),
        servico.getCustoTabelado(),
        servico.getDataCriacao(),
        servico.getDataUltimaAtualizacao());
  }
}
