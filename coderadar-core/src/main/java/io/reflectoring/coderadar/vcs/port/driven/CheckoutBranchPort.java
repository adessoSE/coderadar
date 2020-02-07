package io.reflectoring.coderadar.vcs.port.driven;

import java.io.File;

public interface CheckoutBranchPort {
  boolean checkoutBranch(File repositoryRoot, String branchName);
}
