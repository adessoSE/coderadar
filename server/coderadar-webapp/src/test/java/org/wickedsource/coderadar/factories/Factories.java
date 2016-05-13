package org.wickedsource.coderadar.factories;

public class Factories {

    public static ProjectFactory project() {
        return new ProjectFactory();
    }

    public static ProjectResourceFactory projectResource() {
        return new ProjectResourceFactory();
    }

    public static JobFactory job() {
        return new JobFactory();
    }
}
