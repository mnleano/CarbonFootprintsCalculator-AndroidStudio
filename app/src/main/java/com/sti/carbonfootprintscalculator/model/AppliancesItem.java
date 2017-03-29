package com.sti.carbonfootprintscalculator.model;

public class AppliancesItem {
	String appliancesName;
	int wattage;
	int hours;
	int kwh;

	public AppliancesItem(String appliancesName, int wattage, int hours, int kwh) {
		super();
		this.appliancesName = appliancesName;
		this.wattage = wattage;
		this.hours = hours;
		this.kwh = kwh;
	}

	public String getAppliancesName() {
		return appliancesName;
	}

	public void setAppliancesName(String appliancesName) {
		this.appliancesName = appliancesName;
	}

	public int getWattage() {
		return wattage;
	}

	public void setWattage(int wattage) {
		this.wattage = wattage;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getKwh() {
		return kwh;
	}

	public void setKwh(int kwh) {
		this.kwh = kwh;
	}

}
