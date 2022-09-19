package org.fskroes.resources;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;


public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

    private static final String ALL_COVID_CASUS = "data/all_cases.json";
    private static final int WIREMOCK_PORT = 7777;

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        stubAllCovidCasus();
//        stubPopulation();
        return Collections.singletonMap(
                "quarkus.rest-client.\"org.fskroes.CovidClient\".url",
                wireMockServer.baseUrl()
        );
    }

    private void stubAllCovidCasus() {
        try (InputStream is = WireMockExtensions.class.getResourceAsStream(ALL_COVID_CASUS)) {
            String allCovidCasus = new String(is.readAllBytes());

            wireMockServer.stubFor(
                    get(urlEqualTo("/casus"))
                            .willReturn(okJson(allCovidCasus)));

        } catch (IOException exception) {
            //
        }
    }

    @Override
    public void stop() {
        if (Objects.nonNull(wireMockServer))
            wireMockServer.stop();
    }

//    private void stubPopulation() {
//        try (InputStream is = WireMockExtensions.class.getResourceAsStream(POPULATION_JSON_FILE)) {
//            String extensions = new String(is.readAllBytes());
//
//            // Stub for full list of extensions:
//            wireMockServer.stubFor(get(urlEqualTo(BASE_PATH))
//                    .willReturn(okJson(extensions)));
//
//            // Stub for each country
//            try (StringReader sr = new StringReader(extensions);
//                 JsonParser parser = Json.createParser(sr)) {
//                parser.next();
//                for (JsonValue extension : parser.getArray()) {
//                    String id = extension.asJsonObject().getString("id");
//
//                    wireMockServer.stubFor(get(urlEqualTo(BASE_PATH + "/extensions?id=" + URLEncoder.encode(id, StandardCharsets.UTF_8)))
//                            .willReturn(okJson("[" + extension + "]")));
//                }
//            }
//        } catch (IOException exception) {
//            //
//        }
//    }
}
