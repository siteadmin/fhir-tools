package org.sitenv.spring.dao;

import java.util.List;

import org.sitenv.spring.model.DafProcedure;
import org.sitenv.spring.util.SearchParameterMap;

public interface ProcedureDao {
	
	 public DafProcedure getProcedureById(int id);
	 
	 public DafProcedure getProcedureByVersionId(int theId, String versionId);
	 
	 public List<DafProcedure> search(SearchParameterMap theMap);
	 
	 public List<DafProcedure> getProcedureHistoryById(int theId);
}