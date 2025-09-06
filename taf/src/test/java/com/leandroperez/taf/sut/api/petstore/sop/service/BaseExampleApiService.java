package com.leandroperez.taf.sut.api.petstore.sop.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * @author Leandro Henrique Perez
 */

public class BaseExampleApiService {

    private static final String BASE_URL = "https://petstore3.swagger.io/api/v3";

    public static Response buscarPetsPorStatus(String status) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus")
                .then()
                .extract().response();
    }
}
