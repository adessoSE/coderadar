package org.wickedsource.coderadar.testframework.template;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.wickedsource.coderadar.CoderadarTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoderadarTestApplication.class)
@WebAppConfiguration
public abstract class IntegrationTestTemplate extends TestTemplate{

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

}
