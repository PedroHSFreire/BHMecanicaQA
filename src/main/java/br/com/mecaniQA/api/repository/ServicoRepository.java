package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.model.Servico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class ServicoRepository {
	private static final ServicoRepository INSTANCE = new ServicoRepository();

	private final List<Servico> servicos = new ArrayList<>();
	private final AtomicLong sequencia = new AtomicLong(1L);

	private ServicoRepository() {
	}

	public static ServicoRepository getInstance() {
		return INSTANCE;
	}

	public synchronized Servico create(Servico servico) {
		Servico armazenado = new Servico(servico);
		armazenado.setCodigo(sequencia.getAndIncrement());
		LocalDateTime agora = LocalDateTime.now();
		armazenado.setDataCriacao(agora);
		armazenado.setDataUltimaAtualizacao(agora);
		servicos.add(armazenado);
		return new Servico(armazenado);
	}

	public synchronized List<Servico> findAll() {
		List<Servico> copia = new ArrayList<>();
		for (Servico servico : servicos) {
			copia.add(new Servico(servico));
		}
		return copia;
	}

	public synchronized Optional<Servico> findByCodigo(Long codigo) {
		return servicos.stream()
				.filter(servico -> Objects.equals(servico.getCodigo(), codigo))
				.findFirst()
				.map(Servico::new);
	}

	public synchronized Optional<Servico> update(Long codigo, Servico dadosAtualizados) {
		for (Servico servico : servicos) {
			if (Objects.equals(servico.getCodigo(), codigo)) {
				servico.setNomeServico(dadosAtualizados.getNomeServico());
				servico.setTempoEstimadoDuracaoMinutos(dadosAtualizados.getTempoEstimadoDuracaoMinutos());
				servico.setCustoTabelado(dadosAtualizados.getCustoTabelado());
				servico.setDataUltimaAtualizacao(LocalDateTime.now());
				return Optional.of(new Servico(servico));
			}
		}
		return Optional.empty();
	}

	public synchronized boolean delete(Long codigo) {
		return servicos.removeIf(servico -> Objects.equals(servico.getCodigo(), codigo));
	}
}
