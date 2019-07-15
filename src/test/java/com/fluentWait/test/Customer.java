package com.fluentWait.test;

import com.fluentWait.framework.RestAssuredConfiguration;
import com.fluentWait.test.bin.CustomerBin;
import com.fluentWait.test.common.EndPoint;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Customer {

    @Test
    public void validateCustomer(){
        System.out.println("\nHitting test method validateCustomer");
        given().get("http://localhost:8080/api/v1/customers").then().statusCode(200).log().all();
    }

    @Test(groups = "demo")
    public void validateCustomer2(){
        System.out.println("\nHitting test method: validateCustomer2");
        given().get(EndPoint.GET_CUSTOMERS).then().statusCode(200).log().all();
    }

    @Test(groups = {"demo","response"}) // http://localhost:8080/api/v1/customers/getCustomerQuery?id=3
    public void validateQueryParameter(){
        System.out.println("\nHitting test method: validateQueryParameter");
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("id",3).log().all();
        // Checks for 200 status code, not any returned data
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_QUERY_PARAM).then().statusCode(200).log().all();
        // Getting Response
        Response response = given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_QUERY_PARAM);
        // Test that the response is coming in within the specified time limit
        Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS)<=10000,"Response time is not within the specified limit.");
        // // Inline Validation
        // 1. Hard Assertion: If one value fails, execution is ended
        response.then().body("firstName",equalTo("Fiona")).body("lastName",equalTo("Glennann"));
        // 2. Soft Assertion: If one value fails, execution continues to check
        response.then().body("firstName",equalTo("Fiona"),"lastName",equalTo("Glennann"));

        // // Path Validation
        // 1. Hard Assertion
        Assert.assertEquals(response.path("firstName"),"Fiona");
        Assert.assertEquals(response.path("lastName"),"Glennann");
        Assert.assertEquals(response.path("city"),"L.A.");
        Assert.assertEquals(response.path("phone"),"555-555-1221","The phone number does not match expected value.");
        // 2. Soft Assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.path("firstName"),"Fiona", "First name do not match expected.");
        softAssert.assertEquals(response.path("lastName"),"Glennann", "Last name do not match expected.");
        softAssert.assertEquals(response.path("city"),"L.A.","The location does not match expected.");
        softAssert.assertEquals(response.path("phone"),"555-555-1221","The phone number does not match expected value.");
        softAssert.assertAll();

        // // Java Objects
        CustomerBin customerBin = response.as(CustomerBin.class);
        // 1. Hard Assertion
        Assert.assertEquals(customerBin.getFirstName(),"Fiona");
        Assert.assertEquals(customerBin.getLastName(),"Glennann");
        Assert.assertEquals(customerBin.getPhone(),"555-555-1221");
        Assert.assertEquals(customerBin.getCity(),"L.A.");
        // 2. Soft Assertion
        SoftAssert newSoftAssert = new SoftAssert();
        newSoftAssert.assertEquals(customerBin.getFirstName(),"Fiona", "First name do not match expected.");
        newSoftAssert.assertEquals(customerBin.getLastName(),"Glennann", "Last name do not match expected.");
        newSoftAssert.assertEquals(customerBin.getCity(),"L.A.","The location does not match expected.");
        newSoftAssert.assertEquals(customerBin.getPhone(),"555-555-1221","The phone number does not match expected value.");
        newSoftAssert.assertAll();

    }
    @Test(groups = "demo") // http://localhost:8080/api/v1/customers/3
    public void validatePathParameter(){
        System.out.println("\nHitting test method: validatePathParameter");
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.pathParam("id",3).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_PATH_PARAM).then().statusCode(200).log().all();
    }

    @Test(groups = {"demo","form"})
    public void submitForm(){
        System.out.println("\nHitting test method: submitForm");
        // Create new JSON object for customer data
        JsonObject newCustomer = new JsonObject();
        newCustomer.addProperty("firstName","John");
        newCustomer.addProperty("lastName","Doe");
        newCustomer.addProperty("city","Dallas");
        newCustomer.addProperty("phone","817-555-5555");
        // Send object data
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        given().spec(requestSpecification).body(newCustomer.toString())
                .when()
                .post("/customers")
//                .prettyPeek() // prints JSON out to console to view parsed data
                .then().statusCode(201).log().all();
    }

    @Test(groups = {"demo","fails","404"})
    public void getBadAddress(){
        System.out.println("\nHitting test method: getBadAddress");
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        given().spec(requestSpecification).get("/doesntExist").then().statusCode(404).log().all();
    }

    @Test(groups = {"demo","fails","415"})
    public void unsupportedMediaType(){
        System.out.println("\nHitting test method: unsupportedMediaType");
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecHTML();
        requestSpecification.accept(ContentType.JSON).formParams("id",3).log().all();
        given().spec(requestSpecification).post(EndPoint.POST_CUSTOMER_PARAM).then().statusCode(415).log().all();
    }
//
//    @Test(groups = {"fails","417"})
//    public void expectationFailed(){
//
//    }
//
//    @Test(groups = {"fails","422"})
//    public void nonProcessableEntity(){
//
//    }

}
