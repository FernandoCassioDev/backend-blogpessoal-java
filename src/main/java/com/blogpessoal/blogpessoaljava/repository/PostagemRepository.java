package com.blogpessoal.blogpessoaljava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogpessoal.blogpessoaljava.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long>{}
