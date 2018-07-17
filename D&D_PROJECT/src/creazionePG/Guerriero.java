package creazionePG;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Guerriero extends ClasseBase {
	
	private static final String classe ="guerriero";
	
	public Guerriero (int lvl) {
		 this.caricaClasse(lvl, classe);
		 this.setLvlClasse(lvl);
		 this.calcolaPFClasse(10);
		 this.setNomeClasse(classe);
		 System.out.println("Creato guerriero di lvl "+lvl);
	}
	
}
