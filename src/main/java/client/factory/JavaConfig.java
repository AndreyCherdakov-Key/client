package client.factory;

import java.util.Set;

import org.reflections.Reflections;

/*
 * Search for all classes implementing a given interface in a given package
 * The class is greatly simplified and created exclusively for working in this program.
 */
public class JavaConfig implements ConfigFactory {
	
	private Reflections scanner;
	

	public JavaConfig(String packageToScan) {
		super();
		this.scanner = new Reflections(packageToScan);
	}

	/*
	 * If I have several implementations of a given interface, then there will be an error
	 */
	public <T> Class<? extends T> getImplClass(Class<T> ifc) {
		Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
		if (classes.size() != 1) {
		throw new RuntimeException(ifc + " has Zero or more than One Impls");
		}
		return classes.iterator().next();
	}
}
