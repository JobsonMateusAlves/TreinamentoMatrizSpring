package com.example.Matriz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Semestre {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numeroSemestre;
	@ManyToMany
	@JoinTable(
	  name = "semestre_disciplina", 
	  joinColumns = @JoinColumn(name = "semestre_id"), 
	  inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
	private List<Disciplina> disciplinas = new ArrayList<>();
	@ManyToOne
	private Curso curso;
	
	Semestre() {}
	
	public Semestre(int numeroSemestre, List<Disciplina> disciplinas) {
		super();
		this.numeroSemestre = numeroSemestre;
		this.disciplinas = disciplinas;
	}
	
	public Semestre(List<Disciplina> disciplinas) {
		super();
		this.disciplinas = disciplinas;
	}
	
	public Semestre(int numeroSemestre) {
		super();
		this.numeroSemestre = numeroSemestre;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public int getNumeroSemestre() {
		return numeroSemestre;
	}
	public void setNumeroSemestre(int numeroSemestre) {
		this.numeroSemestre = numeroSemestre;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
