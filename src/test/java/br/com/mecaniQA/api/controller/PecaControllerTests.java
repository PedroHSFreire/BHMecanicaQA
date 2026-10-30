package br.com.mecaniQA.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.PecaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PecaControllerTests {
  private PecaService service;
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    service = mock(PecaService.class);
    mvc = MockMvcBuilders.standaloneSetup(new PecaController(service)).build();
  }

  @Test
  void recursoInexistenteRetorna404() throws Exception {
    when(service.buscar(99999L))
        .thenThrow(new RecursoNaoEncontradoException("Peça não encontrada"));
    mvc.perform(get("/api/pecas/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Peça não encontrada"));
  }
}
