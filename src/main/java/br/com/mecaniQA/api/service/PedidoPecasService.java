package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.AtualizarStatusPedidoRequest;
import br.com.mecaniQA.api.dto.CriarPedidoPecasRequest;
import br.com.mecaniQA.api.dto.ItemPedidoRequest;
import br.com.mecaniQA.api.dto.PedidoPecasResponse;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.PedidoPecasMapper;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.repository.PecaJdbcRepository;
import br.com.mecaniQA.api.repository.PedidoPecasRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoPecasService {

  private final PedidoPecasRepository pedidoRepository;
  private final PecaJdbcRepository pecaRepository;

  public PedidoPecasService(
      PedidoPecasRepository pedidoRepository, PecaJdbcRepository pecaRepository) {
    this.pedidoRepository = pedidoRepository;
    this.pecaRepository = pecaRepository;
  }

  public PedidoPecasResponse criar(CriarPedidoPecasRequest request) {
    PedidoPecas pedido = PedidoPecasMapper.toEntity(request);
    for (ItemPedidoRequest item : request.getItens()) {
      pedido.adicionarItem(buscarPeca(item.getCodigoPeca()), item.getQuantidade());
    }
    return PedidoPecasMapper.toResponse(pedidoRepository.salvar(pedido));
  }

  public List<PedidoPecasResponse> listar() {
    return pedidoRepository.listarTodos().stream().map(PedidoPecasMapper::toResponse).toList();
  }

  public PedidoPecasResponse buscar(Long codigo) {
    return PedidoPecasMapper.toResponse(buscarPedido(codigo));
  }

  public PedidoPecasResponse adicionarItem(Long codigo, ItemPedidoRequest request) {
    buscarPedido(codigo);
    Peca peca = buscarPeca(request.getCodigoPeca());
    PedidoPecas pedido =
        pedidoRepository
            .adicionarItem(codigo, peca, request.getQuantidade())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido de peças não encontrado"));
    return PedidoPecasMapper.toResponse(pedido);
  }

  public PedidoPecasResponse atualizarStatus(Long codigo, AtualizarStatusPedidoRequest request) {
    PedidoPecas pedido =
        pedidoRepository
            .atualizarStatus(codigo, request.getStatus())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido de peças não encontrado"));
    return PedidoPecasMapper.toResponse(pedido);
  }

  public void excluir(Long codigo) {
    if (!pedidoRepository.excluir(codigo))
      throw new RecursoNaoEncontradoException("Pedido de peças não encontrado");
  }

  private PedidoPecas buscarPedido(Long codigo) {
    return pedidoRepository
        .buscarPorCodigo(codigo)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido de peças não encontrado"));
  }

  private Peca buscarPeca(Long codigo) {
    return pecaRepository
        .buscarPorCodigo(codigo)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada: " + codigo));
  }
}
