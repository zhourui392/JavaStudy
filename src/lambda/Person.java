package lambda;

public class Person {
	private String firstName;
	private String name;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Person(String name, String firstname) {
		this.name = name;
		this.firstName = firstname;
	}

}
