package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;

public class ProviderDtoFactory {

	ObjectFactory factory;
	
	public ProviderDtoFactory(){
		factory = new ObjectFactory();
	}
	
	public ProviderDto createProviderDto () {
		return factory.createProviderDto();
	}
	
	public ProviderDto createProviderDto (Provider p) {
		ProviderDto pd = factory.createProviderDto();
		pd.setId(p.getId());
		pd.setName(p.getName());
		pd.setProviderId(p.getProvider_id());
		pd.setSpec(p.getSpec());
		return pd;
	}
	
}
