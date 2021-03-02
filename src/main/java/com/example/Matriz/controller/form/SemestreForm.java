package com.example.Matriz.controller.form;

import com.example.Matriz.modelo.Semestre;
import com.example.Matriz.repository.DisciplinaRepository;

public class SemestreForm {
	
	private int numeroSemestre;

	public int getNumeroSemestre() {
		return numeroSemestre;
	}
	public void setNumeroSemestre(int numeroSemestre) {
		this.numeroSemestre = numeroSemestre;
	}
	
	public Semestre converter(DisciplinaRepository disciplinaRepository) {
		
//		List<Disciplina> disciplinasList = new ArrayList<>();
//		
//		
//		this.disciplinas.forEach( d -> {
//			
//			Optional<Disciplina> optional = d.converter(disciplinaRepository);
//			
//			if (optional.isPresent()) {
//				disciplinasList.add(optional.get());
//			}
//		});
//		
//		if (disciplinasList.size() != this.disciplinas.size()) {
//			return null;
//		}
//		System.out.println(disciplinasList);
		
		return new Semestre(this.numeroSemestre);
	}
}