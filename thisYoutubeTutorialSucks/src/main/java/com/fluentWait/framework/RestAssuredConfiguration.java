package com.fluentWait.framework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

public class RestAssuredConfiguration {

    @BeforeSuite(alwaysRun = true)
    public void configure(){
        System.out.println("inside configure method");

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";

//        These will allow the system to function with different web contexts
//        String port = System.getProperty("server.port");
//        if (port == null){RestAssured.port = 8080;}
//        else{RestAssured.port = Integer.valueOf(port);}
//
//        String baseHost = System.getProperty("server.host");
//        if (baseHost == null){RestAssured.baseURI = "http://localhost";}
//        else{RestAssured.baseURI = baseHost;}
//
//        String basePath = System.getProperty("server.base");
//        if (basePath == null){RestAssured.basePath = "api/v1";}
//        else{RestAssured.basePath = basePath;}

    }

    public RequestSpecification getRequestSpecification(){
        return RestAssured.given().contentType(ContentType.JSON);
    }

    public Response getResponse(RequestSpecification requestSpecification, String endpoint, int status){
        Response response = requestSpecification.get(endpoint);
        Assert.assertEquals(response.getStatusCode(),status);
        response.then().log().all();
        return response;
    }
}
