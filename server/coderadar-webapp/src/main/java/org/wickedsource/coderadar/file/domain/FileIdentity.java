package org.wickedsource.coderadar.file.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * A FileIdentity connects Files that have been renamed at some point in time. Two Files
 * with the same FileIdentity mark the same File, except that the file now has a new name.
 * If a file has not been renamed in its history, it has a unique FileIdentity.
 */
@Entity
@Table
public class FileIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
