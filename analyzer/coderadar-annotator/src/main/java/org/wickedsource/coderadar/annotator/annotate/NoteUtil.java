package org.wickedsource.coderadar.annotator.annotate;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;

public class NoteUtil {

    public static final String CODERADAR_NAMESPACE = "refs/notes/coderadar";

    private static NoteUtil INSTANCE;

    public static NoteUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteUtil();
        }
        return INSTANCE;
    }

    private NoteUtil() {
    }

    /**
     * Retrieves the note within the coderadar namespace of the specified commit.
     *
     * @param gitClient the git client.
     * @param commit    SHA1 hash of the commit to read the note from.
     * @return the coderadar-specific note of the specified git object or null if no such note exists.
     */
    public String getCoderadarNote(Git gitClient, String commit) {
        try {
            RevCommit commitObject = CommitUtils.getCommit(gitClient.getRepository(), commit);
            Note note = gitClient.notesShow()
                    .setNotesRef(CODERADAR_NAMESPACE)
                    .setObjectId(commitObject)
                    .call();
            if (note != null) {
                return BlobUtils.getContent(gitClient.getRepository(), note.getData());
            } else {
                return null;
            }
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the coderadar-specific note on the given git commit. Replaces an old coderadar-specific note, if one exists.
     *
     * @param gitClient the git client.
     * @param commit    SHA1 hash of the commit to add the note to.
     * @param note      the coderadar-specific note to set.
     */
    public void setCoderadarNote(Git gitClient, String commit, String note) {
        try {
            RevCommit commitObject = CommitUtils.getCommit(gitClient.getRepository(), commit);
            gitClient.notesAdd()
                    .setNotesRef(CODERADAR_NAMESPACE)
                    .setObjectId(commitObject)
                    .setMessage(note)
                    .call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

}
