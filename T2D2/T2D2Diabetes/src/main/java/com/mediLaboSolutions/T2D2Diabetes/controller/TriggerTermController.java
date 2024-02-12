package com.mediLaboSolutions.T2D2Diabetes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TriggerTermController {

	@Autowired
	private ITriggerTermService iTriggerTermService;

	@GetMapping("/triggers")
	public List<TriggerTerm> getTriggerTerms() {
		return iTriggerTermService.getTriggerTerms();
	}

	@GetMapping("/triggers/{triggerTermId}")
	public TriggerTerm getTriggerTermById(@PathVariable("triggerTermId") int triggerTermId) {
		return iTriggerTermService.getTriggerTermById(triggerTermId);
	}

	@PostMapping("/trigger")
	public ResponseEntity<TriggerTerm> createTriggerTerm(@RequestBody TriggerTerm newTriggerTerm) throws Exception {
		TriggerTerm createdTriggerTerm = iTriggerTermService.createTriggerTerm(newTriggerTerm);

		if (createdTriggerTerm == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<TriggerTerm>(createdTriggerTerm, HttpStatus.CREATED);
		}
	}

	@PutMapping("/triggers/{triggerTermId}")
	public ResponseEntity<Integer> updateTriggerTermById(@PathVariable("triggerTermId") int triggerTermId, @RequestBody TriggerTerm updatedTriggerTerm) throws Exception {
		Integer isUpdated = iTriggerTermService.updateTriggerTermById(triggerTermId, updatedTriggerTerm);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/triggers/{triggerTermId}")
	public void deleteTriggerTermById(@PathVariable("triggerTermId") int triggerTermId) {
		iTriggerTermService.deleteTriggerTermById(triggerTermId);
	}
}