package com.demo.test.productcatalogtests.util;

object Enviroment {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://34.77.45.240:5000/v1")
  val users = scala.util.Properties.envOrElse("numberOfUsers", "1")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "10000") // in milliseconds

  val writeUrl = scala.util.Properties.envOrElse("writeUrl", "http://34.77.45.240:4000/v1")
  val readUrl = scala.util.Properties.envOrElse("writeUrl", "http://34.77.45.240:5000/v1")
  val pricingUrl = scala.util.Properties.envOrElse("pricingUrl", "http://104.155.125.113:8080/v1")
  val stockUrl = scala.util.Properties.envOrElse("stockUrl", "http://35.240.43.86:8080/v1")
}