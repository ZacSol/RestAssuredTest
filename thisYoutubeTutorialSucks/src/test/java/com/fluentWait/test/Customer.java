package com.fluentWait.test;

import com.fluentWait.framework.RestAssuredConfiguration;
import com.fluentWait.test.common.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

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

    @Test(groups = "demo") // http://localhost:8080/api/v1/customers/getCustomerQuery?id=3
    public void validateQueryParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("id",3).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_QUERY_PARAM).then().statusCode(200).log().all();
    }
    @Test(groups = "demo") // http://localhost:8080/api/v1/customers/3
    public void validatePathParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.pathParam("id",3).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_CUSTOMER_PATH_PARAM).then().statusCode(200).log().all();
    }
    @Test
    public void validateFormParameters(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.accept(ContentType.JSON).formParams("id",3).log().all();
        given().spec(requestSpecification).post(EndPoint.POST_CUSTOMER_PARAM).then().statusCode(200).log().all();
    }

}
