package client.factory;

import client.constants.Constants;

/*
 * The class is greatly simplified and created exclusively for working in this program.
 * Assumes that the created object has only one constructor with parameters
 */

public final class ObjectFactory {
	
	private static volatile ObjectFactory instance;
	private ConfigFactory config;
	
	public static ObjectFactory getInstance() {
		if (instance == null) {
			synchronized (ObjectFactory.class) {
				if (instance == null) {
					instance = new ObjectFactory();
				}
			}
		}
		return instance;
	}
	
	private ObjectFactory() {
		config = new JavaConfig(Constants.PACKAGE_TO_SCAN);
	}
	/*
	 * Create class instance
	 * I can create an instance using a given interface or a given class
	 */
	@SuppressWarnings("unchecked")
	public <T> T createObject(Class<T> type, Object args) {
		Class<? extends T> implClass = type;
		if (type.isInterface()) {
			implClass = config.getImplClass(type);
		}
		T t;
		try {
			t = (T) implClass.getDeclaredConstructors()[0].newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}

}
