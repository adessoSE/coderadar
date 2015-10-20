package org.wickedsource.coderadar.analyzer.note;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;

public class NoteUtil {

    /**
     * Retrieves the coderadar-specific note previously added to the given git object. Only the part of the note within the
     * tags {coderadar} and {/coderadar} is returned.
     *
     * @param gitClient the git client.
     * @param objectId  id of the object whose note to read.
     * @return the coderadar-specific note of the specified git object or null if no such note exists.
     */
    public String getNote(Git gitClient, AnyObjectId objectId) {

    }

    /**
     * Sets the coderadar-specific note on the given git object. Replaces an old coderadar-specific note, if one exists.
     * If a note already exists that is not within the tags {coderadar} and {/coderadar}, it will be ignored.
     *
     * @param gitClient the git client.
     * @param objectId  id of the object whose note to set.
     * @param note      the coderadar-specific note to set.
     */
    public void setNote(Git gitClient, AnyObjectId objectId, String note) {

    }

}
