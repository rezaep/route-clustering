package com.routetitan.cluster.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smile.clustering.XMeans;
import smile.plot.swing.Canvas;
import smile.plot.swing.ScatterPlot;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class ClusterVisualizationService {
    private final Logger log = LoggerFactory.getLogger(ClusterVisualizationService.class);

    public void visualizeCluster(double[][] dataset, XMeans xmeans) {
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
}
