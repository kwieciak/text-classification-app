package org.example.project1.metric;

import org.example.project1.metric.metrics.ChebyshevMetric;
import org.example.project1.metric.metrics.EuclideanMetric;
import org.example.project1.metric.metrics.ManhattanMetric;
import org.example.project1.metric.metrics.WeightedMetric;

public enum MetricType {
    CHEBYSHEV {
        @Override
        public Metric createMetric() {
            return new ChebyshevMetric();
        }
    },
    EUCLIDEAN {
        @Override
        public Metric createMetric() {
            return new EuclideanMetric();
        }
    },
    MANHATTAN {
        @Override
        public Metric createMetric() {
            return new ManhattanMetric();
        }
    },
    WEIGHTED {
        @Override
        public Metric createMetric() {
            return new WeightedMetric(EUCLIDEAN.createMetric(), 0.5);   // to można zmienić na inny typ metryki
        }
    };
    public abstract Metric createMetric();
}

