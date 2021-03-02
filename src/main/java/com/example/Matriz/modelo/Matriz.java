package com.example.Matriz.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Matriz {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int qtdSemestres;
	
	public int getQtdSemestres() {
		return qtdSemestres;
	}
	public void setQtdSemestres(int qtdSemestres) {
		this.qtdSemestres = qtdSemestres;
	}
}
