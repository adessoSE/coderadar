package org.wickedsource.coderadar.annotator.annotate;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.GitTestTemplate;

public class NoteUtilTest extends GitTestTemplate {

    @Test
    public void noteIsAddedToCoderadarNamespace() throws Exception {
        String noteContent = "some file metrics...";
        add("myTestFile.txt", "testLine");
        RevCommit commit = commit();

        NoteUtil.getInstance().setCoderadarNote(git, commit.getName(), noteContent);
        String namespacedNote = getNote(commit.getName(), "coderadar");

        Assert.assertEquals(noteContent, namespacedNote);
    }

    @Test
    public void noteDoesNotConflictWithOtherNamespaces() throws Exception {
        String externalNoteContent = "external note...";
        String coderadarNoteContent = "some file metrics...";
        add("myTestFile.txt", "testLine");
        RevCommit commit = commit();

        addNote(commit.getName(), "otherNamespace", externalNoteContent);
        NoteUtil.getInstance().setCoderadarNote(git, commit.getName(), coderadarNoteContent);

        String externalNote = getNote(commit.getName(), "otherNamespace");
        String coderadarNote = getNote(commit.getName(), "coderadar");

        Assert.assertEquals(externalNoteContent, externalNote);
        Assert.assertEquals(coderadarNoteContent, coderadarNote);
    }

    @Test
    public void noteIsReadFromCoderadarNamespace() throws Exception {
        String noteContent = "some file metrics...";
        add("myTestFile.txt", "testLine");
        RevCommit commit = commit();

        addNote(commit.getName(), "coderadar", noteContent);
        String namespacedNote = NoteUtil.getInstance().getCoderadarNote(git, commit.getName());

        Assert.assertEquals(noteContent, namespacedNote);
    }

}
