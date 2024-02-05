package MODELS;

public class Island {
	private String sea;
	private String name;
	private Double area;
	private Double longitude;
	private Double latitude;

	public Island() {

	}

	public Island(String sea, String name, Double area, Double longitude, Double latitude) {
		this.sea = sea;
		this.name = name;
		this.area = area;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getSea() {
		return sea;
	}

	public void setSea(String sea) {
		this.sea = sea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Island [sea=" + sea + ", name=" + name + ", area=" + area + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}
	

}
