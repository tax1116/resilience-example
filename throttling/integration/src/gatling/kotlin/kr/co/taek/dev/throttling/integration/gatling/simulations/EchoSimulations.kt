package kr.co.taek.dev.throttling.integration.gatling.simulations

import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.jsonFile
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import kr.co.taek.dev.throttling.integration.gatling.endpoints.APIEndpoints
import java.time.Duration

class EchoSimulations : Simulation() {
    private val feeder = jsonFile("data/echo_messages.json").circular()

    private val scn =
        scenario("Echo Load Test")
            .feed(feeder)
            .exec(APIEndpoints.echo)

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(50.0).during(Duration.ofSeconds(60)),
            ),
        ).protocols(
            // http.baseUrl("http://localhost:10000"),
            http.baseUrl("http://localhost"),
        )
    }
}
