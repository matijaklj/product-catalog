package com.demo.test.productcatalogtests.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object ReadScenario {


  val categoryIdFeeder = csv("data/categoryId.csv").eager.circular
  val productIdFeeder = csv("data/productId.csv").eager.circular


  val productString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_Gatling", "description":"test desc"}"""

  val editProductString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_edited_Gatling", "description":"test EDITED desc"}"""


  val getProductsHttp = http("get - product list")
    .get("/products/")
    .check(status is 200)
    .check(jmesPath("count").ofType[Int].gt(0))


  val getProductByIdHttp = http("get - product by id")
    .get("/products/${productId}")
    .check(status is 200)
    .check(jmesPath("id").ofType[String].exists)

  val readScenario = scenario("Read scenario")
    .feed(categoryIdFeeder)
    .feed(productIdFeeder)
    .group("read-group") {
      exec(exec(getProductsHttp)
      .pause(1)
      .exec(getProductByIdHttp))
    }
}
