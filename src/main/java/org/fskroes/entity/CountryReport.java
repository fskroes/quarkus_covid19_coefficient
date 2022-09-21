package org.fskroes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.qson.QsonProperty;

public class CountryReport {

    public int administered;
    @QsonProperty("people_vaccinated")
    public int vaccinated;
    public int confirmed;
    public int recovered;
    public int deaths;
    public String country;
    public int population;
    public String continent;

    @JsonProperty("sq_km_area")
    public int area;

    @JsonProperty("life_expectancy")
    public double lifeExpectancy;

    @JsonProperty("elevation_in_meters")
    public String elevation;

    public String abbreviation;
    public int iso;
    public String location;
    @JsonProperty("capital_city")
    public String capitalCity;
    @JsonProperty("lat")
    public double latitude;
    @JsonProperty("long")
    public double longitude;
    public String updated;

    public void setIso(int iso) {
        this.iso = iso;
    }

    public int getIso() {
        return iso;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUpdated() {
        return updated;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getElevation() {
        return elevation;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public int getAdministered() {
        return administered;
    }

    public void setAdministered(int administered) {
        this.administered = administered;
    }

    public int getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(int vaccinated) {
        this.vaccinated = vaccinated;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
