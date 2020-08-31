package com.demo.test.productcatalogtests.util;

object Enviroment {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://34.78.126.94:5000/v1")
  val users = scala.util.Properties.envOrElse("numberOfUsers", "1")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "10000") // in milliseconds

  val writeUrl = scala.util.Properties.envOrElse("writeUrl", "http://34.78.126.94:4000/v1")
  val readUrl = scala.util.Properties.envOrElse("writeUrl", "http://34.78.126.94:5000/v1")
  val pricingUrl = scala.util.Properties.envOrElse("pricingUrl", "http://34.78.155.195:8080/v1")
  val stockUrl = scala.util.Properties.envOrElse("stockUrl", "http://34.77.89.66:8080/v1")
}