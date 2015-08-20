package register;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileRegisterLoader implements RegisterLoader {

	@Override // Nacitanie udajov ulozenych v subore
	public Register load() {
		
		FileInputStream in = null;
		try {
			in = new FileInputStream("register.bin");
			// @SuppressWarnings("resource")
			ObjectInputStream s = new ObjectInputStream(in);
			// @SuppressWarnings("unused")
			Register register = (Register) s.readObject();
			s.close();
			return register;

		} catch (Exception e) {
			e.printStackTrace();
			return null; // register
		}
	}

	@Override // Ulozenie udajov do suboru
	public void save(Register register) {
		
		try {
			FileOutputStream out = new FileOutputStream("register.bin");
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(register);
			s.close();
			System.out.println("Ulozenie sa podarilo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
