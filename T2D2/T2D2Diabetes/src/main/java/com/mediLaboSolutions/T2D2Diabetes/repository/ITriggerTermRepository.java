package com.mediLaboSolutions.T2D2Diabetes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>TriggerTerm</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface ITriggerTermRepository extends JpaRepository<TriggerTerm, Integer> {

	Optional<TriggerTerm> findById(int triggerTermId);

	List<TriggerTerm> findByOrderByTermAsc();

	void deleteById(int triggerTermId);
}