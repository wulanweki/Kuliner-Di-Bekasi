package com.kuliner_di_bekasi;

public class Berita{
	private int	id_berita;
	private String	judul;
	private String	alamat;
	private double	lat;
	private double	lng;
	
		public Berita(int id_berita, String judul, String alamat, double lat, double lng){
		super();
		this.id_berita = id_berita;
		this.judul = judul;
		this.alamat = alamat;
		this.lat = lat;
		this.lng = lng;
	}

	public int getId(){
		return id_berita;
	}

	public void setId(int id_berita){
		this.id_berita = id_berita;
	}

	public String getJudul(){
		return judul;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public double getLat(){
		return lat;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLng(){
		return lng;
	}

	public void setLng(double lng){
		this.lng = lng;
	}
}
