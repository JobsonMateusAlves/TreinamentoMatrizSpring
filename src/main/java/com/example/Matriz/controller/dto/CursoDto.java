package com.example.Matriz.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Matriz.modelo.Curso;

public class CursoDto {
	private Long id;
	private String nome;
	
	public CursoDto(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
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
	
	public static List<CursoDto> converter(List<Curso> cursos) {
		
		return cursos.stream().map(CursoDto::new).collect(Collectors.toList()); 
	}
}
