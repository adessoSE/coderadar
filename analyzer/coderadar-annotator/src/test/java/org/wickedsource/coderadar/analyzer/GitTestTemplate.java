package org.wickedsource.coderadar.analyzer;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * Template for test cases that need a git repository.
 */
public abstract class GitTestTemplate extends Assert {

    private Logger logger = LoggerFactory.getLogger(GitTestTemplate.class);

    protected File testRepo;

    protected Git git;

    protected PersonIdent author = new PersonIdent("Test Author",
            "author@test.com");

    protected PersonIdent committer = new PersonIdent("Test Committer",
            "committer@test.com");

    @Before
    public void setUp() throws Exception {
        testRepo = initRepo();
        git = Git.open(testRepo);
        logger.info("created temporary git repository at {}", testRepo);
    }

    @After
    public void cleanUp() throws Exception {
        git.close();
        FileUtils.deleteDirectory(testRepo.getParentFile());
        logger.info("deleted temporary git repository at {}", testRepo);
    }

    protected File initRepo() throws Exception {
        String tmpDir = System.getProperty("java.io.tmpdir");
        assertNotNull("java.io.tmpdir was null", tmpDir);
        File dir = new File(tmpDir, "git-test-case-" + System.nanoTime());
        assertTrue(dir.mkdir());

        Git.init().setDirectory(dir).setBare(false).call();
        File repo = new File(dir, Constants.DOT_GIT);
        assertTrue(repo.exists());

        return repo;
    }

    protected void add(String path, String content) throws Exception {
        File file = new File(testRepo.getParentFile(), path);
        if (!file.getParentFile().exists())
            assertTrue(file.getParentFile().mkdirs());
        if (!file.exists())
            assertTrue(file.createNewFile());
        PrintWriter writer = new PrintWriter(file);
        if (content == null)
            content = "";
        try {
            writer.print(content);
        } finally {
            writer.close();
        }
        git.add().addFilepattern(path).call();
    }

    protected RevCommit commit(File repo) throws Exception {
        RevCommit commit = git.commit()
                .setMessage("test commit")
                .setAuthor(author)
                .setCommitter(committer)
                .call();
        return commit;
    }

    protected RevCommit commit() throws Exception {
        return commit(testRepo);
    }

    protected void add(File repo, List<String> paths,
                       List<String> contents) throws Exception {
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            String content = contents.get(i);
            File file = new File(repo.getParentFile(), path);
            if (!file.getParentFile().exists())
                assertTrue(file.getParentFile().mkdirs());
            if (!file.exists())
                assertTrue(file.createNewFile());
            PrintWriter writer = new PrintWriter(file);
            if (content == null)
                content = "";
            try {
                writer.print(content);
            } finally {
                writer.close();
            }
            git.add().addFilepattern(path).call();
        }
    }

    protected void addNote(String commit, String namespace, String note) throws Exception {
        RevCommit commitObject = CommitUtils.getCommit(git.getRepository(), commit);
        git.notesAdd()
                .setMessage(note)
                .setNotesRef(namespace)
                .setObjectId(commitObject)
                .call();
    }

    protected String getNote(String commit, String namespace) throws Exception {
        RevCommit commitObject = CommitUtils.getCommit(git.getRepository(), commit);
        Note note = git.notesShow()
                .setNotesRef(namespace)
                .setObjectId(commitObject)
                .call();
        return BlobUtils.getContent(git.getRepository(), note.getData());
    }

    protected RevCommit addAndCommitTestData() throws Exception {
        add("dir/file1.txt", "file1");
        add("dir/file2.java", "file2");
        add("mainfile.sh", "mainfile");
        return commit();
    }

}
