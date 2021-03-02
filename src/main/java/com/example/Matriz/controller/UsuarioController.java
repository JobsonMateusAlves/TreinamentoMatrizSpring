package com.example.Matriz.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Matriz.controller.dto.UsuarioDto;
import com.example.Matriz.controller.form.UsuarioForm;
import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;
import com.example.Matriz.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDto>> listar(@RequestHeader("usuarioId") Long usuarioId, String tipo) {
		
		Optional<Usuario> usuarioAuth = this.usuarioRepository.findById(usuarioId);
		
		if (!usuarioAuth.isPresent() || usuarioAuth.get().getTipo() != TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		if (tipo == null) {
			List<Usuario> usuarios = this.usuarioRepository.findAll();
			return ResponseEntity.ok(UsuarioDto.converter(usuarios));
		}
		
		List<Usuario> usuarios = this.usuarioRepository.findByTipo(tipo);
		return ResponseEntity.ok(UsuarioDto.converter(usuarios));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> detalhar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuarioAuth = this.usuarioRepository.findById(usuarioId);
		
		if (!usuarioAuth.isPresent() || usuarioAuth.get().getTipo() != TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(id);
		
		if (usuarioOptional.isPresent()) {
			return ResponseEntity.ok(new UsuarioDto(usuarioOptional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> cadastrar(@RequestHeader("usuarioId") Long usuarioId, @RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {

		Optional<Usuario> usuarioAuth = this.usuarioRepository.findById(usuarioId);
		
		if (!usuarioAuth.isPresent() || usuarioAuth.get().getTipo() != TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Usuario usuario = form.converter();
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> atualizar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id, @RequestBody @Valid UsuarioForm form) {
		
		Optional<Usuario> usuarioAuth = this.usuarioRepository.findById(usuarioId);
		
		if (!usuarioAuth.isPresent() || usuarioAuth.get().getTipo() != TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Usuario> usuarioUpdate = this.usuarioRepository.findById(id);
		if (usuarioUpdate.isPresent()) {
			usuarioUpdate.get().setUsuario(form);
			return ResponseEntity.ok().body(new UsuarioDto(usuarioUpdate.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> apagar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuarioAuth = this.usuarioRepository.findById(usuarioId);
		
		if (!usuarioAuth.isPresent() || usuarioAuth.get().getTipo() != TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Usuario> usuarioDelete = this.usuarioRepository.findById(id);
		
		if (usuarioDelete.isPresent()) {
			this.usuarioRepository.deleteById(id);
			
			return ResponseEntity.ok().body(new UsuarioDto(usuarioDelete.get()));
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
}
