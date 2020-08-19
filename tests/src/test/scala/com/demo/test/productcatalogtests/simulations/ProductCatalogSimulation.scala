package com.demo.test.productcatalogtests.simulations

import com.demo.test.productcatalogtests.scenarios.PostProduct
import com.demo.test.productcatalogtests.util.{Enviroment, Headers}
import io.gatling.core.Predef.{rampUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration._

class ProductCatalogSimulation extends Simulation {

  val httpConf = http.baseUrls(Enviroment.baseURL)
    .headers(Headers.commonHeader)
    .contentTypeHeader("application/json")

  setUp(PostProduct.postProduct.inject(
      atOnceUsers(1),
      rampUsersPerSec(1) to 500 during(60*2 seconds)
  ))
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
