package com.demo.test.productcatalogtests.simulations

import com.demo.test.productcatalogtests.scenarios.WriteScenario
import com.demo.test.productcatalogtests.util.{Enviroment, Headers}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class PCWriteSimulation extends Simulation {

  val httpConf = http.baseUrls(Enviroment.baseURL)
    .shareConnections
    .headers(Headers.commonHeader)
    .contentTypeHeader("application/json")


  setUp(WriteScenario.writeScenario.inject(
    rampUsersPerSec(1) to 50 during(30 seconds),
    constantUsersPerSec(50) during (60 seconds),
    rampUsersPerSec(50) to 100 during(30 seconds),
    constantUsersPerSec(100) during (60 seconds),
    rampUsersPerSec(100) to 150 during(30 seconds),
    constantUsersPerSec(150) during (60 seconds),
    rampUsersPerSec(150) to 200 during(30 seconds),
    constantUsersPerSec(200) during (60 seconds),
    rampUsersPerSec(200) to 250 during(30 seconds),
    constantUsersPerSec(250) during (60 seconds)

    /*rampUsers(400) to 500 during (60 seconds),
    constantUsersPerSec(500) during (60 seconds),
    rampUsers(500) to 600 during (60 seconds),
    constantUsersPerSec(600) during (60 seconds),
    //constantUsersPerSec(20) during (15 seconds) randomized,
    */

  ))
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
