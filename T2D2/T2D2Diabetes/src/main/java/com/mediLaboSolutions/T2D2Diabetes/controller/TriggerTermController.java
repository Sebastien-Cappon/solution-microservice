package com.mediLaboSolutions.T2D2Diabetes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Trigger Terms.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class TriggerTermController {

	@Autowired
	private ITriggerTermService iTriggerTermService;

	/**
	 * A <code>GetMapping</code> method on the <code>/triggers</code> URI. It calls
	 * the eponymous <code>ITriggerTermService</code> method and returns a list of
	 * <code>TriggerTerm</code> model entities.
	 * 
	 * @frontCall <code>factor.service.ts</code> : <code>getFactors()</code>
	 * 
	 * @return A <code>TriggerTerm</code> list.
	 */
	@GetMapping("/triggers")
	public List<TriggerTerm> getTriggerTerms() {
		return iTriggerTermService.getTriggerTerms();
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/trigger</code> URI with a
	 * TriggerTerm model entity as <code>RequestBody</code>. It calls the eponymous
	 * <code>ITriggerTermService</code> method and returns the
	 * <code>TriggerTerm</code> added with status code 201. If the result is null,
	 * it returns status code 400.
	 * 
	 * @frontCall <code>factor.service.ts</code> : <code>addNewFactor(newFactor: Factor)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the trigger term already exist in the
	 * triggerterm table.
	 * 
	 * @return A <code>TriggerTerm</code> and a status code.
	 */
	@PostMapping("/trigger")
	public ResponseEntity<TriggerTerm> createTriggerTerm(@RequestBody TriggerTerm newTriggerTerm) throws Exception {
		TriggerTerm createdTriggerTerm = iTriggerTermService.createTriggerTerm(newTriggerTerm);

		if (createdTriggerTerm == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<TriggerTerm>(createdTriggerTerm, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/triggers</code> URI
	 * with the a trigger term id as <code>PathVariables</code>. It calls the
	 * eponymous <code>ITriggerTermService</code> method and returns nothing.
	 */
	@DeleteMapping("/triggers/{triggerTermId}")
	public void deleteTriggerTermById(@PathVariable("triggerTermId") int triggerTermId) {
		iTriggerTermService.deleteTriggerTermById(triggerTermId);
	}
}