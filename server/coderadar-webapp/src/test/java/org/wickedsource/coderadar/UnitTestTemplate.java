package org.wickedsource.coderadar;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class UnitTestTemplate extends TestTemplate{

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

}
