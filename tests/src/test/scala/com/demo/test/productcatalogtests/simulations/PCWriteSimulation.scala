package com.demo.test.productcatalogtests.simulations

import com.demo.test.productcatalogtests.scenarios.WriteScenario
import com.demo.test.productcatalogtests.util.{Enviroment, Headers}
import io.gatling.core.Predef.{rampUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration._

class PCWriteSimulation extends Simulation {

  val httpConf = http.baseUrls(Enviroment.baseURL)
    .shareConnections
    .headers(Headers.commonHeader)
    .contentTypeHeader("application/json")


  setUp(WriteScenario.writeScenario.inject(
    atOnceUsers(1),
      nothingFor(10 seconds),
      rampUsersPerSec(1) to 500 during(60 seconds),
      constantUsersPerSec(500) during (60*4 seconds)


  ))
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
