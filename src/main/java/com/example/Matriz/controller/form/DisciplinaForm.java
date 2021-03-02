package com.example.Matriz.controller.form;

import javax.validation.constraints.NotBlank;

import com.example.Matriz.modelo.Disciplina;

public class DisciplinaForm {

	@NotBlank
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Disciplina converter() {
		
		return new Disciplina(nome);
	}
}
