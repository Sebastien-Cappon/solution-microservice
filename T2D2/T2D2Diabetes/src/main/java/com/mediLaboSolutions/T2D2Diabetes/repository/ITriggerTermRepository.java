package com.mediLaboSolutions.T2D2Diabetes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

@Repository
public interface ITriggerTermRepository extends JpaRepository<TriggerTerm, Integer> {

	Optional<TriggerTerm> findById(int triggerTermId);

	void deleteById(int triggerTermId);
}