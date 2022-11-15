package threaded_cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeituraArquivo {
	
	public static ArrayList<Integer> ler (String filename) {
		
		File file = new File(filename);
		ArrayList<Integer> enderecos = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(file)) {			
			scanner.useDelimiter("\n");
			while (scanner.hasNextInt()) {
				enderecos.add(scanner.nextInt());
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		return enderecos;
		
	}
		
}
