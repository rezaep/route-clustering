package com.routetitan.cluster.resource;

import com.routetitan.cluster.resource.model.request.Stop;
import com.routetitan.cluster.service.ClusterService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cluster")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClusterResource {
    private final ClusterService clusterService;

    public ClusterResource(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @POST
    public Response add(List<Stop> stops) {
        if (stops == null || stops.size() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(clusterService.clusterStops(stops)).build();
    }
}
