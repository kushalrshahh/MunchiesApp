package munchies.example.com.munchies;

public class FoursquareVenue {
	private String name;
	private String idd;
	private String menu;

	public FoursquareVenue() {
		this.name = "";
		this.idd="";
		this.menu="";
	}



	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}



	public void setidd(String idd) {
		this.idd = idd;
	}

	public String getidd() {
		return idd;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMenu() {
		return menu;
	}




}
