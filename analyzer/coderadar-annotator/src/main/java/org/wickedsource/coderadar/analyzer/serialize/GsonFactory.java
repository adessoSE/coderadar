package org.wickedsource.coderadar.analyzer.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.wickedsource.coderadar.analyzer.api.Metric;

public class GsonFactory {

    private static GsonFactory INSTANCE;

    private GsonFactory() {

    }

    public static GsonFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GsonFactory();
        }
        return INSTANCE;
    }

    /**
     * Creates a JSON-Parser configured for serializing and deserializing coderadar metrics.
     */
    public Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Metric.class, new MetricTypeAdapter());
        return builder.create();
    }

}
