package creazionePG;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;





public class Personaggio {
	protected Set<ClasseBase> classi;
	protected int lvlPersonaggio;
	protected Razza razza;
	protected long exp;
	protected long expProssimoLivello;
	protected int attaccoInMischia;
	protected int attaccoADistanza;
	protected int TiroSalvezzaVolonta;
	protected int TiroSalvezzaTempra;
	protected int TiroSalvezzaRiflessi;
	protected Map <Caratteristica,Integer> caratteristiche;
	protected String nome;
	protected String cognome;
	protected int eta;
	protected int pfTotali;
	
	public Personaggio () {
		classi = new HashSet<>();
		setCaratteristicheIniziali ();
	}

	public Personaggio(Razza razza, String nome, String cognome, int eta,Map <Caratteristica,Integer> caratteristiche) {
		super();
		this.razza = razza;
		this.nome = nome;
		this.cognome = cognome;
		this.eta = eta;
		
		classi = new HashSet<>();
		if (caratteristiche==null)
			setCaratteristicheIniziali ();
		else
			this.caratteristiche=caratteristiche;
	}



	public void setCaratteristicheIniziali () {

		caratteristiche= new HashMap <> ();
		caratteristiche.put(Caratteristica.FORZA, 10);
		caratteristiche.put(Caratteristica.DESTREZZA, 10);
		caratteristiche.put(Caratteristica.COSTITUZIONE, 10);
		caratteristiche.put(Caratteristica.INTELLIGENZA, 10);
		caratteristiche.put(Caratteristica.SAGGEZZA, 10);
		caratteristiche.put(Caratteristica.CARISMA, 10);
		
	}
	
	public void addClasse (ClasseBase c) {
		if (classi.contains(c))
			throw new IllegalArgumentException();
		classi.add(c);
		Iterator<ClasseBase> i =classi.iterator();
		this.pfTotali=0;
		this.attaccoADistanza=0;
		this.attaccoInMischia=0;
		this.lvlPersonaggio=0;
		while (i.hasNext()) {
			
			ClasseBase appoClasse =(ClasseBase) i.next();
			this.attaccoADistanza += appoClasse.getAttaccoBase();
			this.attaccoInMischia += appoClasse.getAttaccoBase();
			System.out.println("PUNTI FERITA CLASSE "+appoClasse.getPf());
			this.pfTotali+=appoClasse.getPf();
			this.lvlPersonaggio +=appoClasse.getLvlClasse();

		}
		this.pfTotali+= ((caratteristiche.get(Caratteristica.COSTITUZIONE)-10)/2);
		this.attaccoADistanza += ((caratteristiche.get(Caratteristica.DESTREZZA)-10)/2);
		this.attaccoInMischia += ((caratteristiche.get(Caratteristica.FORZA)-10)/2);
		System.out.println("TERMINE");
	}
	
	public int getLvl() {
		int sommaLivelli=0;
		for (ClasseBase c : classi) {
			sommaLivelli+=c.getLvlClasse();
		}
		return sommaLivelli;
	}
	public Razza getRazza() {
		return razza;
	}
	public void setRazza(Razza razza) {
		this.razza = razza;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public long getExp() {
		return exp;
	}
	void setExp(long exp) {
		this.exp = exp;
	}
	public Map<Caratteristica, Integer> getCaratteristiche() {
		return caratteristiche;
	}
	public void setCaratteristiche(Map<Caratteristica, Integer> caratteristiche) {
		this.caratteristiche = caratteristiche;
	}
	@Override
	public String toString() {
		StringBuilder s =new StringBuilder();
		s.append(nome+ " ").append(cognome+" ").append(razza.toString()+ " ").append("lvl "+new Integer(lvlPersonaggio).toString()+ " ").append("pf "+pfTotali).append("\n");
		Iterator<ClasseBase> i =(Iterator<ClasseBase>) classi.iterator();
		s.append("FOR ").append(caratteristiche.get(Caratteristica.FORZA)+"\n");
		s.append("DES ").append(caratteristiche.get(Caratteristica.DESTREZZA)+"\n");
		s.append("COS ").append(caratteristiche.get(Caratteristica.COSTITUZIONE)+"\n");
		s.append("INT ").append(caratteristiche.get(Caratteristica.INTELLIGENZA)+"\n");
		s.append("SAG ").append(caratteristiche.get(Caratteristica.SAGGEZZA)+"\n");
		s.append("CAR ").append(caratteristiche.get(Caratteristica.CARISMA)+"\n");
		while (i.hasNext()) {
			ClasseBase appoClasse =(ClasseBase) i.next();
			s.append(appoClasse.getNomeClasse()+ " " + appoClasse.getLvlClasse()+"\n");
		}
		s.append("Attacco in mischia " + this.attaccoInMischia + "\n");
		
		s.append("Attacco a distanza " + this.attaccoADistanza);
		
		return s.toString();
	}
	
}
