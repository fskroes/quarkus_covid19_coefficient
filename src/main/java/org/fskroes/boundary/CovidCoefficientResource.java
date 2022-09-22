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

import static org.fskroes.helper.Continents.getContinents;

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

        var liveCasesForContinent = covidClient.getLiveCasesForContinent(continent);
        var vaccineCasesForContinent = covidClient.getCasesVaccineForContinent(continent);

        return Uni
                .createFrom()
                .item(coefficientControl
                        .getCoefficientForContinent(continent, liveCasesForContinent, vaccineCasesForContinent)
                );
    }

    @GET
    @Path("coefficient/all")
    public Uni<CaseCoefficient> getCoefficientForAllCountries() {
        var allLiveCases = covidClient.getAllLiveCases();
        var allVaccineCases = getContinents()
                .stream()
                .map(c -> covidClient.getCasesVaccineForContinent(c))
                .toList();

        return Uni
                .createFrom()
                .item(coefficientControl.getCoefficientForAllCountries(allLiveCases, allVaccineCases));
    }
}
