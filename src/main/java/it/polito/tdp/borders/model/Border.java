package it.polito.tdp.borders.model;

public class Border {
	
	private int dyad;
	private int state1no;
	private String state1ab;
	private int state2no;
	private String state2ab;
	private int year;
	private int conttype;
	private double version;
	public Border(int dyad, int state1no, String state1ab, int state2no, String state2ab, int year, int conttype,
			double version) {
		super();
		this.dyad = dyad;
		this.state1no = state1no;
		this.state1ab = state1ab;
		this.state2no = state2no;
		this.state2ab = state2ab;
		this.year = year;
		this.conttype = conttype;
		this.version = version;
	}
	public int getDyad() {
		return dyad;
	}
	public void setDyad(int dyad) {
		this.dyad = dyad;
	}
	public int getState1no() {
		return state1no;
	}
	public void setState1no(int state1no) {
		this.state1no = state1no;
	}
	public String getState1ab() {
		return state1ab;
	}
	public void setState1ab(String state1ab) {
		this.state1ab = state1ab;
	}
	public int getState2no() {
		return state2no;
	}
	public void setState2no(int state2no) {
		this.state2no = state2no;
	}
	public String getState2ab() {
		return state2ab;
	}
	public void setState2ab(String state2ab) {
		this.state2ab = state2ab;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getConttype() {
		return conttype;
	}
	public void setConttype(int conttype) {
		this.conttype = conttype;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	
	

}
