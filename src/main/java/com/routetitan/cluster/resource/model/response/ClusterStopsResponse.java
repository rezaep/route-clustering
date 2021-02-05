package com.routetitan.cluster.resource.model.response;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ClusterStopsResponse {
    @NotEmpty
    private List<ClusteredStop> stops;

    public ClusterStopsResponse() {
    }

    public ClusterStopsResponse(List<ClusteredStop> stops) {
        this.stops = stops;
    }

    public List<ClusteredStop> getStops() {
        return stops;
    }

    public void setStops(List<ClusteredStop> stops) {
        this.stops = stops;
    }
}
