package org.fskroes.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.fskroes.entity.Cases;
import org.fskroes.entity.CasesVaccined;
import org.fskroes.entity.Country;
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
    @Path("/vaccines")
    CasesVaccined getCasesVaccineForCountry(@QueryParam String country);
}
