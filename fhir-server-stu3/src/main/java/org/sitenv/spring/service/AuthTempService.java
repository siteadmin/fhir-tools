package org.sitenv.spring.service;

import java.util.List;

import org.sitenv.spring.model.DafAuthtemp;

public interface AuthTempService {

	public DafAuthtemp saveOrUpdate(DafAuthtemp auth);

	public DafAuthtemp getAuthByClientId(String clientId, String clientSecret);

	public List<DafAuthtemp> getList();

	public DafAuthtemp validateAccessToken(String accessToken);

	public DafAuthtemp getAuthenticationById(String transactionId);

	public DafAuthtemp getAuthById(String clientId);

	public DafAuthtemp validateIdToken(String idToken);

}
