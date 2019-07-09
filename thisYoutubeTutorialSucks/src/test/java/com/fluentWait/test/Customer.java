package com.fluentWait.test;

import com.fluentWait.test.common.EndPoint;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Customer {

    @Test
    public void validateCustomer(){
        given().get("http://localhost:8080/api/v1/customers").then().statusCode(200).log().all();
    }

    @Test(groups = "demo")
    public void validateCustomer2(){
        given().get(EndPoint.GET_CUSTOMERS).then().statusCode(200).log().all();
    }
}
