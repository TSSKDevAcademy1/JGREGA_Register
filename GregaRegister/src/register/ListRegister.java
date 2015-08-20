package register;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

// Trieda pre pracu s List-om
public class ListRegister implements Register, Iterable<Person>, Serializable {

	private List<Person> persons = new ArrayList<Person>();

	@Override
	public Iterator<Person> iterator() {
		return persons.iterator();
	}

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public int getSize() {
		return persons.size();
	}

	@Override
	public Person getPerson(int index) {
		Collections.sort(persons);
		return persons.get(index);
	}

	@Override
	public void setPerson(Person person, int a) {
		persons.set(a, person);
	}

	@Override
	public void addPerson(Person person) {
		persons.add(person);
	}

	@Override // Sluzi pre vyhladanie osoby v registri podla mena
	public Person findPersonByName(String name) throws Exception {
		return persons.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
	}

	@Override //  Sluzi pre vyhladanie osoby v registri podla telefonneho cisla
	public Person findPersonByPhoneNumber(String phoneNumber) throws Exception {
		for (int i = 0; i < persons.size(); i++) {
			if (phoneNumber.equals(persons.get(i).getPhoneNumber())) {
				return persons.get(i);
			}
		}
		throw new Exception("Person with this phone number NOT FOUND!!");
	}

	@Override // Sluzi pre odstranenie osoby z registra
	public void removePerson(Person person) {
		for (int i = 0; i < persons.size(); i++) {
			if (person.equals(persons.get(i))) {
				persons.remove(i);
			}
		}
	}
}
