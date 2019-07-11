package com.fluentWait.test;

import com.fluentWait.framework.RestAssuredConfiguration;
import com.fluentWait.test.bin.CustomerBin;
import com.fluentWait.test.common.EndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Customer {

    @Test
    public void validateCustomer(){
        System.out.println("Hitting test method validateCustomer");
        given().get("http://localhost:8080/api/v1/customers").then().statusCode(200).log().all();
    }

    @Test(groups = "demo")
    public void validateCustomer2(){
        System.out.println("Hitting test method: validateCustomer2");
        given().get(EndPoint.GET_CUSTOMERS).then().statusCode(200).log().all();
//        given().get("/customers").then().statusCode(200).log().all();
    }

    @Test(groups = {"demo","response"}) // http://localhost:8080/api/v1/customers/getCustomerQuery?id=3
    public void validateQueryParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("id",3).log().all();
        // Checks for 200 status code, not any returned data
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_QUERY_PARAM).then().statusCode(200).log().all();
        // Getting Response
        Response response = given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_QUERY_PARAM);
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
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.pathParam("id",3).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_PATH_PARAM).then().statusCode(200).log().all();
    }
//    @Test(groups = "form")
//    public void validateFormParameters(){
//        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
//        requestSpecification.accept(ContentType.JSON).formParams("id",3).log().all();
//        given().spec(requestSpecification).post(EndPoint.POST_CUSTOMER_PARAM).then().statusCode(200).log().all();
//    }

}
