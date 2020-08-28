package com.demo.test.productcatalogtests.scenarios

import com.demo.test.productcatalogtests.util.Enviroment
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import sun.jvm.hotspot.runtime.Flags

object ReadCommandScenario {

  //private val categoryIdFeeder = csv("data/categoryId.csv").eager.circular
  private val categoryIdFeederRnd = csv("data/categoryId.csv").eager.random
  //private val productIdFeeder = csv("data/productId.csv").eager.circular
  private val productIdFeederRnd = csv("data/productId.csv").eager.random

  val r = scala.util.Random
  private val rndValue = () => BigDecimal(r.nextInt(200)+r.nextFloat).setScale(2, BigDecimal.RoundingMode.HALF_UP).toFloat
  //private val categoryString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_category_"""+ id +""""}"""

  private val productString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_product_"""+ id +""", "description":"test desc"}"""

  private val editProductString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_edited_"""+ id +"""", "description":"test EDITED desc"}"""

  private val productPriceString = (id:String) =>  """{ "productId": """ + "\"" + id + "\"" + """, "value":""""+ rndValue() +"""" }"""

  private val postProductPriceHttp = http("post - product price")
    .post(Enviroment.pricingUrl + "/pricing/")
    .body(StringBody(productPriceString("${productId}")))
    .check(status is 200)

  private val putProductPriceHttp = http("post - product price")
    .put(Enviroment.pricingUrl + "/pricing/")
    .body(StringBody(productPriceString("${productId}")))
    .check(status is 200)

  private val addProductStock = http("post - product stock")
    .post(Enviroment.stockUrl + "/stock/${productId}/add/" + r.nextInt(1000))
    .check(status is 200)

  private val removeProductStock = http("post - product stock")
    .post(Enviroment.stockUrl + "/stock/${productId}/remove/" + r.nextInt(100))
    .check(status is 200)

  private val postProductAddCategoryHttp = http("post - add product category")
    .post(Enviroment.writeUrl + "/products/${productId}/categories/${categoryId}")
    .body(StringBody(productString("${productId}")))
    .check(status is 200)
    .check(bodyString exists)

  private val postEditProduct = http("post - edit Product")
    .put(Enviroment.writeUrl + "/products/")
    .body(StringBody(editProductString("${productId}")))
    .check(status is 200)
    .check(bodyString exists)

  val writeCommandsScenario: ScenarioBuilder = scenario("Write command scenario")
    .feed(categoryIdFeederRnd)
    .feed(productIdFeederRnd)
    .exec(postProductAddCategoryHttp)
    .pause(1)
    .exec(postEditProduct)

  val pricingCommandsScenario: ScenarioBuilder = scenario("Pricing command scenario")
    .feed(productIdFeederRnd)
    .exec(postProductPriceHttp)
    .pause(2)
    .exec(putProductPriceHttp)

  val stockCommandsScenario: ScenarioBuilder = scenario("Stock command scenario")
    .feed(productIdFeederRnd)
    .exec(addProductStock)
    .pause(5)
    .exec(removeProductStock)
    .pause(5)
    .exec(removeProductStock)
}
