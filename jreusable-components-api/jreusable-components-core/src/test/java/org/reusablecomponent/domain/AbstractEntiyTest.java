package org.reusablecomponent.domain;

class AbstractEntiyTest {

    static class Person extends AbstractEntity<Long> {
	
	private String name;

	public String getName() {
	    return name;
	}

	@Override
	public AbstractEntity<Long> createNewInstance() {
	    // TODO Auto-generated method stub
	    return null;
	}

    }
    
    static class Department extends AbstractEntity<String> {
	
	private String name;
	
	private String value;

	public String getName() {
	    return name;
	}

	public String getValue() {
	    return value;
	}
	
	public static class Builder extends AbstractEntityBuilder<String, Department> {

	    @Override
	    public Department build() {
		return null;
	    }
	    
	}
    }
    
    
    void test() {
	
	final var person = new Person();
    }
}
