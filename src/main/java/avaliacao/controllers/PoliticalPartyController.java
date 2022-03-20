package avaliacao.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import avaliacao.services.PoliticalPartyService;
import avaliacao.services.dto.PoliticalPartyDto;
import avaliacao.services.dto.PoliticalPartyFormDto;
import avaliacao.services.dto.PoliticalPartyWithAssociatesDto;

@RestController
@RequestMapping(value = "/partidos")
public class PoliticalPartyController {

	@Autowired
	private PoliticalPartyService service;

	@GetMapping
	public ResponseEntity<List<PoliticalPartyDto>> findAll(@RequestParam(required = false) String ideology) {
		if (ideology == null) {
			List<PoliticalPartyDto> list = service.findAll();
			return ResponseEntity.ok().body(list);
		}
		List<PoliticalPartyDto> list = service.findByIdeology(ideology);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PoliticalPartyDto> findById(@PathVariable long id) {
		PoliticalPartyDto obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/associados")
	public ResponseEntity<PoliticalPartyWithAssociatesDto> findByIdWithAssociates(@PathVariable long id) {
		PoliticalPartyWithAssociatesDto obj = service.findByIdWithAssociates(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<PoliticalPartyDto> insert(@RequestBody @Valid PoliticalPartyFormDto form) {
		PoliticalPartyDto saved = service.insert(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<PoliticalPartyDto> update(@PathVariable long id, @RequestBody @Valid PoliticalPartyFormDto form) {
		PoliticalPartyDto updated = service.update(id, form);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> deleteById(@PathVariable long id) {
		service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
