package util.cryptography;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;

public class TestProvider {

	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			Provider provider = providers[i];
			System.out.println("Provider name: " + provider.getName());
			System.out.println("Provider information: " + provider.getInfo());
			System.out.println("Provider version: " + provider.getVersion());
			
			Set entries = provider.entrySet();
			Iterator iterator = entries.iterator();
			while (iterator.hasNext()) {
				System.out.println("Property entry: " + iterator.next());
			}
		}
	}

}
