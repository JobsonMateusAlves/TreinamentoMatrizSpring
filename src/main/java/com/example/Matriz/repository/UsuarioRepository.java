package com.example.Matriz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Matriz.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	List<Usuario> findByTipo(String tipo);
}
