package org.wickedsource.coderadar.commit.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.core.configuration.Injector;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Updates a Commit's date coordinate fields according to the specified date. The date coordinate
 * fields allow easier database queries when working with dates.
 */
public class UpdateDateCoordinatesEntityListener {

    @Autowired
    private CoderadarConfiguration config;

    @PrePersist
    @PreUpdate
    public void updateDateCoordinates(Commit commit) {
        Injector.getInstance().inject(this);
        DateCoordinates dateCoordinates = new DateCoordinates();
        dateCoordinates.updateFromDate(commit.getTimestamp(), config.getDateLocale());
        commit.setDateCoordinates(dateCoordinates);
    }

}
