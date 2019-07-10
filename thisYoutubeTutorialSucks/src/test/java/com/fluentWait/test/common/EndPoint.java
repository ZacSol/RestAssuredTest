package com.fluentWait.test.common;

public interface EndPoint {
    String GET_CUSTOMERS = "/customers";
    String GET_CUSTOMER_PATH_PARAM = "/customers/{id}";
    String GET_CUSTOMER_QUERY_PARAM = "/customers/getCustomerQuery";
    String POST_CUSTOMER_PARAM = "/customers";

    String GOOGLE_API = "/v1/volumes";
}
