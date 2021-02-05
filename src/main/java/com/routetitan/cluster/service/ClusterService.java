package com.routetitan.cluster.service;

import com.routetitan.cluster.resource.model.request.Stop;
import com.routetitan.cluster.resource.model.response.ClusteredStop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smile.clustering.XMeans;
import smile.plot.swing.Canvas;
import smile.plot.swing.ScatterPlot;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class ClusterService {
    private static final int KMAX = 7;
    private final Logger log = LoggerFactory.getLogger(ClusterService.class);

    public List<ClusteredStop> clusterStops(List<Stop> stops) {
        double[][] dataset = extractLocation(stops);
        XMeans xmeans = XMeans.fit(dataset, KMAX);
        visualizeCluster(dataset, xmeans);
        return convertToClusteredStopList(stops, xmeans.y);
    }

    private double[][] extractLocation(List<Stop> stops) {
        return stops.stream()
                .map(stop -> new double[]{Double.parseDouble(stop.getLng()), Double.parseDouble(stop.getLat())})
                .toArray(double[][]::new);
    }

    private void visualizeCluster(double[][] dataset, XMeans xmeans) {
        CompletableFuture.runAsync(() -> {
            try {
                Canvas plot = createPlot(dataset, xmeans);

                File parent = new File(System.getProperty("user.home"), "routetitan");
                if (!parent.exists()) {
                    parent.mkdir();
                }

                savePlotToFile(plot, parent);
            } catch (IOException e) {
                log.error("failed to save visualization.", e);
            }
        });
    }

    private Canvas createPlot(double[][] dataset, XMeans xmeans) {
        Canvas plot = ScatterPlot.of(dataset, xmeans.y, '.').canvas();
        plot.setAxisLabels("lat", "lng");
        plot.add(ScatterPlot.of(xmeans.centroids, '@'));
        return plot;
    }

    private void savePlotToFile(Canvas plot, File parent) throws IOException {
        BufferedImage image = plot.toBufferedImage(800, 800);
        javax.imageio.ImageIO.write(image, "png", new File(parent, "clusters_visualisation.png"));
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
