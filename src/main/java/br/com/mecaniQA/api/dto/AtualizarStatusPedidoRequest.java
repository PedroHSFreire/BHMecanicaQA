package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusPedidoPecas;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusPedidoRequest {
  @NotNull(message = "é obrigatório")
  private StatusPedidoPecas status;

  public AtualizarStatusPedidoRequest() {}

  public StatusPedidoPecas getStatus() {
    return status;
  }

  public void setStatus(StatusPedidoPecas status) {
    this.status = status;
  }
}
