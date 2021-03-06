package org.sitenv.spring.service;

import java.util.List;

import org.sitenv.spring.model.EncounterResource;
import org.sitenv.spring.model.ExtractionTask;

import ca.uhn.fhir.model.dstu2.resource.Encounter;

public interface EncounterResourceService {
	
	public EncounterResource saveOrUpdate(EncounterResource encounter);
	
	public List<EncounterResource> getAllEncounterResources();
	
	public EncounterResource getEncounterResourceById(Integer id);
	
	public List<EncounterResource> findDuplicatesBeforePersist(Encounter encounter, ExtractionTask et);
	
	public List<EncounterResource> getEncounterResourcesByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
}
