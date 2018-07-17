package model;

public class Post {
	
	private String testo;
	private String titolo;
	private String autore;
	private String data;
	private Long id;
	
	public Post(String testo, String titolo, String autore, String data,Long id) {
		super();
		this.testo = testo;
		this.titolo = titolo;
		this.autore = autore;
		this.data = data;
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
