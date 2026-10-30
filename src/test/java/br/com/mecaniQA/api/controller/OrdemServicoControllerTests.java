package br.com.mecaniQA.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class OrdemServicoControllerTests {
  private OrdemDeServicoService service;
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    service = mock(OrdemDeServicoService.class);
    mvc = MockMvcBuilders.standaloneSetup(new OrdemServicoController(service)).build();
  }

  @Test
  void excluirOrdemInexistenteRetorna404() throws Exception {
    doThrow(new RecursoNaoEncontradoException("Ordem de serviço não encontrada"))
        .when(service)
        .excluir(99999L);
    mvc.perform(delete("/api/ordens-servico/99999")).andExpect(status().isNotFound());
  }
}
