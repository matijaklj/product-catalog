package com.demo.test.productcatalogtests.simulations

import com.demo.test.productcatalogtests.scenarios.{ReadCommandScenario, ReadScenario, WriteScenario}
import com.demo.test.productcatalogtests.util.{Enviroment, Headers}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class PCReadSimulation extends Simulation {

  val httpConf = http.baseUrls(Enviroment.baseURL)
    .shareConnections
    .headers(Headers.commonHeader)
    .contentTypeHeader("application/json")

  val reqPerSec = Integer.getInteger("reqPerSec", 125).toInt

  setUp(
    ReadCommandScenario.writeCommandsScenario.inject(
      rampUsersPerSec(1) to 20 during(60 seconds),
      constantUsersPerSec(20) during (60*5 seconds)
    ),
    ReadCommandScenario.pricingCommandsScenario.inject(
      rampUsersPerSec(1) to 20 during(60 seconds),
      constantUsersPerSec(20) during (60*5 seconds)
    ),
    ReadCommandScenario.stockCommandsScenario.inject(
      rampUsersPerSec(1) to 20 during(60 seconds),
      constantUsersPerSec(20) during (60*5 seconds)
    ),
    ReadScenario.readScenario.inject(
      rampUsersPerSec(1) to reqPerSec.toDouble during(60 seconds),
      constantUsersPerSec(reqPerSec.toDouble) during (60*5 seconds)
    )
  )
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(Enviroment.maxResponseTime.toInt)
    )
}
