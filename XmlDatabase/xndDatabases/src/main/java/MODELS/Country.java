package MODELS;

import java.util.Date;

public class Country {

	private String name;
	private Long population;
	private Date indepDate;
	private String government;

	public Country() {

	}

	public Country(String name, Long population, Date indepDate, String government) {
		this.name = name;
		this.population = population;
		this.indepDate = indepDate;
		this.government = government;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public Date getIndepDate() {
		return indepDate;
	}

	public void setIndepDate(Date indepDate) {
		this.indepDate = indepDate;
	}

	public String getGovernment() {
		return government;
	}

	public void setGovernment(String government) {
		this.government = government;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", population=" + population + ", indepDate=" + indepDate + ", government="
				+ government + "]";
	}

	
}
