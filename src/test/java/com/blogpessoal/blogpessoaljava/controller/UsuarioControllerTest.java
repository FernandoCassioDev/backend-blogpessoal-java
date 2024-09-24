package com.blogpessoal.blogpessoaljava.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blogpessoal.blogpessoaljava.model.Usuario;
import com.blogpessoal.blogpessoaljava.repository.UsuarioRepository;
import com.blogpessoal.blogpessoaljava.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @BeforeAll
  void start() {
    usuarioRepository.deleteAll();

    usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", "-"));

  }

  @Test
  @DisplayName("Cadastrar um usuário")
  public void deveCriarUmUsuario() {
    HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
        new Usuario(0L, "teste", "email@email.com", "12345678", "-"));

    ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
        corpoRequisicao, Usuario.class);

    assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
  }

  @Test
  @DisplayName("Não deve permitir duplicação de usuário")
  public void naodeveDuplicarUsuario() {

    usuarioService.cadastrarUsuario(new Usuario(0L, "joao da silva", "joaosilva@email.com", "12345678", "-"));

    HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
        new Usuario(0L, "joao da silva", "joaosilva@email.com", "12345678", "-"));

    ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
        corpoRequisicao, Usuario.class);

    assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
  }

  @Test
  @DisplayName("Deve atualizarum usuário")
  public void deveAtualizarUmUsuario() {

    Optional<Usuario> usuarioCadastrado = usuarioService
        .cadastrarUsuario(new Usuario(0L, "joe", "joe@email.com", "12345678", "-"));

    Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "joeee", "joeeeee@email.com", "12345678", "-");

    HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

    ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange(
        "/usuarios/atualizar", HttpMethod.PUT,
        corpoRequisicao, Usuario.class);

    assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
  }

}
