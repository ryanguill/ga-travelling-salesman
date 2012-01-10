package com;

public class ZipLocation {

	private String zipcode;
	private String city;
	private String state;
	private double lattitude;
	private double longitude;
	private String id;
	private boolean selected = false;

	public ZipLocation(String zipcode, String city, String state, double lattitude, double longitude, String id) {

		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.id = id;
		this.selected = false;

	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public double getLattitude() {
		return lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getId() {
		return id;
	}

	public boolean isSelected() {
		return selected;
	}

	public static double getLatLongDistance(double lat1, double lng1, double lat2, double lng2) {
		double pi80 = Math.PI / 180;
		lat1 *= pi80;
		lng1 *= pi80;
		lat2 *= pi80;
		lng2 *= pi80;

		double earthRadius = 6372.797; // mean radius of Earth in km
		double dlat = lat2 - lat1;
		double dlng = lng2 - lng1;
		double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlng / 2) * Math.sin(dlng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		;
		return earthRadius * c;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;

		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		long temp;

		temp = Double.doubleToLongBits(lattitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ZipLocation)) {
			return false;
		}
		ZipLocation other = (ZipLocation) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (Double.doubleToLongBits(lattitude) != Double.doubleToLongBits(other.lattitude)) {
			return false;
		}
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		if (selected != other.selected) {
			return false;
		}
		if (state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!state.equals(other.state)) {
			return false;
		}
		if (zipcode == null) {
			if (other.zipcode != null) {
				return false;
			}
		} else if (!zipcode.equals(other.zipcode)) {
			return false;
		}
		return true;
	}

}
