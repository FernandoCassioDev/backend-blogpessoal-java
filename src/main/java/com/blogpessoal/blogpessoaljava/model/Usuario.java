package com.blogpessoal.blogpessoaljava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "O atributo Usuario é obrigatório!")
  @Email(message = "O atributo usuário deve ser um e-mail válido!")
  private String usuario;

  @NotBlank(message = "O atributo Senha é obrigatório!")
  @Size(min = 8, message = "A senha deve ter no minímo 8 caracteres")
  private String senha;

  @Size(max = 5000, message = "O link da foto não pode ser maior que 5000 caracteres")
  private String foto;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
  @JsonIgnoreProperties("usuario")
  private List<Postagem> postagens;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public List<Postagem> getPostagens() {
    return postagens;
  }

  public void setPostagens(List<Postagem> postagens) {
    this.postagens = postagens;
  }

  

}
