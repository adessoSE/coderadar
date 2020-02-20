package io.reflectoring.coderadar.rest.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driver.branch.get.ListBranchesUseCase;
import io.reflectoring.coderadar.rest.domain.GetBranchResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListBranchesController {
  private final ListBranchesUseCase listBranchesUseCase;

  public ListBranchesController(ListBranchesUseCase listBranchesUseCase) {
    this.listBranchesUseCase = listBranchesUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/branches", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetBranchResponse>> listBranches(@PathVariable Long projectId) {
    List<Branch> branches = listBranchesUseCase.listBranchesInProject(projectId);
    List<GetBranchResponse> responses = new ArrayList<>(branches.size());
    for (Branch branch : branches) {
      responses.add(new GetBranchResponse(branch.getId(), branch.getName()));
    }
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
