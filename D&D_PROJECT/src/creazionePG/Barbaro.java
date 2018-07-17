package creazionePG;

public class Barbaro extends ClasseBase {
	
private static final String classe ="barbaro";
	
	public Barbaro (int lvl) {
		 this.caricaClasse(lvl, classe);
		 this.setLvlClasse(lvl);
		 this.calcolaPFClasse(12);
		 this.setNomeClasse(classe);
		 System.out.println("Creato barbaro di lvl "+lvl);
	}

}
