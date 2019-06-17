package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NodeSerializer extends StdSerializer<Node> {

    public NodeSerializer() {
        this(null);
    }

    public NodeSerializer(Class<Node> t) {
        super(t);
    }

    @Override
    public void serialize(Node node, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("filename",node.getFilename());
        gen.writeStringField("path",node.getPath());
        gen.writeStringField("packageName",node.getPackageName());
        gen.writeArrayFieldStart("children");
        for(Node child : node.getChildren()){
            gen.writeObject(child);
        }
        gen.writeEndArray();
        gen.writeArrayFieldStart("dependencies");
        for(Node dependency : node.getDependencies()){
            gen.writeStartObject();
            gen.writeStringField("filename",dependency.getFilename());
            gen.writeStringField("path",dependency.getPath());
            gen.writeStringField("packageName",dependency.getPackageName());
            gen.writeArrayFieldStart("children");
            gen.writeEndArray();
            gen.writeArrayFieldStart("dependencies");
            gen.writeEndArray();
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeNumberField("layer",node.getLayer());
        gen.writeEndObject();
    }
}
