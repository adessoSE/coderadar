package org.wickedsource.coderadar.analyzer.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.wickedsource.coderadar.analyzer.api.Metric;

import java.io.IOException;

public class MetricTypeAdapter extends TypeAdapter<Metric> {

    @Override
    public void write(JsonWriter out, Metric value) throws IOException {
        out.jsonValue(value.getId());
    }

    @Override
    public Metric read(JsonReader in) throws IOException {
        return new Metric(in.nextString());
    }
}
