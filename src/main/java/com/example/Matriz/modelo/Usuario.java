package com.example.Matriz.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;

import com.example.Matriz.controller.form.UsuarioForm;

@Entity
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;
	
	Usuario() {}
	
	public Usuario(String nome, TipoUsuario tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public void setUsuario(@Valid UsuarioForm usuario) {

		this.setNome(usuario.getNome());
		this.setTipo(TipoUsuario.valueOf(usuario.getTipo()));
	}

}
