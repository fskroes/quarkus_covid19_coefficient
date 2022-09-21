package org.fskroes.boundary;

import io.smallrye.common.constraint.NotNull;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.entity.Cases;
import org.fskroes.entity.Country;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CovidCasesResource {

    @Inject
    @RestClient
    CovidClient covidClient;

    @GET
    public Cases getAllLiveCases() {
        return covidClient.getAllLiveCases();
    }

    @GET
    public Country getLiveCasesPerCountry(@NotNull @QueryParam String country) {
        return covidClient.getLiveCasesPerCountry(country);
    }

    @GET
    public Cases getLiveCasesForContinent(@NotNull @QueryParam String continent) {
        return covidClient.getLiveCasesForContinent(continent);
    }
}
