package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.exception.ApiError;
import br.com.mecaniQA.api.exception.ErroDePersistenciaException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Tratamento explícito chamado pelos controllers, sem mecanismo global do Spring. */
final class ControllerSupport {
  private ControllerSupport() {}

  static <T> ResponseEntity<T> executar(Supplier<ResponseEntity<T>> acao, String caminho) {
    try {
      return acao.get();
    } catch (RecursoNaoEncontradoException e) {
      return erro(HttpStatus.NOT_FOUND, e.getMessage(), caminho);
    } catch (ErroDePersistenciaException e) {
      return erro(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao acessar o banco de dados", caminho);
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> ResponseEntity<T> erro(HttpStatus status, String mensagem, String caminho) {
    ApiError body =
        new ApiError(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            mensagem,
            caminho,
            Map.of());
    return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
  }
}
