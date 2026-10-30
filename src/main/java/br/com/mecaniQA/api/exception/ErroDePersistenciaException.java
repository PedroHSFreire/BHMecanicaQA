package br.com.mecaniQA.api.exception;

public class ErroDePersistenciaException extends RuntimeException {
  public ErroDePersistenciaException(String message, Throwable cause) {
    super(message, cause);
  }
}
