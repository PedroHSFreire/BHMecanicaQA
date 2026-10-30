package br.com.mecaniQA.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.PedidoPecasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PedidoPecasControllerTests {
  private PedidoPecasService service;
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    service = mock(PedidoPecasService.class);
    mvc = MockMvcBuilders.standaloneSetup(new PedidoPecasController(service)).build();
  }

  @Test
  void excluirPedidoInexistenteRetorna404() throws Exception {
    doThrow(new RecursoNaoEncontradoException("Pedido de peças não encontrado"))
        .when(service)
        .excluir(99999L);
    mvc.perform(delete("/api/pedidos-pecas/99999")).andExpect(status().isNotFound());
  }
}
