package com.routetitan.cluster.resource;

import com.routetitan.cluster.resource.model.request.ClusterStopsRequest;
import com.routetitan.cluster.resource.model.request.Stop;
import com.routetitan.cluster.resource.model.response.ClusterStopsResponse;
import com.routetitan.cluster.resource.model.response.ClusteredStop;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ClusterResourceTest {

    @Test
    public void testClusterStopsShouldReturnBadRequestWhenBodyIsEmpty() {
        ClusterStopsRequest request = new ClusterStopsRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/cluster")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testClusterStopsShouldReturnStopsWithClusterId() {
        ClusterStopsRequest request = buildRequest();

        ClusterStopsResponse response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(request)
                .when()
                .post("/cluster")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .body().as(ClusterStopsResponse.class);

        assertThat(response.getStops(), hasSize(3));
        List<ClusteredStop> stops = response.getStops();
        for (int i = 0; i < stops.size(); i++) {
            ClusteredStop stop = stops.get(i);
            assertThat(stop.getId(), is(request.getStops().get(i).getId()));
            assertThat(stop.getCity(), is(request.getStops().get(i).getCity()));
            assertThat(stop.getZip(), is(request.getStops().get(i).getZip()));
            assertThat(stop.getLat(), is(request.getStops().get(i).getLat()));
            assertThat(stop.getLng(), is(request.getStops().get(i).getLng()));
            assertThat(Integer.parseInt(stop.getCluster()), lessThan((7)));
        }
    }

    private ClusterStopsRequest buildRequest() {
        List<Stop> stops = Arrays.asList(
                new Stop("1001", "Amsterdam", "1012 RR", "52.372992", "4.890011"),
                new Stop("1002", "Roden", "9301 KB", "53.1378", "6.435663"),
                new Stop("1003", "Berkel En Rodenrijs", "2651 JN", "51.99419", "4.474514")
        );

        return new ClusterStopsRequest(stops);
    }
}