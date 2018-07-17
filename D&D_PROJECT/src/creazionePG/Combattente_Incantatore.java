package creazionePG;


import java.util.List;
import java.util.Map;
import java.util.Set;


public class Combattente_Incantatore extends ClasseBase{
	
	protected Map <Integer,Set<Incantesimo>> incantesimiConosciuti;
	protected List <Integer> incantesimiRimastiAttuali;
	protected List <Integer> incantesimiDefault;
	protected ClasseIncantatore classeIncantatore;
	protected int lvlIncantatore;
	
	public Combattente_Incantatore(Map<Integer, Set<Incantesimo>> incantesimiConosciuti,
			List<Integer> incantesimiRimastiAttuali, List<Integer> incantesimiDefault,
			ClasseIncantatore classeIncantatore, int lvlIncantatore) {
		super();
		this.incantesimiConosciuti = incantesimiConosciuti;
		this.incantesimiRimastiAttuali = incantesimiRimastiAttuali;
		this.incantesimiDefault = incantesimiDefault;
		this.classeIncantatore = classeIncantatore;
		this.lvlIncantatore = lvlIncantatore;
	}
	public boolean acquisisciNuovoIncantesimo (Integer lvl, Incantesimo incantesimo) {		
		if (lvlIncantatore >=lvl && incantesimiConosciuti.get(lvl).size()<incantesimiDefault.get(lvl) ) {
			incantesimiConosciuti.get(lvl).add(incantesimo);
			return true;
		}
		return false;
	}
	public boolean lanciaIncantesimoSePresente (Integer lvl ) {
		int numeroincantesimo=incantesimiRimastiAttuali.get(lvl);
		if (numeroincantesimo>=1) {
			incantesimiRimastiAttuali.set(lvl, --numeroincantesimo);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void RicaricaTuttiIncantesimi () {	
		for (int i=0;i<lvlIncantatore+1;i++) 
			incantesimiRimastiAttuali.set(i, incantesimiDefault.get(i));

	}
	
	
	
	
	
}
