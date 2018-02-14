package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider creat(long pid, String name, String spec) {
		Provider p = new Provider();
		p.setProvider_id(pid);
		p.setName(name);
		p.setSpec(spec);
		return p;
	}

}
