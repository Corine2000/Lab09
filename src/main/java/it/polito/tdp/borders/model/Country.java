package it.polito.tdp.borders.model;

public class Country implements Comparable<Country>{
	
	private int codice;
	private String abbreviazione;
	private String NomeStato;
	
	public Country(int codice, String abbreviazione, String nomeStato) {
		super();
		this.codice = codice;
		this.abbreviazione = abbreviazione;
		NomeStato = nomeStato;
	}
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getAbbreviazione() {
		return abbreviazione;
	}
	public void setAbbreviazione(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}
	public String getNomeStato() {
		return NomeStato;
	}
	public void setNomeStato(String nomeStato) {
		NomeStato = nomeStato;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codice;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (codice != other.codice)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return this.codice+" "+this.abbreviazione+" "+this.NomeStato;
	}
	@Override
	public int compareTo(Country altro) {
		// TODO Auto-generated method stub
		return this.NomeStato.compareTo(altro.getNomeStato());
	}
	
	

}
