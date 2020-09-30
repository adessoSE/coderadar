package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.analyzer.service.AnalyzingService;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.DeleteProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserAdapter implements DeleteUserPort {

  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final DeleteProjectAdapter deleteProjectAdapter;
  private final RefreshTokenRepository refreshTokenRepository;
  private final AnalyzingService analyzingService;

  @SneakyThrows
  @Override
  public void deleteUser(long userId) {
    List<ProjectEntity> projectsForUser = projectRepository.findProjectsCreatedByUser(userId);
    for (ProjectEntity projectEntity : projectsForUser) {
      analyzingService.stop(projectEntity.getId());
      while (projectRepository.isBeingProcessed(projectEntity.getId())) {
        Thread.sleep(1L);
        // Very ugly, I know, the other option would be to tell the end user that they can't
        // delete this user at this time.
      }
      projectRepository.setBeingProcessed(projectEntity.getId(), true);
      deleteProjectAdapter.delete(projectEntity.getId());
    }
    refreshTokenRepository.deleteByUser(userId);
    userRepository.deleteById(userId);
  }
}
