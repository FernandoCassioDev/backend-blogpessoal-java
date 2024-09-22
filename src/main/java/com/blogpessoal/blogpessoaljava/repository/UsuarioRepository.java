package com.blogpessoal.blogpessoaljava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogpessoal.blogpessoaljava.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
  public Optional<Usuario> findByUsuario(String usuario);
}
