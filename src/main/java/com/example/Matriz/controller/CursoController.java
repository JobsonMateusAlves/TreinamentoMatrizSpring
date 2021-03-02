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

import com.example.Matriz.controller.dto.CursoDto;
import com.example.Matriz.controller.dto.DetalheCursoDto;
import com.example.Matriz.controller.form.CursoForm;
import com.example.Matriz.modelo.Curso;
import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;
import com.example.Matriz.repository.CursoRepository;
import com.example.Matriz.repository.UsuarioRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<CursoDto>> listar(@RequestHeader("usuarioId") Long usuarioId) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() == TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		List<Curso> cursos = this.cursoRepository.findAll();
		return ResponseEntity.ok(CursoDto.converter(cursos));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalheCursoDto> detalhar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() == TipoUsuario.ADMININASTRO) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
		
		System.out.println(cursoOptional.get().getSemestres().size());
		
		if (cursoOptional.isPresent()) {
			return ResponseEntity.ok(new DetalheCursoDto(cursoOptional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CursoDto> cadastrar(@RequestHeader("usuarioId") Long usuarioId, @RequestBody @Valid CursoForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Curso curso = form.converter();
		cursoRepository.save(curso);
		
		URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(uri).body(new CursoDto(curso));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CursoDto> atualizar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id, @RequestBody @Valid CursoForm form) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Curso> cursoUpdate = this.cursoRepository.findById(id);
		if (cursoUpdate.isPresent()) {
			cursoUpdate.get().setNome(form.getNome());
			return ResponseEntity.ok().body(new CursoDto(cursoUpdate.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<CursoDto> apagar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Curso> cursoDelete = this.cursoRepository.findById(id);
		
		if (cursoDelete.isPresent()) {
			this.cursoRepository.deleteById(id);
			
			return ResponseEntity.ok().body(new CursoDto(cursoDelete.get()));
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
}
