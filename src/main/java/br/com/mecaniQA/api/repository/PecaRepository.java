package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.model.Peca;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class PecaRepository {
	private static final PecaRepository INSTANCE = new PecaRepository();

	private final List<Peca> pecas = new ArrayList<>();
	private final AtomicLong sequencia = new AtomicLong(1L);

	private PecaRepository() {
	}

	public static PecaRepository getInstance() {
		return INSTANCE;
	}

	public synchronized Peca create(Peca peca) {
		Peca armazenada = new Peca(peca);
		armazenada.setCodigo(sequencia.getAndIncrement());
		LocalDateTime agora = LocalDateTime.now();
		armazenada.setDataCadastro(agora);
		armazenada.setDataUltimaAtualizacao(agora);
		pecas.add(armazenada);
		return new Peca(armazenada);
	}

	public synchronized List<Peca> findAll() {
		List<Peca> copia = new ArrayList<>();
		for (Peca peca : pecas) {
			copia.add(new Peca(peca));
		}
		return copia;
	}

	public synchronized Optional<Peca> findByCodigo(Long codigo) {
		return pecas.stream()
				.filter(peca -> Objects.equals(peca.getCodigo(), codigo))
				.findFirst()
				.map(Peca::new);
	}

	public synchronized Optional<Peca> update(Long codigo, Peca dadosAtualizados) {
		for (Peca peca : pecas) {
			if (Objects.equals(peca.getCodigo(), codigo)) {
				peca.setCodigoBarras(dadosAtualizados.getCodigoBarras());
				peca.setFornecedorMarca(dadosAtualizados.getFornecedorMarca());
				peca.setQuantidadeEstoque(dadosAtualizados.getQuantidadeEstoque());
				peca.setPrecoCusto(dadosAtualizados.getPrecoCusto());
				peca.setPrecoVenda(dadosAtualizados.getPrecoVenda());
				peca.setTamanho(dadosAtualizados.getTamanho());
				peca.setCor(dadosAtualizados.getCor());
				peca.setCategoria(dadosAtualizados.getCategoria());
				peca.setDataUltimaAtualizacao(LocalDateTime.now());
				return Optional.of(new Peca(peca));
			}
		}
		return Optional.empty();
	}

	public synchronized boolean delete(Long codigo) {
		return pecas.removeIf(peca -> Objects.equals(peca.getCodigo(), codigo));
	}
}
