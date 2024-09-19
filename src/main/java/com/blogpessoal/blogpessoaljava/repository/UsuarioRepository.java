package com.blogpessoal.blogpessoaljava.repository;

import java.util.Optional;

import com.blogpessoal.blogpessoaljava.model.Usuario;

public interface UsuarioRepository {
  public Optional<Usuario> findByUsuario(String usuario);
}
