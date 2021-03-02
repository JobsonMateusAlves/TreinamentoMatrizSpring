package com.example.Matriz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Curso {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@OneToMany(mappedBy="curso")
	private List<Semestre> semestres = new ArrayList<>();
	
	Curso() {}
	
	public Curso(String nome) {
		super();
		this.nome = nome;
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
	
	public List<Semestre> getSemestres() {
		return semestres;
	}
	public void setSemestres(List<Semestre> semestres) {
		this.semestres = semestres;
	}
	
	public void addSemestre(Semestre semestre) {
		this.semestres.add(semestre);
	}
}
