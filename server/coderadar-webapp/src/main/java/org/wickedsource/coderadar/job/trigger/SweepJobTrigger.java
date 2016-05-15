package org.wickedsource.coderadar.job.trigger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SweepJobTrigger {

    @Scheduled(fixedDelay = 5000)
    private void trigger(){
        // only if MASTER!
        // for each project:
        // check against projects sweep strategy which commits are to be sweeped
        // for each commit, queue a SweepJob
    }
}
