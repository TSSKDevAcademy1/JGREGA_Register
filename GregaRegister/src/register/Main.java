package register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

/**
 * Created by jaro on 3.2.2014.
 */

// Hlavna funkcia MAIN
public class Main {
	public static String choice;

	// @SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		Register register = null;
		RegisterLoader registerLoader = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Work with (1)Array (2)List:");
		choice = input.readLine();
		try {
			switch (choice) { // Vyber z moznosti pracovat s Array/List
			case "1":
				register = new ArrayRegister(20);
				break;
			case "2":
				register = new ListRegister();
				break;
			}
		} catch (Exception e) {
			e.getMessage();
			System.out.println("Wrong input!! {defaul set:Array}");
			register = new ArrayRegister(20);
		}

		System.out.println("SAVE/LOAD data TO/FROM {(1)File(2)Database(3)TextFile} :");
		choice = input.readLine();
		try {
			switch (choice) { // Vyber z moznosti Ulozenia/Nacitania udajov z
								// FILE/DATABASE/TEXTFILE
			case "1":
				registerLoader = new FileRegisterLoader();
				break;
			case "2":
				registerLoader = new DatabaseRegisterLoader();
				break;
			case "3":
				registerLoader = new TextFileRegisterLoader();
				break;
			}
		} catch (Exception e) {
			e.getMessage();
			System.out.println("Wrong input!! {defaul set:Database}");
			registerLoader = new DatabaseRegisterLoader();
		}

		ConsoleUI ui = new ConsoleUI(register, registerLoader);

		ui.run();
	}
}
