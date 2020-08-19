package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;

import java.util.List;

public interface DetachContributorPort {

    /**
     * Deletes the "works on" relationship between the contributor
     * and the project
     *
     * @param contributor
     * @param projectId
     */
    void detachContributorFromProject(Contributor contributor, long projectId);

    /**
     * Deletes the "works on" relationship between the contributors
     * and the project
     *
     * @param contributors
     * @param projectId
     */
    void detachContributorsFromProject(List<Contributor> contributors, long projectId);

}
