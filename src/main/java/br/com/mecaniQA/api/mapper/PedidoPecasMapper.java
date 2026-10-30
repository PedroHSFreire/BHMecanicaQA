package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.CriarPedidoPecasRequest;
import br.com.mecaniQA.api.dto.ItemPedidoResponse;
import br.com.mecaniQA.api.dto.PedidoPecasResponse;
import br.com.mecaniQA.api.model.PedidoPecas;

public final class PedidoPecasMapper {
  private PedidoPecasMapper() {}

  public static PedidoPecas toEntity(CriarPedidoPecasRequest dto) {
    return new PedidoPecas(dto.getFornecedor());
  }

  public static PedidoPecasResponse toResponse(PedidoPecas pedido) {
    return new PedidoPecasResponse(
        pedido.getCodigo(),
        pedido.getFornecedor(),
        pedido.getStatus(),
        pedido.getItens().stream()
            .map(
                item ->
                    new ItemPedidoResponse(
                        PecaMapper.toResponse(item.getPeca()), item.getQuantidade()))
            .toList(),
        pedido.getDataCriacao(),
        pedido.getDataUltimaAtualizacao());
  }
}
