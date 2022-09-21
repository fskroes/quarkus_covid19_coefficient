package org.fskroes.boundary;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.control.CoefficientCalculator;
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
    CoefficientCalculator coefficientCalculator;

    @GET
    @Path("/coefficient")
    public Uni<CaseCoefficient> getCoefficientForCountry(@QueryParam String country) {

        var givenCountryLiveCases = covidClient.getLiveCasesPerCountry(country);
        var vaccineCasesForCountry = covidClient.getCasesVaccineForCountry(country);

        var calculatedCoefficient = coefficientCalculator
                .getCoefficientCalculator(givenCountryLiveCases, vaccineCasesForCountry);
        return Uni
                .createFrom()
                .item(calculatedCoefficient);
    }
}
