package com.example.Matriz.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.Matriz.modelo.Disciplina;
import com.example.Matriz.modelo.Semestre;
import com.example.Matriz.repository.DisciplinaRepository;

public class SemestreUpdateForm {
	
	private int numeroSemestre;
	private List<DisciplinaSemestreForm> disciplinas = new ArrayList<>();
	
	public int getNumeroSemestre() {
		return numeroSemestre;
	}
	public void setNumeroSemestre(int numeroSemestre) {
		this.numeroSemestre = numeroSemestre;
	}
	
	public List<DisciplinaSemestreForm> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<DisciplinaSemestreForm> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public Semestre converter(DisciplinaRepository disciplinaRepository) {
		
		List<Disciplina> disciplinasList = new ArrayList<>();
		
		this.disciplinas.forEach( d -> {
			
			Optional<Disciplina> optional = d.converter(disciplinaRepository);
			
			if (optional.isPresent()) {
				disciplinasList.add(optional.get());
			}
		});
		
		return new Semestre(this.numeroSemestre, disciplinasList);
	}
}