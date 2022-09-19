package org.fskroes.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.fskroes.model.Cases;
import org.fskroes.model.CasesVaccined;
import org.fskroes.model.Country;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public interface CovidClient {

    @GET
    @Path("/cases")
    Cases getAllLiveCases();

    @GET
    @Path("/cases")
    Country getLiveCasesPerCountry(@QueryParam String country);

    @GET
    @Path("/cases")
    Cases getLiveCasesForContinent(@QueryParam String continent);

    @GET
    @Path("/history")
    Cases getHistoryCasesOfDeathForCountry(@QueryParam String country, @QueryParam String status);

    @GET
    @Path("/vaccines")
    CasesVaccined getCasesVaccineForCountry(@QueryParam String country);
}
