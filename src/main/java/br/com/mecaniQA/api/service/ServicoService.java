package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.AtualizarServicoRequest;
import br.com.mecaniQA.api.dto.CriarServicoRequest;
import br.com.mecaniQA.api.dto.ServicoResponse;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.ServicoMapper;
import br.com.mecaniQA.api.model.Servico;
import br.com.mecaniQA.api.repository.ServicoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

  private final ServicoRepository repository;

  public ServicoService(ServicoRepository repository) {
    this.repository = repository;
  }

  public ServicoResponse criar(CriarServicoRequest request) {
    Servico servico = repository.salvar(ServicoMapper.toEntity(request));
    return ServicoMapper.toResponse(servico);
  }

  public List<ServicoResponse> listar() {
    return repository.listarTodos().stream().map(ServicoMapper::toResponse).toList();
  }

  public ServicoResponse buscar(Long codigo) {
    return ServicoMapper.toResponse(buscarEntidade(codigo));
  }

  public ServicoResponse atualizar(Long codigo, AtualizarServicoRequest request) {
    Servico servico =
        repository
            .atualizar(codigo, request.getDuracaoEstimadaMinutos(), request.getCustoTabelado())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
    return ServicoMapper.toResponse(servico);
  }

  public void excluir(Long codigo) {
    if (!repository.excluir(codigo)) {
      throw new RecursoNaoEncontradoException("Serviço não encontrado");
    }
  }

  private Servico buscarEntidade(Long codigo) {
    return repository
        .buscarPorCodigo(codigo)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
  }
}
