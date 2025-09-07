package com.leandroperez.taf.sut.api.petstore.sop.service;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

/**
 * @author Leandro Henrique Perez
 */

public class ExampleApiService extends BaseApiService {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";
    private static final String PATH_PET_FIND_BY_STATUS = "/pet/findByStatus";

    private RequestSpecification requestSpec;
    private Response response;

    public ExampleApiService() {
    }


    public RequestSpecification authenticateUserAndStoreRequestExample01() {
        return this.requestSpec = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("accept", "application/json")
                .header("api_key", API_KEY)
                .contentType("application/json");
    }


    public RequestSpecification authenticateUserExample02() {
        return createBasicRequestSpec(
                BASE_URI,
                "application/json",
                ContentType.JSON
        ).header("api_key", API_KEY);
    }


    public Response getPetsByStatus(String status) {
        response = requestSpec
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus")
                .then()
                .extract()
                .response();
        return response;
    }


    public boolean isPetByStatusValidList() {
        try {
            response.then()
                    .body("size()", greaterThan(0))
                    .body("id", everyItem(notNullValue()))
                    .body("id", everyItem(instanceOf(Number.class)))
                    .body("status", hasItem("available"))
                    .body("photoUrls.flatten()", hasItem("http://example.com/pic.jpg"));
            return true;

        } catch (AssertionError e) {
            return false;
        }
    }


    public boolean isInStatusCodeSafe(String expectedStatusCode) {
        return isInStatusCodeSafe(response, expectedStatusCode);
    }


}
