package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.graph.AbstractMapper;
import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;

public class FindingsMapper extends AbstractMapper<Finding, FindingEntity> {

  @Override
  public Finding mapNodeEntity(FindingEntity nodeEntity) {
    Finding domainObject = new Finding();
    domainObject.setCharEnd(nodeEntity.getCharEnd());
    domainObject.setCharStart(nodeEntity.getCharStart());
    domainObject.setLineEnd(nodeEntity.getLineEnd());
    domainObject.setLineStart(nodeEntity.getLineStart());
    return domainObject;
  }

  @Override
  public FindingEntity mapDomainObject(Finding domainObject) {
    FindingEntity findingEntity = new FindingEntity();
    findingEntity.setCharEnd(domainObject.getCharEnd());
    findingEntity.setCharStart(domainObject.getCharStart());
    findingEntity.setLineEnd(domainObject.getLineEnd());
    findingEntity.setLineStart(domainObject.getLineStart());
    return findingEntity;
  }
}
