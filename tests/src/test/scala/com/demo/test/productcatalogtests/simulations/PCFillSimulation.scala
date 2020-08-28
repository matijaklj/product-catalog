package com.demo.test.productcatalogtests.simulations

import com.demo.test.productcatalogtests.scenarios.WriteScenario
import com.demo.test.productcatalogtests.util.{Enviroment, Headers}
import io.gatling.core.Predef.{nothingFor, rampUsersPerSec, _}
import io.gatling.http.Predef._

import scala.concurrent.duration._

class PCFillSimulation extends Simulation {

  val httpConf = http.baseUrls(Enviroment.baseURL)
    .shareConnections
    .headers(Headers.commonHeader)
    .contentTypeHeader("application/json")


  setUp(
    // insert 100 categories
    WriteScenario.fillWriteSideCategoriesScenario.inject(
      atOnceUsers(100)
    ),
    // insert 500 products
    WriteScenario.fillWriteSideScenario.inject(
      nothingFor(20 seconds),
      atOnceUsers(125),
      nothingFor(5 seconds),
      atOnceUsers(125),
      nothingFor(5 seconds),
      atOnceUsers(125),
      nothingFor(5 seconds),
      atOnceUsers(125)
  ))
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
