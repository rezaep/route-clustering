package com.routetitan.cluster.resource.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ClusterStopsRequest {
    @NotNull
    @NotEmpty
    private List<Stop> stops;

    public ClusterStopsRequest() {
    }

    public ClusterStopsRequest(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}
