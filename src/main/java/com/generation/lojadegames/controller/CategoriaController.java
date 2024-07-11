package com.generation.lojadegames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.lojadegames.model.Categoria;
import com.generation.lojadegames.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			return ResponseEntity.ok(categoriaOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public Categoria criarCategoria(@RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id,
			@RequestBody Categoria categoriaAtualizada) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();
			categoria.setNome(categoriaAtualizada.getNome());
			categoria.setDescricao(categoriaAtualizada.getDescricao());

			Categoria categoriaAtualizadaEntity = categoriaRepository.save(categoria);
			return ResponseEntity.ok(categoriaAtualizadaEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			categoriaRepository.delete(categoriaOptional.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/nome/{nome}")
	public List<Categoria> buscarCategoriasPorNome(@PathVariable String nome) {
		return categoriaRepository.findByNomeContaining(nome);
	}
}
