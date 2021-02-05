package com.routetitan.cluster.resource;

import com.routetitan.cluster.resource.model.request.ClusterStopsRequest;
import com.routetitan.cluster.resource.model.response.ClusterStopsResponse;
import com.routetitan.cluster.resource.model.response.ClusteredStop;
import com.routetitan.cluster.service.ClusterService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public ClusterStopsResponse clusterStops(@Valid ClusterStopsRequest request) {
        List<ClusteredStop> clusteredStops = clusterService.clusterStops(request.getStops());
        return new ClusterStopsResponse(clusteredStops);
    }
}
