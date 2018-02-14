package edu.stevens.cs548.clinic.domain;

public interface IProviderFactory {
	public Provider creat(long pid, String name, String spec);
}
