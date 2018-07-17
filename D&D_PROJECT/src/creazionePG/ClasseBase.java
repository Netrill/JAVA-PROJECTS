package creazionePG;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public abstract class ClasseBase  {
	
 
	private int lvlClasse;
	private int attaccoBase;
	private int pf;
	private int ca;
	private int tiroSalvezzaVolonta;
	private int tiroSalvezzaTempra;
	private int tiroSalvezzaRiflessi;
	private int numeroTalenti;
	private String nomeClasse;
 
	 
	
	public String getNomeClasse() {
		return nomeClasse;
	}
	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
	public int getLvlClasse() {
		return lvlClasse;
	}
	public void setLvlClasse(int lvlClasse) {
		this.lvlClasse = lvlClasse;
	}
	
	public int getAttaccoBase() {
		return attaccoBase;
	}
	public void setAttaccoBase(int attaccoBase) {
		this.attaccoBase = attaccoBase;
	}
	 
	public int getPf() {
		return pf;
	}
	public void setPf(int pf) {
		this.pf = pf;
	}
	public int getCa() {
		return ca;
	}
	public void setCa(int ca) {
		this.ca = ca;
	}
	public int getTiroSalvezzaVolonta() {
		return tiroSalvezzaVolonta;
	}
	public void setTiroSalvezzaVolonta(int tiroSalvezzaVolonta) {
		this.tiroSalvezzaVolonta = tiroSalvezzaVolonta;
	}
	public int getTiroSalvezzaTempra() {
		return tiroSalvezzaTempra;
	}
	public void setTiroSalvezzaTempra(int tiroSalvezzaTempra) {
		this.tiroSalvezzaTempra = tiroSalvezzaTempra;
	}
	public int getTiroSalvezzaRiflessi() {
		return tiroSalvezzaRiflessi;
	}
	public void setTiroSalvezzaRiflessi(int tiroSalvezzaRiflessi) {
		this.tiroSalvezzaRiflessi = tiroSalvezzaRiflessi;
	}
	public int getNumeroTalenti() {
		return numeroTalenti;
	}
	public void setNumeroTalenti(int numeroTalenti) {
		this.numeroTalenti = numeroTalenti;
	} 
	void caricaClasse (int lvl,String classe) {
		if (lvl >0 && lvl <=20) {
			try {
				String line32;
				try (Stream<String> lines = Files.lines(Paths.get(".//.//filedata//"+classe+".txt"))) {
				    line32 = lines.skip(lvl-1).findFirst().get();
				}
				String [] array =line32.split("\\s+");
				this.setLvlClasse(lvl);
				for (int i=0;i<array.length;i++)  {
					if (i==0)
						this.setAttaccoBase(Integer.parseInt(array[i]));
					if (i==1)
						this.setTiroSalvezzaTempra(Integer.parseInt(array[i]));
					if (i==2)
						this.setTiroSalvezzaRiflessi(Integer.parseInt(array[i]));
					if (i==3)
						this.setTiroSalvezzaVolonta(Integer.parseInt(array[i]));
					if (i==4)
						this.setNumeroTalenti(Integer.parseInt(array[i]));		
				}
	  
			}catch(Exception e) {
				System.out.println(e.getMessage());
			} 
		}
	}
	void calcolaPFClasse (int dadoVita) {
		Random x = new Random();
		int somma=0;
		for (int i =0;i<dadoVita;i++ ) 
			somma+=x.nextInt(dadoVita);
		
		int pf =(int) (dadoVita + (somma));
		this.pf =  pf;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeClasse == null) ? 0 : nomeClasse.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) throws IllegalArgumentException {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClasseBase other = (ClasseBase) obj;
		if (nomeClasse == null) {
			if (other.nomeClasse != null)
				return false;
		} else if (!nomeClasse.equals(other.nomeClasse))
			return false;
		return true;
	}
	
	

}
