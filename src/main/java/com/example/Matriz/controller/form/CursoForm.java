package com.example.Matriz.controller.form;

import javax.validation.constraints.NotBlank;

import com.example.Matriz.modelo.Curso;

public class CursoForm {

	@NotBlank
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Curso converter() {
		
		return new Curso(nome);
	}
}
