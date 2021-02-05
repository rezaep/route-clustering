package com.routetitan.cluster.service;

import com.routetitan.cluster.resource.model.request.Stop;
import com.routetitan.cluster.resource.model.response.ClusteredStop;
import smile.clustering.XMeans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClusterService {
    private static final int KMAX = 7;

    @Inject
    ClusterVisualizationService visualizationService;

    public List<ClusteredStop> clusterStops(List<Stop> stops) {
        double[][] dataset = extractLocation(stops);
        XMeans xmeans = XMeans.fit(dataset, KMAX);
        visualizationService.visualizeCluster(dataset, xmeans);
        return convertToClusteredStopList(stops, xmeans.y);
    }

    private double[][] extractLocation(List<Stop> stops) {
        return stops.stream()
                .map(stop -> new double[]{Double.parseDouble(stop.getLng()), Double.parseDouble(stop.getLat())})
                .toArray(double[][]::new);
    }

    private List<ClusteredStop> convertToClusteredStopList(List<Stop> stops, int[] y) {
        List<ClusteredStop> clusteredStops = new ArrayList<>();

        for (int i = 0; i < stops.size(); i++) {
            Stop stop = stops.get(i);
            clusteredStops.add(new ClusteredStop(stop.getId(), stop.getCity(), stop.getZip(), stop.getLat(), stop.getLng()
                    , String.valueOf(y[i])));
        }

        return clusteredStops;
    }
}
