package org.wickedsource.coderadar.commit.rest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.factories.Factories;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommitControllerTest extends ControllerTestTemplate {

    @InjectMocks
    private CommitController commitController;

    @Mock
    private CommitRepository commitRepository;

    @Spy
    private CommitResourceAssembler commitResourceAssembler;

    @Test
    public void getCommits() throws Exception {
        when(commitRepository.findAll(any(Pageable.class))).thenReturn(Factories.commit().commitPage());

        mvc().perform(get("/projects/1/commits"))
                .andExpect(status().isOk())
                .andExpect(contains(PagedResources.class));
    }

    @Override
    protected Object getController() {
        return commitController;
    }
}