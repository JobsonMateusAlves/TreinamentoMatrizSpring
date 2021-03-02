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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Matriz.controller.dto.SemestreDto;
import com.example.Matriz.controller.form.MontarSemestreForm;
import com.example.Matriz.controller.form.SemestreForm;
import com.example.Matriz.controller.form.SemestreUpdateForm;
import com.example.Matriz.modelo.Curso;
import com.example.Matriz.modelo.Semestre;
import com.example.Matriz.modelo.TipoUsuario;
import com.example.Matriz.modelo.Usuario;
import com.example.Matriz.repository.CursoRepository;
import com.example.Matriz.repository.DisciplinaRepository;
import com.example.Matriz.repository.UsuarioRepository;
import com.example.Matriz.repository.semestreRepository;

@RestController
@RequestMapping("/cursos/{cursoId}/semestres")
public class SemestreController {

	@Autowired
	private semestreRepository semestreRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<SemestreDto>> listar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		List<Semestre> semestres = this.semestreRepository.findByCursoId(cursoId);
		return ResponseEntity.ok(SemestreDto.converter(semestres));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SemestreDto> detalhar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Semestre> semestreOptional = this.semestreRepository.findById(id);
		
		if (semestreOptional.isPresent() && semestreOptional.get().getCurso().getId() == cursoId) {
			return ResponseEntity.ok(new SemestreDto(semestreOptional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<SemestreDto> cadastrar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId, @RequestBody @Valid SemestreForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Curso> cursoOptional = this.cursoRepository.findById(cursoId);
		
		if (cursoOptional.isPresent()) {
			Semestre semestre = form.converter(disciplinaRepository);
			
			semestre.setCurso(cursoOptional.get());
			semestreRepository.save(semestre);
			cursoOptional.get().addSemestre(semestre);
			
			URI uri = uriBuilder.path("/semestres/{id}").buildAndExpand(semestre.getId()).toUri();
			return ResponseEntity.created(uri).body(new SemestreDto(semestre));
		}
		
		
		return ResponseEntity.notFound().build();

	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<SemestreDto> atualizar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId, @PathVariable long id, @RequestBody @Valid SemestreUpdateForm form) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Semestre> semestreUpdate = this.semestreRepository.findById(id);
		
		if (semestreUpdate.isPresent() && semestreUpdate.get().getCurso().getId() == cursoId) {
			
			Semestre semestre = form.converter(disciplinaRepository);
			
			if (semestre.getNumeroSemestre() != 0) {
				semestreUpdate.get().setNumeroSemestre(semestre.getNumeroSemestre());
			}
			
			semestreUpdate.get().setDisciplinas(semestre.getDisciplinas());
			
			return ResponseEntity.ok().body(new SemestreDto(semestreUpdate.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<SemestreDto> montarSemestre(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId, @PathVariable long id, @RequestBody @Valid MontarSemestreForm form) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Semestre> semestreUpdate = this.semestreRepository.findById(id);
		
		if (semestreUpdate.isPresent() && semestreUpdate.get().getCurso().getId() == cursoId) {
			
			Semestre semestre = form.converter(disciplinaRepository);
			
			semestreUpdate.get().setDisciplinas(semestre.getDisciplinas());
			
			return ResponseEntity.ok().body(new SemestreDto(semestreUpdate.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<SemestreDto> apagar(@RequestHeader("usuarioId") Long usuarioId, @PathVariable long cursoId, @PathVariable long id) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		if (!usuario.isPresent() || usuario.get().getTipo() != TipoUsuario.COORDENADOR) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Semestre> semestreDelete = this.semestreRepository.findById(id);
		
		if (semestreDelete.isPresent() && semestreDelete.get().getCurso().getId() == cursoId) {
			this.semestreRepository.deleteById(id);
			
			return ResponseEntity.ok().body(new SemestreDto(semestreDelete.get()));
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
}
