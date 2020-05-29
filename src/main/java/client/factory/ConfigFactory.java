package client.factory;

public interface ConfigFactory {

	<T> Class<? extends T> getImplClass(Class<T> ifc);

}
