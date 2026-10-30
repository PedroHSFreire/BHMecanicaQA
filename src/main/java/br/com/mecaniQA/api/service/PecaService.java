package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.AtualizarPecaRequest;
import br.com.mecaniQA.api.dto.CriarPecaRequest;
import br.com.mecaniQA.api.dto.PecaResponse;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.PecaMapper;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.repository.PecaJdbcRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PecaService {

  private final PecaJdbcRepository repository;

  public PecaService(PecaJdbcRepository repository) {
    this.repository = repository;
  }

  public PecaResponse criar(CriarPecaRequest request) {
    Peca peca = repository.salvar(PecaMapper.toEntity(request));
    return PecaMapper.toResponse(peca);
  }

  public List<PecaResponse> listar() {
    return repository.listarTodos().stream().map(PecaMapper::toResponse).toList();
  }

  public PecaResponse buscar(Long codigo) {
    return PecaMapper.toResponse(buscarEntidade(codigo));
  }

  public PecaResponse atualizar(Long codigo, AtualizarPecaRequest request) {
    Peca peca =
        repository
            .atualizar(
                codigo,
                request.getQuantidadeEstoque(),
                request.getPrecoCusto(),
                request.getPrecoVenda())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada"));
    return PecaMapper.toResponse(peca);
  }

  public void excluir(Long codigo) {
    if (!repository.excluir(codigo)) {
      throw new RecursoNaoEncontradoException("Peça não encontrada");
    }
  }

  private Peca buscarEntidade(Long codigo) {
    return repository
        .buscarPorCodigo(codigo)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada"));
  }
}
