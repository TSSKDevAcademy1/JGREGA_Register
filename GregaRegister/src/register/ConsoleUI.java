package register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * User interface of the application.
 */
public class ConsoleUI implements Serializable {
	/** register.Register of persons. */

	private Register register;
	private RegisterLoader registerLoader;

	/**
	 * In JDK 6 use Console class instead.
	 * 
	 * @see readLine()
	 */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Menu options.
	 */
	private enum Option {
		PRINT, ADD, UPDATE, REMOVE, FIND, EXIT
	};

	public ConsoleUI(Register register, RegisterLoader registerLoader) { 
		
		this.register = register;
		this.registerLoader = registerLoader;
		
		if (registerLoader.load() == null) { // ak file neexistuje create new
			switch (Main.choice) {
			case "1":
				this.register = new ArrayRegister(20);
			case "2":
				this.register = new ListRegister();
			}
		} else {
			this.register = registerLoader.load();
		}
	}

	public void run() throws Exception {
	
		while (true) {
			try {
				switch (showMenu()) {
				case PRINT:
					printRegister();
					break;
				case ADD:
					addToRegister();
					break;
				case UPDATE:
					updateRegister();
					break;
				case REMOVE:
					removeFromRegister();
					break;
				case FIND:
					findInRegister();
					break;
				case EXIT:
					registerLoader.save(register);
					System.out.print("Good bye :) ");
					return;
				}
			} catch (Exception e) {
				e.getMessage();
				System.out.println("Wrong Input !!");
			}
		}
	}

	private String readLine() {
		
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	// Vykreslenie a zobrazenie Menu
	private Option showMenu() {
		
		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			selection = Integer.parseInt(readLine());
		} while (selection <= 0 || selection > Option.values().length);
		clear();
		return Option.values()[selection - 1];	
	}

	// Vypis osob z registra
	private void printRegister() {
		
		 String format = "|%1$-5s|%2$-17s|%3$-20s|\n";
		System.out.println("---------------------------------------------");
		System.out.format(format,"Index","Name", "Phone Number");
		System.out.println("|--------------------------------------------|");
		for (int i = 0; i < register.getSize(); i++) {
			if (register.getPerson(i) != null)
				System.out.format(format, i + 1 + ".", register.getPerson(i).getName(),
						register.getPerson(i).getPhoneNumber());
		}
		System.out.println("----------------------------------------------");
	}
	
	// Pridanie osoby do registra
	private void addToRegister() {
		
		boolean helper = false;
		System.out.println("Enter Name: ");
		String name = readLine();
		System.out.println("Enter Phone Number: ");
		String phoneNumber = readLine();
		for (int i = 0; i < register.getCount(); i++) { // kontrola ci zadane meno/cislo sa uz nenachadza v registri														
														
			helper = ((register.getPerson(i).getName().equals(name)
					|| register.getPerson(i).getPhoneNumber().equals(phoneNumber)) ? true : false);
		}
		if (!helper) {
			register.addPerson(new Person(name, phoneNumber));
		} else {
			System.out.println("Zadane Meno/Cislo sa uz v registri nachadza!!!");
		}
		clear();
	}

	// Update mena/cisla osoby na zaklade jej index-u
	private void updateRegister() {

		System.out.println("Enter item which you want to change:");
		String item = readLine();
		int itemNum = Integer.parseInt(item);
		System.out.println("Enter new name of person:");
		String nameReplace = readLine();
		System.out.println("Enter new number of person:");
		String numberReplace = readLine();
		register.setPerson(new Person(nameReplace, numberReplace), (itemNum - 1));
	}

	// Vyhladanie osoby v registri na zaklade Mena/cisla
	private void findInRegister() throws Exception {

		System.out.println("(1. Find by name 2. Find by phoneNumber)Enter your choice:");
		int choice = Integer.parseInt(readLine());
		clear();
		switch (choice) {
		case 1:
			System.out.println("Enter name of person:");
			String nameFind = readLine();
			String elementStr = register.findPersonByName(nameFind).toString();
			clear();
			System.out.println(elementStr);
			break;
		case 2:
			System.out.println("Enter phoneNumber of person:");
			String numberFind = readLine();
			Person foundPerson = register.findPersonByPhoneNumber(numberFind);
			clear();
			System.out.println(foundPerson);
			break;
		default:
			System.out.println("This choice is not supported");
		}		
	}

	// Odstranenie osoby z registra
	private void removeFromRegister() {
		
		printRegister();
		System.out.println("Enter index: ");
		int index = Integer.parseInt(readLine());
		Person person = register.getPerson(index - 1);
		register.removePerson(person);
		clear();
	}
	
	// funkcia vytvarajuca pocit vymazovania obrazovky {zariadkovanie}
	public void clear() {		
		
			for (int i = 0; i < 1000; i++) {
				System.out.println();
			}	
	}
}
