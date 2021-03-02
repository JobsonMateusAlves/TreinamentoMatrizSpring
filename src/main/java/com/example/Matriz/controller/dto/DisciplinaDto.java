package com.example.Matriz.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Matriz.modelo.Disciplina;

public class DisciplinaDto {

	private Long id;
	private String nome;
	
	public DisciplinaDto(Disciplina disciplina) {
		this.id = disciplina.getId();
		this.nome = disciplina.getNome();
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
	
	public static List<DisciplinaDto> converter(List<Disciplina> disciplinas) {
		
		return disciplinas.stream().map(DisciplinaDto::new).collect(Collectors.toList()); 
	}
}
