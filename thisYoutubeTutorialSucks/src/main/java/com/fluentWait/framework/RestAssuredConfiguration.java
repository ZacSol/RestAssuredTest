package com.fluentWait.framework;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class RestAssuredConfiguration {

    @BeforeSuite(alwaysRun = true)
    public void configure(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
    }

}
