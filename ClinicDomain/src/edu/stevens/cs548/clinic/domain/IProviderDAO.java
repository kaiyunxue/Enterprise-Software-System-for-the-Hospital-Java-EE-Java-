package edu.stevens.cs548.clinic.domain;

public interface IProviderDAO {
	public static class ProviderExn extends Exception {
		private static final long serialVersionUID = 1L;
		public ProviderExn (String msg) {
			super(msg);
		}
	}

	public long addProvider (Provider p) throws ProviderExn;
	//pid==>NPI
	public Provider getProviderByProviderId (long pid) throws ProviderExn;
    //retriProvider£¨long pid)
	public Provider getProvider (long id) throws ProviderExn;
}
