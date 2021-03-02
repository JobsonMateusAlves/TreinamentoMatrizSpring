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

import com.example.Matriz.controller.dto.DisciplinaDto;
import com.example.Matriz.controller.form.DisciplinaForm;
import com.example.Matriz.modelo.Disciplina;
import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;
import com.example.Matriz.repository.DisciplinaRepository;
import com.example.Matriz.repository.UsuarioRepository;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<DisciplinaDto>> listar(@RequestHeader("usuarioId") Long usuarioId) {

		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		List<Disciplina> disciplinas = this.disciplinaRepository.findAll();
		return ResponseEntity.ok(DisciplinaDto.converter(disciplinas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DisciplinaDto> detalhar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Disciplina> disciplinaOptional = this.disciplinaRepository.findById(id);
		
		if (disciplinaOptional.isPresent()) {
			return ResponseEntity.ok(new DisciplinaDto(disciplinaOptional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DisciplinaDto> cadastrar(@RequestHeader("usuarioId") Long usuarioId, @RequestBody @Valid DisciplinaForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Disciplina disciplina = form.converter();
		disciplinaRepository.save(disciplina);
		
		URI uri = uriBuilder.path("/disciplinas/{id}").buildAndExpand(disciplina.getId()).toUri();
		return ResponseEntity.created(uri).body(new DisciplinaDto(disciplina));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DisciplinaDto> atualizar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id, @RequestBody @Valid DisciplinaForm form) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Disciplina> disciplinaUpdate = this.disciplinaRepository.findById(id);
		if (disciplinaUpdate.isPresent()) {
			disciplinaUpdate.get().setNome(form.getNome());
			return ResponseEntity.ok().body(new DisciplinaDto(disciplinaUpdate.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DisciplinaDto> apagar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Disciplina> disciplinaDelete = this.disciplinaRepository.findById(id);
		
		if (disciplinaDelete.isPresent()) {
			this.disciplinaRepository.deleteById(id);
			
			return ResponseEntity.ok().body(new DisciplinaDto(disciplinaDelete.get()));
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
}
