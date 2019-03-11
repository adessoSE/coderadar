package org.wickedsource.coderadar.file.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class FileRepositoryImpl implements FileRepositoryCustom {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public File findInCommit(String filepath, String commitName, Long projectId) {
    Query query =
        entityManager.createQuery(
            "select f from Commit c join c.files a join a.id.file f where f.filepath=:filepath and c.name=:commitName and c.project.id = :projectId");
    query.setParameter("filepath", filepath);
    query.setParameter("projectId", projectId);
    query.setParameter("commitName", commitName);
    List<File> files = query.getResultList();

    if (files.size() == 1) {
      return files.get(0);
    } else if (files.size() > 1) {
      // Usually, we only get one file as result. However in the exotic case that MySQL is used
      // (whose queries
      // are case insensitive by default) and the same file exists in the database more than once
      // with different
      // upper or lower case characters, we can have more than one result. In this case, we select
      // the correct
      // file by hand.
      for (File file : files) {
        if (file.getFilepath().equals(filepath)) {
          return file;
        }
      }
    }

    return null;
  }
}
