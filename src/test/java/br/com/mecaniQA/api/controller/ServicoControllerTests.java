package br.com.mecaniQA.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ServicoControllerTests {
  private ServicoService service;
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    service = mock(ServicoService.class);
    mvc = MockMvcBuilders.standaloneSetup(new ServicoController(service)).build();
  }

  @Test
  void servicoInexistenteRetorna404() throws Exception {
    when(service.buscar(99999L))
        .thenThrow(new RecursoNaoEncontradoException("Serviço não encontrado"));
    mvc.perform(get("/api/servicos/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Serviço não encontrado"));
  }
}
