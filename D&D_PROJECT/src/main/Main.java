package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import creazionePG.*;

public class Main {

	public static void main(String[] args) {
		Map <Caratteristica,Integer> caratteristiche = new HashMap <>();
		caratteristiche.put(Caratteristica.FORZA, 16);
		caratteristiche.put(Caratteristica.DESTREZZA, 18);
		caratteristiche.put(Caratteristica.COSTITUZIONE, 10);
		caratteristiche.put(Caratteristica.INTELLIGENZA, 10);
		caratteristiche.put(Caratteristica.SAGGEZZA, 10);
		caratteristiche.put(Caratteristica.CARISMA, 10);
		Personaggio x = new Personaggio(Razza.UMANO,"","Netrill",1867,caratteristiche);
	

		x.addClasse(new Guerriero(5));
		x.addClasse(new Barbaro(7));
		
		System.out.println(x);

	}

}
