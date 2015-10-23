package org.wickedsource.coderadar.analyzer.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;

public class GsonFactory {

    /**
     * Creates a JSON-Parser configured for serializing and deserializing coderadar metrics.
     */
    public Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Metric.class, new MetricTypeAdapter());
        return builder.create();
    }

}
