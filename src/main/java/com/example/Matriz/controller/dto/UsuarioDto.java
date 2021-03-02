package com.example.Matriz.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Matriz.controller.dto.UsuarioDto;
import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;

public class UsuarioDto {

	private Long id;
	private String nome;
	private TipoUsuario tipo;
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.tipo = usuario.getTipo();
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
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList()); 
	}
}
