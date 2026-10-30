package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusOrdemServico;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusOrdemServicoRequest {
  @NotNull(message = "é obrigatório")
  private StatusOrdemServico status;

  public AtualizarStatusOrdemServicoRequest() {}

  public StatusOrdemServico getStatus() {
    return status;
  }

  public void setStatus(StatusOrdemServico status) {
    this.status = status;
  }
}
