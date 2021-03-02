package com.example.Matriz.controller.form;

import javax.validation.constraints.NotBlank;

import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;

public class UsuarioForm {
	
	@NotBlank
	private String nome;
	@NotBlank
	private String tipo;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Usuario converter() {
		
		return new Usuario(nome, TipoUsuario.valueOf(this.tipo));
	}
}
