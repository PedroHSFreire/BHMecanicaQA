package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.ServicoRequest;
import br.com.mecaniQA.api.model.Servico;
import br.com.mecaniQA.api.repository.ServicoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
	private final ServicoRepository repository = ServicoRepository.getInstance();

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody ServicoRequest request) {
		String erro = validar(request);
		if (erro != null) {
			return ResponseEntity.badRequest().body(Map.of("erro", erro));
		}

		Servico criado = repository.create(toModel(request));
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}

	@GetMapping
	public ResponseEntity<List<Servico>> listar() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		return repository.findByCodigo(codigo)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("erro", "Serviço não encontrado.")));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<?> atualizar(@PathVariable Long codigo, @RequestBody ServicoRequest request) {
		String erro = validar(request);
		if (erro != null) {
			return ResponseEntity.badRequest().body(Map.of("erro", erro));
		}

		return repository.update(codigo, toModel(request))
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("erro", "Serviço não encontrado.")));
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remover(@PathVariable Long codigo) {
		boolean removido = repository.delete(codigo);
		if (!removido) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("erro", "Serviço não encontrado."));
		}
		return ResponseEntity.noContent().build();
	}

	private Servico toModel(ServicoRequest request) {
		Servico servico = new Servico();
		servico.setNomeServico(request.nomeServico());
		servico.setTempoEstimadoDuracaoMinutos(request.tempoEstimadoDuracaoMinutos());
		servico.setCustoTabelado(request.custoTabelado());
		return servico;
	}

	private String validar(ServicoRequest request) {
		if (request == null) {
			return "O corpo da requisição do serviço é obrigatório.";
		}
		if (estaEmBranco(request.nomeServico())) {
			return "nomeServico é obrigatório.";
		}
		if (request.tempoEstimadoDuracaoMinutos() == null || request.tempoEstimadoDuracaoMinutos() <= 0) {
			return "tempoEstimadoDuracaoMinutos deve ser informado e maior que zero.";
		}
		if (request.custoTabelado() == null || request.custoTabelado().compareTo(BigDecimal.ZERO) < 0) {
			return "custoTabelado deve ser informado e não pode ser negativo.";
		}
		return null;
	}

	private boolean estaEmBranco(String valor) {
		return valor == null || valor.isBlank();
	}
}
