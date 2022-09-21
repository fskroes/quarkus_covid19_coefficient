package org.fskroes.entity;

import io.quarkus.qson.QsonProperty;

public class AllCountryInformation {

    public int administered;
    @QsonProperty("people_vaccinated")
    public int vaccinated;
    public int confirmed;
    public int recovered;
    public int deaths;
    public String country;
    public int population;
    public String continent;

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
