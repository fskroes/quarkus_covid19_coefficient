package org.fskroes.model;

import io.quarkus.qson.QsonProperty;

import java.util.List;

public class Cases {

    @QsonProperty("Albania")
    private Country albania;
    public void setAlbania(Country albania) {
        this.albania = albania;
    }
    public Country getAlbania() {
        return albania;
    }

    @QsonProperty("France")
    public Country france;
    public void setFrance(Country france) {
        this.france = france;
    }
    public Country getFrance() {
        return france;
    }

    @QsonProperty("Netherlands")
    public Country netherlands;
    public void setNetherlands(Country netherlands) {
        this.netherlands = netherlands;
    }
    public Country getNetherlands() {
        return netherlands;
    }

    public List<Country> getCountries() {
        return List.of(
                albania,
                france,
                netherlands
        );
    }
}
