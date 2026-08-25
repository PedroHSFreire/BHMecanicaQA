package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.PecaRequest;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.repository.PecaRepository;
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
@RequestMapping("/api/pecas")
public class PecaController {
	private final PecaRepository repository = PecaRepository.getInstance();

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody PecaRequest request) {
		String erro = validar(request);
		if (erro != null) {
			return ResponseEntity.badRequest().body(Map.of("erro", erro));
		}

		Peca criada = repository.create(toModel(request));
		return ResponseEntity.status(HttpStatus.CREATED).body(criada);
	}

	@GetMapping
	public ResponseEntity<List<Peca>> listar() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo) {
		return repository.findByCodigo(codigo)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("erro", "Peça não encontrada.")));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<?> atualizar(@PathVariable Long codigo, @RequestBody PecaRequest request) {
		String erro = validar(request);
		if (erro != null) {
			return ResponseEntity.badRequest().body(Map.of("erro", erro));
		}

		return repository.update(codigo, toModel(request))
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("erro", "Peça não encontrada.")));
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remover(@PathVariable Long codigo) {
		boolean removida = repository.delete(codigo);
		if (!removida) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("erro", "Peça não encontrada."));
		}
		return ResponseEntity.noContent().build();
	}

	private Peca toModel(PecaRequest request) {
		Peca peca = new Peca();
		peca.setCodigoBarras(request.codigoBarras());
		peca.setFornecedorMarca(request.fornecedorMarca());
		peca.setQuantidadeEstoque(request.quantidadeEstoque());
		peca.setPrecoCusto(request.precoCusto());
		peca.setPrecoVenda(request.precoVenda());
		peca.setTamanho(request.tamanho());
		peca.setCor(request.cor());
		peca.setCategoria(request.categoria());
		return peca;
	}

	private String validar(PecaRequest request) {
		if (request == null) {
			return "O corpo da requisição da peça é obrigatório.";
		}
		if (estaEmBranco(request.codigoBarras())) {
			return "codigoBarras é obrigatório.";
		}
		if (estaEmBranco(request.fornecedorMarca())) {
			return "fornecedorMarca é obrigatório.";
		}
		if (request.quantidadeEstoque() == null || request.quantidadeEstoque() < 0) {
			return "quantidadeEstoque deve ser informado e não pode ser negativo.";
		}
		if (request.precoCusto() == null || request.precoCusto().compareTo(BigDecimal.ZERO) < 0) {
			return "precoCusto deve ser informado e não pode ser negativo.";
		}
		if (request.precoVenda() == null || request.precoVenda().compareTo(BigDecimal.ZERO) < 0) {
			return "precoVenda deve ser informado e não pode ser negativo.";
		}
		if (request.categoria() == null) {
			return "categoria é obrigatória.";
		}
		return null;
	}

	private boolean estaEmBranco(String valor) {
		return valor == null || valor.isBlank();
	}
}
