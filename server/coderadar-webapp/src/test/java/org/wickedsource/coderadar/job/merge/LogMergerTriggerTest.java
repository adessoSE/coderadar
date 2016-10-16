package org.wickedsource.coderadar.job.merge;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.wickedsource.coderadar.factories.entities.EntityFactory.project;

public class LogMergerTriggerTest {

    private CommitRepository commitRepository = Mockito.mock(CommitRepository.class);
    private JobLogger jobLogger = Mockito.mock(JobLogger.class);
    private MergeLogJobRepository mergeLogJobRepository = Mockito.mock(MergeLogJobRepository.class);
    private ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private LogMergerTrigger trigger;

    @Before
    public void setup() {
        trigger = new LogMergerTrigger(jobLogger, mergeLogJobRepository, projectRepository, commitRepository);
    }

    @Test
    public void dontTriggerIfAlreadyQueued() {
        whenQueuedJobCountIs(1);
        boolean shouldBeTriggered = trigger.shouldJobBeQueuedForProject(project().validProject());
        assertThat(shouldBeTriggered).isFalse();
    }

    @Test
    public void dontTriggerIfNoMergedCommits() {
        whenQueuedJobCountIs(0);
        whenUnmergedCommitCountIs(0);
        whenTotalCommitCountIs(100);
        boolean shouldBeTriggered = trigger.shouldJobBeQueuedForProject(project().validProject());
        assertThat(shouldBeTriggered).isFalse();
    }

    @Test
    public void triggerIfUnmergedCommitsAvailable() {
        whenQueuedJobCountIs(0);
        whenUnmergedCommitCountIs(1);
        whenTotalCommitCountIs(100);
        boolean shouldBeTriggered = trigger.shouldJobBeQueuedForProject(project().validProject());
        assertThat(shouldBeTriggered).isTrue();
    }

    private void whenQueuedJobCountIs(int count) {
        when(mergeLogJobRepository.countByProcessingStatusInAndProjectId(anyList(), anyLong())).thenReturn(count);
    }

    private void whenUnmergedCommitCountIs(int count) {
        when(commitRepository.countByProjectIdAndScannedTrueAndMergedFalse(anyLong())).thenReturn(count);
    }

    private void whenTotalCommitCountIs(int count) {
        when(commitRepository.countByProjectId(anyLong())).thenReturn(count);
    }

}