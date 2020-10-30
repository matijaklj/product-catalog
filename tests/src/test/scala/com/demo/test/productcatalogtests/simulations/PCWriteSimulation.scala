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
    rampUsersPerSec(1) to 100 during(60 seconds),
    constantUsersPerSec(100) during (60 seconds),
    rampUsersPerSec(100) to 200 during(60 seconds),
    constantUsersPerSec(200) during (60 seconds),
    rampUsersPerSec(200) to 300 during(60 seconds),
    constantUsersPerSec(300) during (60 seconds),
    rampUsersPerSec(300) to 400 during(60 seconds),
    constantUsersPerSec(400) during (60 seconds),
    rampUsersPerSec(400) to 500 during(60 seconds),
    constantUsersPerSec(500) during (60 seconds)
  ))
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
