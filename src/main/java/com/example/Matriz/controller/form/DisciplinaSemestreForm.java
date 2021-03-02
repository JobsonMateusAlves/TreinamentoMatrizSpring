package com.example.Matriz.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.example.Matriz.modelo.Disciplina;
import com.example.Matriz.repository.DisciplinaRepository;

public class DisciplinaSemestreForm {

	@NotBlank
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Optional<Disciplina> converter(DisciplinaRepository disciplinaRepository) {
		
		return disciplinaRepository.findById(this.id);
	}
}
