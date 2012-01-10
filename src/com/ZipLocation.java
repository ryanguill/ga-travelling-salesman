package com;

public class ZipLocation {

	private String zipcode;
	private String city;
	private String state;
	private double lattitude;
	private double longitude;
	private String id;
	private boolean selected = false;
	
	public ZipLocation ( 	String zipcode
			, String city
			, String state
			, double lattitude
			, double longitude
			, String id
			) {

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


	public static double getLatLongDistance ( double lat1, double lng1, double lat2, double lng2 )
    {
        double pi80 = Math.PI/180;
        lat1 *= pi80;
        lng1 *= pi80;
        lat2 *= pi80;
        lng2 *= pi80;
        
        double earthRadius = 6372.797; // mean radius of Earth in km
        double dlat = lat2-lat1;
        double dlng = lng2-lng1;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlng / 2) * Math.sin(dlng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));;
        return earthRadius*c;
    }
}
