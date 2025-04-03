package kr.co.taek.dev.throttling.integration.gatling.endpoints

import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import io.gatling.javaapi.http.HttpRequestActionBuilder

object APIEndpoints {
    val echo: HttpRequestActionBuilder =
        http("Echo API - #{tier}")
            .post("/echo")
            .header("X-API-KEY", "#{apiKey}")
            .body(StringBody("#{message}"))
            .asJson()
            .check(status().`is`(200))
}
