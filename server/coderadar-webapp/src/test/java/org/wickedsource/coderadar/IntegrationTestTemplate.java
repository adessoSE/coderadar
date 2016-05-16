package org.wickedsource.coderadar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestCoderadar.class)
@WebAppConfiguration
public abstract class IntegrationTestTemplate {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

}
