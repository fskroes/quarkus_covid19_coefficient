package org.fskroes.boundary;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.control.CoefficientControl;
import org.fskroes.model.CaseCoefficient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CovidCoefficientResource {

    @Inject
    @RestClient
    CovidClient covidClient;

    @Inject
    CoefficientControl coefficientControl;

    @GET
    @Path("/coefficient")
    public Uni<CaseCoefficient> getCoefficientForContinent(@QueryParam String continent) {

        var givenCountryLiveCases = covidClient.getLiveCasesForContinent(continent);
        var vaccineCasesForCountry = covidClient.getCasesVaccineForContinent(continent);

        return Uni
                .createFrom()
                .item(coefficientControl
                        .getCoefficientForContinent(continent, givenCountryLiveCases, vaccineCasesForCountry)
                );
    }
}
