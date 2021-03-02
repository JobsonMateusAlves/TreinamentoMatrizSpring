package com.example.Matriz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Matriz.modelo.Semestre;

public interface semestreRepository extends JpaRepository<Semestre, Long> {

	List<Semestre> findByCursoId(Long id);
}