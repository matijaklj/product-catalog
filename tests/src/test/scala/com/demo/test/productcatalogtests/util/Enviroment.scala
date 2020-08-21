package com.demo.test.productcatalogtests.util;

object Enviroment {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:4000/v1/products")
  val users = scala.util.Properties.envOrElse("numberOfUsers", "1")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "5000") // in milliseconds

}