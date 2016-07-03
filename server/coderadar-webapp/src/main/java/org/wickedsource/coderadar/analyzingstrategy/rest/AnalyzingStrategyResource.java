package org.wickedsource.coderadar.analyzingstrategy.rest;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class AnalyzingStrategyResource extends ResourceSupport {

    private Date fromDate;

    @NotNull
    private boolean active;

    public AnalyzingStrategyResource() {

    }

    public AnalyzingStrategyResource(Date fromDate, boolean active) {
        this.fromDate = fromDate;
        this.active = active;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
