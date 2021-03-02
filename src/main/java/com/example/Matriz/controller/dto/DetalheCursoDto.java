package com.example.Matriz.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Matriz.modelo.Curso;

public class DetalheCursoDto {
	private Long id;
	private String nome;
	private List<SemestreDto> semestres = new ArrayList<>();
	
	public DetalheCursoDto(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.setSemestres(SemestreDto.converter(curso.getSemestres()));
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
	
	public List<SemestreDto> getSemestres() {
		return semestres;
	}
	public void setSemestres(List<SemestreDto> semestres) {
		this.semestres = semestres;
	}
	
	public static List<CursoDto> converter(List<Curso> cursos) {
		
		return cursos.stream().map(CursoDto::new).collect(Collectors.toList()); 
	}	
}
