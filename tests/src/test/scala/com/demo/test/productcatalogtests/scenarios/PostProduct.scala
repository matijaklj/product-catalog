package com.demo.test.productcatalogtests.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

object PostProduct {

  var i = 6000

  val feeder = Iterator.continually(Map("id" -> {
    i=i+1
    i.toString
  }))

  val productString = (id:String) => """{ "id": """ + "\"" + id + "\"" + """, "name":"test_Gatling", "description":"test desc"}"""

  /*
  val feeder = Iterator.continually(Map("id" -> (Random.alphanumeric.take(30).mkString)))


    val productString = (id:Option[String]) =>  id match {
    case None => ""
    case Some(s: String) => """{ "id": """ + "\"" + s + "\"" + """, name":"test_Gatling", "description":"test desc"}"""
  }
   */
  val postUserHttp = http("post product")
    .post("/")
    .body(StringBody(productString("${id}")))
    .check(status is 201)
    .check(bodyString exists)

  val postProduct = scenario("post product")
    .feed(feeder)
    .exec(postUserHttp)
}
