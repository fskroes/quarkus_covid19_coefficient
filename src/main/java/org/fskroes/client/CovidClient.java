package org.fskroes.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.fskroes.entity.Country;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RegisterRestClient
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public interface CovidClient {

    @GET
    @Path("/cases")
    Map<String, Country> getLiveCasesForContinent(@QueryParam String continent);

    @GET
    @Path("/vaccines")
    Map<String, Country> getCasesVaccineForContinent(@QueryParam String continent);
}
