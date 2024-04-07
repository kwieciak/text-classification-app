package org.example.project1.metric;

import org.example.project1.metric.metrics.ChebyshevMetric;
import org.example.project1.metric.metrics.EuclideanMetric;
import org.example.project1.metric.metrics.ManhattanMetric;

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
    };
    public abstract Metric createMetric();
}

