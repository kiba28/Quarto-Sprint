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

import avaliacao.services.AssociateService;
import avaliacao.services.dto.AssociateDto;
import avaliacao.services.dto.AssociateFormDto;
import avaliacao.services.dto.AssociatePartyFormDto;

@RestController
@RequestMapping(value = "/associados")
public class AssociateController {

	@Autowired
	private AssociateService service;

	@GetMapping
	public ResponseEntity<List<AssociateDto>> findAll(@RequestParam(required = false) String politicalOffice) {
		if (politicalOffice == null) {
			List<AssociateDto> list = service.findAll();
			return ResponseEntity.ok(list);
		}
		List<AssociateDto> list = service.findByPoliticalOffice(politicalOffice);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AssociateDto> findById(@PathVariable long id) {
		AssociateDto obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<AssociateDto> insert(@RequestBody @Valid AssociateFormDto form) {
		AssociateDto saved = service.insert(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<AssociateDto> update(@PathVariable Long id, @RequestBody @Valid AssociateFormDto form) {
		AssociateDto updated = service.update(id, form);
		return ResponseEntity.ok(updated);
	}

	@PutMapping(value = "/partidos")
	@Transactional
	public ResponseEntity<AssociateDto> updateAssociateParty(@RequestBody @Valid AssociatePartyFormDto form) {
		AssociateDto updated = service.updatePoliticalParty(form);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> deleteById(@PathVariable long id) {
		service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}/partidos/{id2}")
	@Transactional
	public ResponseEntity<Void> deleteAssociateFromParty(@PathVariable long id, @PathVariable long id2) {
		service.deleteAssociateFromParty(id, id2);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
