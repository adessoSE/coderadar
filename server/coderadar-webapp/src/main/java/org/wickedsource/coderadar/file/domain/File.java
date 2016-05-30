package org.wickedsource.coderadar.file.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Represents a file in a VCS repository.
 */
@Entity
@Table
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String filepath;

    @ManyToOne(cascade = CascadeType.ALL)
    private FileIdentity identity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public FileIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(FileIdentity identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File that = (File) o;

        if (!filepath.equals(that.filepath)) return false;
        return identity.equals(that.identity);

    }

    @Override
    public int hashCode() {
        int result = filepath.hashCode();
        result = 31 * result + identity.hashCode();
        return result;
    }
}
