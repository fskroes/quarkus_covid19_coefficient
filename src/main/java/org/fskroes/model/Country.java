package org.fskroes.model;

import io.quarkus.qson.QsonProperty;

public class Country {

    @QsonProperty("All")
    public AllCountryInformation all;
    public void setAll(AllCountryInformation all) {
        this.all = all;
    }

    public AllCountryInformation getAll() {
        return all;
    }

    public int confirmed;
    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int recovered;
    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getRecovered() {
        return recovered;
    }

    public int deaths;
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public String updated;
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdated() {
        return updated;
    }
}
