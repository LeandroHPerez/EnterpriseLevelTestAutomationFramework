package com.leandroperez.taf.sut.api.petstore.sop.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author Leandro Henrique Perez
 */

public class BaseApiService {


    protected RequestSpecification createBasicRequestSpec(String baseUri, ContentType acceptTypeHeader, ContentType contentType) {
        return RestAssured.given()
                .baseUri(baseUri)
                .header("Accept", acceptTypeHeader)
                .contentType(contentType);
    }


    protected RequestSpecification createBasicRequestSpec(String baseUri, String acceptTypeHeader, ContentType contentType) {
        return RestAssured.given()
                .baseUri(baseUri)
                .header("Accept", acceptTypeHeader)
                .contentType(contentType);
    }


    protected boolean validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        return actualStatusCode == expectedStatusCode;
    }


    protected Boolean isInStatusCodeSafe(Response response, String expectedStatusCode) {
        try {
            int statusCode = Integer.parseInt(expectedStatusCode);
            response.then().statusCode(statusCode);
            return true;
        } catch (NumberFormatException e) {
            return null; // Fail of conversion from string to integer
        } catch (AssertionError e) {
            return false; //Invalid status code
        }
    }



}
