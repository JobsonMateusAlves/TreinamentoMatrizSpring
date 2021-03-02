package com.example.Matriz.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Matriz.modelo.Semestre;

public class SemestreDto {

	private Long id;
	private int numeroSemestre;
	private List<DisciplinaDto> disciplinas = new ArrayList<>();
	
	public SemestreDto(Semestre semestre) {
		this.id = semestre.getId();
		System.out.println(semestre.getDisciplinas().size());
		this.numeroSemestre = semestre.getNumeroSemestre();
		this.disciplinas = DisciplinaDto.converter(semestre.getDisciplinas());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getNumeroSemestre() {
		return numeroSemestre;
	}
	public void setNumeroSemestre(int numeroSemestre) {
		this.numeroSemestre = numeroSemestre;
	}
	
	public List<DisciplinaDto> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<DisciplinaDto> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public static List<SemestreDto> converter(List<Semestre> semestres) {
		
		return semestres.stream().map(SemestreDto::new).collect(Collectors.toList()); 
	}	
}
