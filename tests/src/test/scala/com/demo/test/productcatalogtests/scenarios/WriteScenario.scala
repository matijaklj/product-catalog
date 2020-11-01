package com.demo.test.productcatalogtests.scenarios

import com.demo.test.productcatalogtests.util.Enviroment
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object WriteScenario {


  val categoryIdFeeder = csv("data/categoryId.csv").eager.circular;
  val categoryIdFeederRnd = csv("data/categoryId.csv").eager.random;
  val productIdFeeder = csv("data/productId.csv").eager.circular;


  var ids = 10000
  val productIdIncremental = Iterator.continually(Map("id" -> {
    ids=ids+1
    ids.toString
  }))

  val categoryString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_category_"""+ id +""""}"""

  val productString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_product_"""+ id +"""", "description":"test desc"}"""

  val editProductString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_edited_"""+ id +"""", "description":"test EDITED desc"}"""

  val postCategoryHttp = http("post - create category")
    .post(Enviroment.writeUrl + "/categories/")
    .body(StringBody(categoryString("${categoryId}")))
    .check(status is 200)

  val postProductHttp = http("post - create product")
    .post(Enviroment.writeUrl + "/products/")
    .body(StringBody(productString("${id}")))
    .check(status is 200)
    .check(bodyString exists)
    .check(bodyString saveAs "createdId")

  val postProductAddCategoryHttp = http("post - add product category")
    .post(Enviroment.writeUrl + "/products/${createdId}/categories/${categoryId}")
    .body(StringBody(productString("${id}")))
    .check(status is 200)
    .check(bodyString exists)

  val postEditProduct = http("post - edit Product")
    .put(Enviroment.writeUrl + "/products/")
    .body(StringBody(editProductString("${createdId}")))
    .check(status is 200)
    .check(bodyString exists)

  // the main write scenario
  val writeScenario = scenario("Write scenario")
    .feed(productIdIncremental)
    .feed(categoryIdFeeder)
    .group("write-group") {
      exitBlockOnFail {
        exec(postProductHttp)
          .pause(1)
          .exec(postProductAddCategoryHttp)
          .pause(1)
          .exec(postEditProduct)
      }
    }

  val fillWriteSideCategoriesScenario = scenario("Fill write side scenario - Categories")
    .feed(categoryIdFeeder)
    .exec(postCategoryHttp)

  val fillWriteSideScenario = scenario("Fill write side scenario")
    .feed(productIdIncremental)
    .feed(categoryIdFeederRnd)
    .exec(postProductHttp)
    .pause(1)
    .exec(postProductAddCategoryHttp)
}
