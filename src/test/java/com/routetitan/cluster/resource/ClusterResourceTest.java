package com.routetitan.cluster.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ClusterResourceTest {

    @Test
    public void testClusterStopsShouldReturnBadRequestWhenBodyIsEmpty() {
        given()
                .contentType(ContentType.JSON)
                .body("[]")
                .post("/cluster")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testClusterStopsShouldReturnStopsWithClusterId() {
        String requestBody = "[{\"id\":\"1001\",\"city\":\"Amsterdam\",\"zip\":\"1012 RR\",\"lat\":\"52.372992\",\"lng\":\"4.890011\"},{\"id\":\"1002\",\"city\":\"Roden\",\"zip\":\"9301 KB\",\"lat\":\"53.1378\",\"lng\":\"6.435663\"}]";

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/cluster")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }
}