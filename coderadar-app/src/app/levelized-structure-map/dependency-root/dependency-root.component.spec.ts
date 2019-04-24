import {async, TestBed} from '@angular/core/testing';

import {DependencyRootComponent} from './dependency-root.component';
import {DependencyTreeProvider} from '../DependencyTreeProvider';
import {APP_INITIALIZER} from '@angular/core';
import {Globals} from '../Globals';
import {dependencyTreeProviderFactory} from '../app.module';
import {StructureMapComponent} from '../structure-map/structure-map.component';
import {DependencyTreeComponent} from '../dependency-tree/dependency-tree.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';


describe('DependencyRootComponent', () => {
  let httpTestingController: HttpTestingController;
  let service: DependencyTreeProvider;
  const testdata = {
    filename: 'coderadar',
    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar',
    packageName: 'org.wickedsource.coderadar',
    children: [
      {
        filename: 'analyzer',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer',
        packageName: 'org.wickedsource.coderadar.analyzer',
        children: [
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\rest',
            packageName: 'org.wickedsource.coderadar.analyzer.rest',
            children: [
              {
                filename: 'AnalyzerController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\rest\\AnalyzerController.java',
                packageName: 'org.wickedsource.coderadar.analyzer.rest.AnalyzerController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerPluginRegistry.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                    packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzerResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\rest\\AnalyzerResource.java',
                packageName: 'org.wickedsource.coderadar.analyzer.rest.AnalyzerResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'AnalyzerResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\rest\\AnalyzerResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.analyzer.rest.AnalyzerResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'service',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service',
            packageName: 'org.wickedsource.coderadar.analyzer.service',
            children: [
              {
                filename: 'AnalyzerPluginRegistry.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'analyzerconfig',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig',
        packageName: 'org.wickedsource.coderadar.analyzerconfig',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain',
            packageName: 'org.wickedsource.coderadar.analyzerconfig.domain',
            children: [
              {
                filename: 'AnalyzerConfiguration.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzerConfigurationFile.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFile.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'AnalyzerConfigurationFileRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'AnalyzerConfigurationRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest',
            packageName: 'org.wickedsource.coderadar.analyzerconfig.rest',
            children: [
              {
                filename: 'AnalyzerConfigurationController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest\\AnalyzerConfigurationController.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AnalyzerConfigurationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'AnalyzerConfigurationFileController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest\\AnalyzerConfigurationFileController.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationFileController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerPluginRegistry.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                    packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AnalyzerConfigurationFile.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFile.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfigurationFileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfigurationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ValidationException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\ValidationException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.ValidationException.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzerConfigurationResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest\\AnalyzerConfigurationResource.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'AnalyzerConfigurationResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest\\AnalyzerConfigurationResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzerVerifier.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\rest\\AnalyzerVerifier.java',
                packageName: 'org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerVerifier.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerPluginRegistry.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                    packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ValidationException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\ValidationException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.ValidationException.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'analyzingjob',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob',
        packageName: 'org.wickedsource.coderadar.analyzingjob',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain',
            packageName: 'org.wickedsource.coderadar.analyzingjob.domain',
            children: [
              {
                filename: 'AnalyzingJob.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJob.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzingJobRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJobRepository.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJobRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProjectResetException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\ProjectResetException.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.domain.ProjectResetException.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProjectResetter.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\ProjectResetter.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.domain.ProjectResetter.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AnalyzeCommitJobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJobRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FindingRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\rest',
            packageName: 'org.wickedsource.coderadar.analyzingjob.rest',
            children: [
              {
                filename: 'AnalyzingJobController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\rest\\AnalyzingJobController.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzingJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJob.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AnalyzingJobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJobRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectResetException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\ProjectResetException.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.ProjectResetException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectResetter.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\ProjectResetter.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.ProjectResetter.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'AnalyzeCommitJobRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJobRepository.java',
                        packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJobRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FindingRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValueId.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'File.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                                packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'MetricValueRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'AnalyzingJobResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\rest\\AnalyzingJobResource.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'AnalyzingJobResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\rest\\AnalyzingJobResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzingJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJob.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'commit',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit',
        packageName: 'org.wickedsource.coderadar.commit',
        children: [
          {
            filename: 'configuration',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\configuration',
            packageName: 'org.wickedsource.coderadar.commit.configuration',
            children: [
              {
                filename: 'CommitMappingResourceProvider.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\configuration\\CommitMappingResourceProvider.java',
                packageName: 'org.wickedsource.coderadar.commit.configuration.CommitMappingResourceProvider.java',
                children: [],
                dependencies: [
                  {
                    filename: 'MappingResourceProvider.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\MappingResourceProvider.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.MappingResourceProvider.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain',
            packageName: 'org.wickedsource.coderadar.commit.domain',
            children: [
              {
                filename: 'Commit.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'CommitRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'CommitToFileAssociation.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                children: [],
                dependencies: [
                  {
                    filename: 'File.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Module.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'CommitToFileAssociationRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociationRepository.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'CommitToFileId.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileId.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileId.java',
                children: [],
                dependencies: [
                  {
                    filename: 'File.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'DateCoordinates.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\DateCoordinates.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.DateCoordinates.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ModuleAssociation.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\ModuleAssociation.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.ModuleAssociation.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ModuleAssociationId.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\ModuleAssociationId.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.ModuleAssociationId.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ModuleAssociationRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\ModuleAssociationRepository.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.ModuleAssociationRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'UpdateDateCoordinatesEntityListener.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\UpdateDateCoordinatesEntityListener.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.UpdateDateCoordinatesEntityListener.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Injector.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\Injector.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.Injector.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'event',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\event',
            packageName: 'org.wickedsource.coderadar.commit.event',
            children: [
              {
                filename: 'CommitToFileAssociatedEvent.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\event\\CommitToFileAssociatedEvent.java',
                packageName: 'org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CommitToFileAssociation.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Module.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                        packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\rest',
            packageName: 'org.wickedsource.coderadar.commit.rest',
            children: [
              {
                filename: 'CommitController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\rest\\CommitController.java',
                packageName: 'org.wickedsource.coderadar.commit.rest.CommitController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'CommitResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\rest\\CommitResource.java',
                packageName: 'org.wickedsource.coderadar.commit.rest.CommitResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'CommitResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\rest\\CommitResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.commit.rest.CommitResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'core',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core',
        packageName: 'org.wickedsource.coderadar.core',
        children: [
          {
            filename: 'common',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common',
            packageName: 'org.wickedsource.coderadar.core.common',
            children: [
              {
                filename: 'ResourceNotFoundException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'configuration',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration',
            packageName: 'org.wickedsource.coderadar.core.configuration',
            children: [
              {
                filename: 'CoderadarApplicationContextConfiguration.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarApplicationContextConfiguration.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarApplicationContextConfiguration.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'CoderadarConfiguration.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'Injector.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\Injector.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.Injector.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'MappingResourceProvider.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\MappingResourceProvider.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.MappingResourceProvider.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'RegisterMappingResources.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\RegisterMappingResources.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.RegisterMappingResources.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest',
            packageName: 'org.wickedsource.coderadar.core.rest',
            children: [
              {
                filename: 'dates',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates',
                packageName: 'org.wickedsource.coderadar.core.rest.dates',
                children: [
                  {
                    filename: 'serialize',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize',
                    children: [
                      {
                        filename: 'DayDeserializer.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DayDeserializer.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DayDeserializer.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Day.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'DaySerializer.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DaySerializer.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DaySerializer.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Day.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'WeekDeserializer.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekDeserializer.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekDeserializer.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Week.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'WeekSerializer.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekSerializer.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekSerializer.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Week.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'series',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series',
                    children: [
                      {
                        filename: 'DayPoint.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\DayPoint.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.DayPoint.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Day.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'DayDeserializer.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DayDeserializer.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DayDeserializer.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Day.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'DaySerializer.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DaySerializer.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DaySerializer.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Day.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'MonthPoint.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\MonthPoint.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.MonthPoint.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Month.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Month.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Month.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Point.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Series.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'WeekPoint.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\WeekPoint.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.WeekPoint.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Week.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'WeekDeserializer.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekDeserializer.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekDeserializer.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Week.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'WeekSerializer.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekSerializer.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekSerializer.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Week.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'YearPoint.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\YearPoint.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.YearPoint.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Year.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Year.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Year.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'Day.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Month.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Month.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Month.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Week.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Year.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Year.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Year.java',
                    children: [],
                    dependencies: []
                  }
                ],
                dependencies: []
              },
              {
                filename: 'validation',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation',
                packageName: 'org.wickedsource.coderadar.core.rest.validation',
                children: [
                  {
                    filename: 'AccessTokenNotExpiredException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\AccessTokenNotExpiredException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.AccessTokenNotExpiredException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ControllerExceptionHandler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\ControllerExceptionHandler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.ControllerExceptionHandler.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ErrorDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\ErrorDTO.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.ErrorDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FieldErrorDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\FieldErrorDTO.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.FieldErrorDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RefreshTokenNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\RefreshTokenNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RegistrationException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\RegistrationException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.RegistrationException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ValidationException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\ValidationException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.ValidationException.java',
                    children: [],
                    dependencies: []
                  }
                ],
                dependencies: []
              },
              {
                filename: 'AbstractResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'WorkdirManager.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
            children: [],
            dependencies: [
              {
                filename: 'ResourceNotFoundException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'CoderadarConfiguration.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'Project.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProjectRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                children: [],
                dependencies: []
              }
            ]
          }
        ],
        dependencies: []
      },
      {
        filename: 'file',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file',
        packageName: 'org.wickedsource.coderadar.file',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain',
            packageName: 'org.wickedsource.coderadar.file.domain',
            children: [
              {
                filename: 'File.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileIdentity.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileIdentity.java',
                packageName: 'org.wickedsource.coderadar.file.domain.FileIdentity.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileIdentityRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileIdentityRepository.java',
                packageName: 'org.wickedsource.coderadar.file.domain.FileIdentityRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepository.java',
                packageName: 'org.wickedsource.coderadar.file.domain.FileRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileRepositoryCustom.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepositoryCustom.java',
                packageName: 'org.wickedsource.coderadar.file.domain.FileRepositoryCustom.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileRepositoryImpl.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepositoryImpl.java',
                packageName: 'org.wickedsource.coderadar.file.domain.FileRepositoryImpl.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'GitLogEntry.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntry.java',
                packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntry.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitLogEntryRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'filepattern',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern',
        packageName: 'org.wickedsource.coderadar.filepattern',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain',
            packageName: 'org.wickedsource.coderadar.filepattern.domain',
            children: [
              {
                filename: 'FilePattern.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                children: [],
                dependencies: [
                  {
                    filename: 'InclusionType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'FilePatternRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FileSetType.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'match',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match',
            packageName: 'org.wickedsource.coderadar.filepattern.match',
            children: [
              {
                filename: 'AntPathMatcher.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\AntPathMatcher.java',
                packageName: 'org.wickedsource.coderadar.filepattern.match.AntPathMatcher.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FilePatternMatcher.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\FilePatternMatcher.java',
                packageName: 'org.wickedsource.coderadar.filepattern.match.FilePatternMatcher.java',
                children: [],
                dependencies: [
                  {
                    filename: 'FilePattern.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'InclusionType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'StringUtils.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\StringUtils.java',
                packageName: 'org.wickedsource.coderadar.filepattern.match.StringUtils.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\rest',
            packageName: 'org.wickedsource.coderadar.filepattern.rest',
            children: [
              {
                filename: 'FilePatternController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\rest\\FilePatternController.java',
                packageName: 'org.wickedsource.coderadar.filepattern.rest.FilePatternController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'FilePattern.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'InclusionType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'FilePatternRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'FilePatternDTO.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\rest\\FilePatternDTO.java',
                packageName: 'org.wickedsource.coderadar.filepattern.rest.FilePatternDTO.java',
                children: [],
                dependencies: [
                  {
                    filename: 'FileSetType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'InclusionType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'FilePatternResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\rest\\FilePatternResource.java',
                packageName: 'org.wickedsource.coderadar.filepattern.rest.FilePatternResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'FilePatternResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\rest\\FilePatternResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.filepattern.rest.FilePatternResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FilePattern.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'InclusionType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'job',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job',
        packageName: 'org.wickedsource.coderadar.job',
        children: [
          {
            filename: 'analyze',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze',
            packageName: 'org.wickedsource.coderadar.job.analyze',
            children: [
              {
                filename: 'AnalyzeCommitJob.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJob.java',
                packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJob.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'AnalyzeCommitJobRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJobRepository.java',
                packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJobRepository.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'AnalyzeCommitJobTrigger.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJobTrigger.java',
                packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJobTrigger.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerConfigurationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FilePatternRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'JobLogger.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
                    packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'CommitAnalyzer.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\CommitAnalyzer.java',
                packageName: 'org.wickedsource.coderadar.job.analyze.CommitAnalyzer.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerPluginRegistry.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                    packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AnalyzerConfigurationFile.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFile.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfigurationFileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfigurationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'File.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepository.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.FileRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GitLogEntry.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntry.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntry.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'GitLogEntryRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FilePattern.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'InclusionType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'FilePatternRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FileSetType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FilePatternMatcher.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\FilePatternMatcher.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.match.FilePatternMatcher.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'FilePattern.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'InclusionType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'LocalGitRepositoryManager.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                    packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'WorkdirManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                        packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ResourceNotFoundException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                            packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'CoderadarConfiguration.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                            packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'ProjectRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'UserException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitRepositoryChecker.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'RepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitRepositoryCloner.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'RepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitRepositoryUpdater.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'RepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'FindingRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'MetricValue.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValue.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueId.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GitCommitFinder.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'FileAnalyzer.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\FileAnalyzer.java',
                packageName: 'org.wickedsource.coderadar.job.analyze.FileAnalyzer.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'associate',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate',
            packageName: 'org.wickedsource.coderadar.job.associate',
            children: [
              {
                filename: 'AssociateGitLogJob.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\AssociateGitLogJob.java',
                packageName: 'org.wickedsource.coderadar.job.associate.AssociateGitLogJob.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'AssociateGitLogTrigger.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\AssociateGitLogTrigger.java',
                packageName: 'org.wickedsource.coderadar.job.associate.AssociateGitLogTrigger.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'JobLogger.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
                    packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'CommitToFileAssociator.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\CommitToFileAssociator.java',
                packageName: 'org.wickedsource.coderadar.job.associate.CommitToFileAssociator.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitToFileAssociation.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Module.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                        packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'CommitToFileAssociationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociationRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'CommitToFileAssociatedEvent.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\event\\CommitToFileAssociatedEvent.java',
                    packageName: 'org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitToFileAssociation.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Module.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                            packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'GitLogAssociator.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\GitLogAssociator.java',
                packageName: 'org.wickedsource.coderadar.job.associate.GitLogAssociator.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'MergeLogJobRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\MergeLogJobRepository.java',
                packageName: 'org.wickedsource.coderadar.job.associate.MergeLogJobRepository.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'core',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core',
            packageName: 'org.wickedsource.coderadar.job.core',
            children: [
              {
                filename: 'FirstCommitFinder.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\FirstCommitFinder.java',
                packageName: 'org.wickedsource.coderadar.job.core.FirstCommitFinder.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Commit.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'Job.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'JobRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProcessingStatus.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ResultStatus.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ResultStatus.java',
                packageName: 'org.wickedsource.coderadar.job.core.ResultStatus.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'execute',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\execute',
            packageName: 'org.wickedsource.coderadar.job.execute',
            children: [
              {
                filename: 'JobExecutionService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\execute\\JobExecutionService.java',
                packageName: 'org.wickedsource.coderadar.job.execute.JobExecutionService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'JobLogger.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
                    packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ResultStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ResultStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ResultStatus.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'JobDeletedException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobDeletedException.java',
                    packageName: 'org.wickedsource.coderadar.job.queue.JobDeletedException.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'JobQueueService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobQueueService.java',
                    packageName: 'org.wickedsource.coderadar.job.queue.JobQueueService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'JobRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                        packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'JobUpdater.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobUpdater.java',
                    packageName: 'org.wickedsource.coderadar.job.queue.JobUpdater.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'JobRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                        packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'JobExecutor.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\execute\\JobExecutor.java',
                packageName: 'org.wickedsource.coderadar.job.execute.JobExecutor.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzeCommitJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\AnalyzeCommitJob.java',
                    packageName: 'org.wickedsource.coderadar.job.analyze.AnalyzeCommitJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'CommitAnalyzer.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\CommitAnalyzer.java',
                    packageName: 'org.wickedsource.coderadar.job.analyze.CommitAnalyzer.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'AnalyzerPluginRegistry.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzer\\service\\AnalyzerPluginRegistry.java',
                        packageName: 'org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'AnalyzerConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'AnalyzerConfigurationFile.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFile.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'AnalyzerConfigurationFileRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'AnalyzerConfigurationRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.FileRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitLogEntry.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntry.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntry.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitLogEntryRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePattern.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'InclusionType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'FilePatternRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileSetType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePatternMatcher.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\FilePatternMatcher.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.match.FilePatternMatcher.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'FilePattern.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                            packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'InclusionType.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'LocalGitRepositoryManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                        packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'WorkdirManager.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'UserException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'GitRepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryChecker.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryCloner.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryUpdater.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'FindingRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValueId.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'File.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                                packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'MetricValue.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValue.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'MetricValueRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitCommitFinder.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'AssociateGitLogJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\AssociateGitLogJob.java',
                    packageName: 'org.wickedsource.coderadar.job.associate.AssociateGitLogJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'GitLogAssociator.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\associate\\GitLogAssociator.java',
                    packageName: 'org.wickedsource.coderadar.job.associate.GitLogAssociator.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitMetadataScanner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\CommitMetadataScanner.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.CommitMetadataScanner.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'LocalGitRepositoryManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                        packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'WorkdirManager.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'UserException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'GitRepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryChecker.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryCloner.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryUpdater.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'CommitWalker.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitWalker.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitWalker.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CommitWalkerFilter.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\CommitWalkerFilter.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.CommitWalkerFilter.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'CommitProcessor.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                                packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'RevCommitWithSequenceNumber.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                                packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'DateRangeCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\DateRangeCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.DateRangeCommitFilter.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'LastKnownCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\LastKnownCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.LastKnownCommitFilter.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'GitCommitFinder.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'ScanCommitsJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\ScanCommitsJob.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'FileMetadataScanner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\FileMetadataScanner.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.FileMetadataScanner.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitLogEntry.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntry.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntry.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitLogEntryRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePattern.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'InclusionType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'FilePatternRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileSetType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePatternMatcher.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\FilePatternMatcher.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.match.FilePatternMatcher.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'FilePattern.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                            packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'InclusionType.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'LocalGitRepositoryManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                        packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'WorkdirManager.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'UserException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'GitRepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryChecker.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryCloner.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryUpdater.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'FirstCommitFinder.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\FirstCommitFinder.java',
                        packageName: 'org.wickedsource.coderadar.job.core.FirstCommitFinder.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'CommitRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ProcessingStatus.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'ChangeTypeMapper.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\ChangeTypeMapper.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.ChangeTypeMapper.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitCommitFinder.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ScanFilesJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\ScanFilesJob.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.ScanFilesJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'queue',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue',
            packageName: 'org.wickedsource.coderadar.job.queue',
            children: [
              {
                filename: 'JobDeletedException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobDeletedException.java',
                packageName: 'org.wickedsource.coderadar.job.queue.JobDeletedException.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'JobDequeuer.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobDequeuer.java',
                packageName: 'org.wickedsource.coderadar.job.queue.JobDequeuer.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'JobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'JobQueueService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobQueueService.java',
                packageName: 'org.wickedsource.coderadar.job.queue.JobQueueService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'JobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProcessingStatus.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'JobUpdater.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\queue\\JobUpdater.java',
                packageName: 'org.wickedsource.coderadar.job.queue.JobUpdater.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Job.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                    packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'JobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'scan',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan',
            packageName: 'org.wickedsource.coderadar.job.scan',
            children: [
              {
                filename: 'commit',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit',
                packageName: 'org.wickedsource.coderadar.job.scan.commit',
                children: [
                  {
                    filename: 'CommitMetadataScanner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\CommitMetadataScanner.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.CommitMetadataScanner.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'LocalGitRepositoryManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                        packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'WorkdirManager.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'UserException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'GitRepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryChecker.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryCloner.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryUpdater.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'CommitWalker.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitWalker.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitWalker.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CommitWalkerFilter.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\CommitWalkerFilter.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.CommitWalkerFilter.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'CommitProcessor.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                                packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'RevCommitWithSequenceNumber.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                                packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'DateRangeCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\DateRangeCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.DateRangeCommitFilter.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'LastKnownCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\LastKnownCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.LastKnownCommitFilter.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'GitCommitFinder.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'CommitMetadataScannerTrigger.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\CommitMetadataScannerTrigger.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.CommitMetadataScannerTrigger.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CoderadarConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'JobLogger.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
                        packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Job.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                            packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'PersistingCommitProcessor.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\PersistingCommitProcessor.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.PersistingCommitProcessor.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'CommitProcessor.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'RevCommitWithSequenceNumber.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ScanCommitsJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\ScanCommitsJob.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.ScanCommitsJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'ScanCommitsJobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\commit\\ScanCommitsJobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.commit.ScanCommitsJobRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ],
                dependencies: []
              },
              {
                filename: 'file',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file',
                packageName: 'org.wickedsource.coderadar.job.scan.file',
                children: [
                  {
                    filename: 'FileMetadataScanner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\FileMetadataScanner.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.FileMetadataScanner.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitLogEntry.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntry.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntry.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'GitLogEntryRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePattern.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'InclusionType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'FilePatternRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileSetType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FileSetType.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FileSetType.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePatternMatcher.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\match\\FilePatternMatcher.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.match.FilePatternMatcher.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'FilePattern.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePattern.java',
                            packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePattern.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'InclusionType.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'LocalGitRepositoryManager.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
                        packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'WorkdirManager.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                            packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'UserException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'GitRepositoryChecker.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryChecker.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryCloner.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryCloner.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'GitRepositoryUpdater.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'RepositoryUpdater.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                                packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'FirstCommitFinder.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\FirstCommitFinder.java',
                        packageName: 'org.wickedsource.coderadar.job.core.FirstCommitFinder.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'CommitRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ProcessingStatus.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'ChangeTypeMapper.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\ChangeTypeMapper.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.ChangeTypeMapper.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitCommitFinder.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'FileMetadataScannerTrigger.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\FileMetadataScannerTrigger.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.FileMetadataScannerTrigger.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CoderadarConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePatternRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'JobLogger.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
                        packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Job.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                            packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Hasher.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\Hasher.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.Hasher.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ScanFilesJob.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\ScanFilesJob.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.ScanFilesJob.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'Job.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                        packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'ScanFilesJobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\scan\\file\\ScanFilesJobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.scan.file.ScanFilesJobRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'JobGauges.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobGauges.java',
            packageName: 'org.wickedsource.coderadar.job.JobGauges.java',
            children: [],
            dependencies: [
              {
                filename: 'JobRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProcessingStatus.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                children: [],
                dependencies: []
              }
            ]
          },
          {
            filename: 'JobLogger.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\JobLogger.java',
            packageName: 'org.wickedsource.coderadar.job.JobLogger.java',
            children: [],
            dependencies: [
              {
                filename: 'Commit.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'Job.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\Job.java',
                packageName: 'org.wickedsource.coderadar.job.core.Job.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'Project.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                children: [],
                dependencies: []
              }
            ]
          },
          {
            filename: 'LocalGitRepositoryManager.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\LocalGitRepositoryManager.java',
            packageName: 'org.wickedsource.coderadar.job.LocalGitRepositoryManager.java',
            children: [],
            dependencies: [
              {
                filename: 'WorkdirManager.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\WorkdirManager.java',
                packageName: 'org.wickedsource.coderadar.core.WorkdirManager.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'UserException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'Project.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'GitRepositoryChecker.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryChecker.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitRepositoryCloner.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryCloner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitRepositoryUpdater.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryUpdater.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ]
          }
        ],
        dependencies: []
      },
      {
        filename: 'metric',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric',
        packageName: 'org.wickedsource.coderadar.metric',
        children: [
          {
            filename: 'configuration',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\configuration',
            packageName: 'org.wickedsource.coderadar.metric.configuration',
            children: [
              {
                filename: 'MetricValueMappingResourceProvider.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\configuration\\MetricValueMappingResourceProvider.java',
                packageName: 'org.wickedsource.coderadar.metric.configuration.MetricValueMappingResourceProvider.java',
                children: [],
                dependencies: [
                  {
                    filename: 'MappingResourceProvider.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\MappingResourceProvider.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.MappingResourceProvider.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain',
            packageName: 'org.wickedsource.coderadar.metric.domain',
            children: [
              {
                filename: 'finding',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding',
                packageName: 'org.wickedsource.coderadar.metric.domain.finding',
                children: [
                  {
                    filename: 'Finding.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\Finding.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.finding.Finding.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'FindingRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  }
                ],
                dependencies: []
              },
              {
                filename: 'metricvalue',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue',
                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue',
                children: [
                  {
                    filename: 'ChangedFileDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\ChangedFileDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.ChangedFileDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GroupedByFileMetricValueDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedByFileMetricValueDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByFileMetricValueDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GroupedByModuleMetricValueDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedByModuleMetricValueDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByModuleMetricValueDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GroupedMetricValueDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedMetricValueDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricValue.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValue.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricValueId.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProfileValuePerCommitDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\ProfileValuePerCommitDTO.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricType.java',
                        packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricType.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\rest',
            packageName: 'org.wickedsource.coderadar.metric.rest',
            children: [
              {
                filename: 'MetricResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\rest\\MetricResource.java',
                packageName: 'org.wickedsource.coderadar.metric.rest.MetricResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'MetricResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\rest\\MetricResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.metric.rest.MetricResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'MetricsController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\rest\\MetricsController.java',
                packageName: 'org.wickedsource.coderadar.metric.rest.MetricsController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'MetricValueRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'MetricRegistryBean.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\MetricRegistryBean.java',
            packageName: 'org.wickedsource.coderadar.metric.MetricRegistryBean.java',
            children: [],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'metricquery',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery',
        packageName: 'org.wickedsource.coderadar.metricquery',
        children: [
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest',
            packageName: 'org.wickedsource.coderadar.metricquery.rest',
            children: [
              {
                filename: 'commit',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit',
                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit',
                children: [
                  {
                    filename: 'metric',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric',
                    children: [
                      {
                        filename: 'series',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series',
                        children: [
                          {
                            filename: 'DaySeriesFactory.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series\\DaySeriesFactory.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series.DaySeriesFactory.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'CommitRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'ProcessingStatus.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'Day.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DayPoint.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\DayPoint.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.DayPoint.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Day.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'DayDeserializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DayDeserializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DayDeserializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Day.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  },
                                  {
                                    filename: 'DaySerializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DaySerializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DaySerializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Day.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  }
                                ]
                              },
                              {
                                filename: 'Point.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'MetricValueRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DateSeries.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Point.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'Series.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          },
                          {
                            filename: 'MetricValueSeriesFactory.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series\\MetricValueSeriesFactory.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series.MetricValueSeriesFactory.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'Point.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'MetricValueDTO.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueDTO.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'MetricValueRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DateSeries.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Point.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'Series.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          },
                          {
                            filename: 'WeekSeriesFactory.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series\\WeekSeriesFactory.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series.WeekSeriesFactory.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'CommitRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'ProcessingStatus.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'DateCoordinates.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\DateCoordinates.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.DateCoordinates.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Week.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Point.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'WeekPoint.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\WeekPoint.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.WeekPoint.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Week.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'WeekDeserializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekDeserializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekDeserializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Week.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  },
                                  {
                                    filename: 'WeekSerializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekSerializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekSerializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Week.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  }
                                ]
                              },
                              {
                                filename: 'MetricValueRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DateSeries.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Point.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'Series.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          }
                        ],
                        dependencies: []
                      },
                      {
                        filename: 'CommitMetricsHistoryQuery.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\CommitMetricsHistoryQuery.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.CommitMetricsHistoryQuery.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'DateRange.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateRange.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateRange.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Interval.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\Interval.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.Interval.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitMetricsQuery.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\CommitMetricsQuery.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.CommitMetricsQuery.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'CommitMetricsResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\CommitMetricsResource.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.CommitMetricsResource.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValueDTO.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueDTO.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitMetricValuesController.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\CommitMetricValuesController.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.CommitMetricValuesController.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'CommitRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ProcessingStatus.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'ResourceNotFoundException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                            packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Day.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Series.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'MetricValueDTO.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueDTO.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'MetricValueRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'DaySeriesFactory.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series\\DaySeriesFactory.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series.DaySeriesFactory.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'CommitRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'ProcessingStatus.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'Day.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DayPoint.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\DayPoint.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.DayPoint.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Day.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'DayDeserializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DayDeserializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DayDeserializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Day.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  },
                                  {
                                    filename: 'DaySerializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\DaySerializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.DaySerializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Day.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Day.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Day.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  }
                                ]
                              },
                              {
                                filename: 'Point.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'MetricValueRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DateSeries.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Point.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'Series.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          },
                          {
                            filename: 'WeekSeriesFactory.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\series\\WeekSeriesFactory.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.series.WeekSeriesFactory.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'CommitRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'ProcessingStatus.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                    packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'DateCoordinates.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\DateCoordinates.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.DateCoordinates.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoderadarConfiguration.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Week.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Point.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'WeekPoint.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\WeekPoint.java',
                                packageName: 'org.wickedsource.coderadar.core.rest.dates.series.WeekPoint.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Week.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'WeekDeserializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekDeserializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekDeserializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Week.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  },
                                  {
                                    filename: 'WeekSerializer.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\serialize\\WeekSerializer.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.serialize.WeekSerializer.java',
                                    children: [],
                                    dependencies: [
                                      {
                                        filename: 'Week.java',
                                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\Week.java',
                                        packageName: 'org.wickedsource.coderadar.core.rest.dates.Week.java',
                                        children: [],
                                        dependencies: []
                                      }
                                    ]
                                  }
                                ]
                              },
                              {
                                filename: 'MetricValueRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                                packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DateSeries.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                                packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Point.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                                    children: [],
                                    dependencies: []
                                  },
                                  {
                                    filename: 'Series.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                                    packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          },
                          {
                            filename: 'ProjectVerifier.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                            packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'DatePointConverter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\DatePointConverter.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.DatePointConverter.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'MetricValueHistoryResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\metric\\MetricValueHistoryResource.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.metric.MetricValueHistoryResource.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Point.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'profilerating',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\profilerating',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.profilerating',
                    children: [
                      {
                        filename: 'CommitProfileRatingsController.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\profilerating\\CommitProfileRatingsController.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.profilerating.CommitProfileRatingsController.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'CommitRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ProcessingStatus.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                                packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'ResourceNotFoundException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                            packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'MetricValueRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'ProfileValuePerCommitDTO.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\ProfileValuePerCommitDTO.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'MetricType.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricType.java',
                                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricType.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'ProjectVerifier.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                            packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ResourceNotFoundException.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                                packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'ProjectRepository.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'CommitProfileRatingsOutputResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\profilerating\\CommitProfileRatingsOutputResource.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.profilerating.CommitProfileRatingsOutputResource.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProfileValuePerCommitDTO.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\ProfileValuePerCommitDTO.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'MetricType.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricType.java',
                                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricType.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'CommitProfileRatingsQuery.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\profilerating\\CommitProfileRatingsQuery.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.profilerating.CommitProfileRatingsQuery.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProfileRatingDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\profilerating\\ProfileRatingDTO.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.profilerating.ProfileRatingDTO.java',
                        children: [],
                        dependencies: []
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'DateRange.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateRange.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateRange.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'DateSeries.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\DateSeries.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.DateSeries.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Point.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Point.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Point.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Series.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\dates\\series\\Series.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.dates.series.Series.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Interval.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\commit\\Interval.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.commit.Interval.java',
                    children: [],
                    dependencies: []
                  }
                ],
                dependencies: []
              },
              {
                filename: 'tree',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree',
                packageName: 'org.wickedsource.coderadar.metricquery.rest.tree',
                children: [
                  {
                    filename: 'delta',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta',
                    children: [
                      {
                        filename: 'ChangedFilesMap.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\ChangedFilesMap.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.ChangedFilesMap.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Changes.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\Changes.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.Changes.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'DeltaMetricValueSet.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\DeltaMetricValueSet.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaMetricValueSet.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValuesSet.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricValuesSet.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'DeltaTreePayload.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\DeltaTreePayload.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreePayload.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValuesSet.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricValuesSet.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'MetricsTreePayload.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreePayload.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreePayload.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'DeltaTreeQuery.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\DeltaTreeQuery.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreeQuery.java',
                        children: [],
                        dependencies: []
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'CommitMetricsPayload.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\CommitMetricsPayload.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.CommitMetricsPayload.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricsTreeController.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreeController.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreeController.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Commit.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ChangedFileDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\ChangedFileDTO.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.ChangedFileDTO.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GroupedMetricValueDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedMetricValueDTO.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'MetricValueRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ChangedFilesMap.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\ChangedFilesMap.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.ChangedFilesMap.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Changes.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\Changes.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.Changes.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'DeltaTreePayload.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\DeltaTreePayload.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreePayload.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValuesSet.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricValuesSet.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'MetricsTreePayload.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreePayload.java',
                            packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreePayload.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'DeltaTreeQuery.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\delta\\DeltaTreeQuery.java',
                        packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreeQuery.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectVerifier.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                        packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ResourceNotFoundException.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                            packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'ProjectRepository.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'MetricsTreeNodeType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreeNodeType.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreeNodeType.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricsTreePayload.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreePayload.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreePayload.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricsTreeQuery.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreeQuery.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreeQuery.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricsTreeResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreeResource.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreeResource.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricsTreeResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricsTreeResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricsTreeResourceAssembler.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'AbstractResourceAssembler.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GroupedByFileMetricValueDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedByFileMetricValueDTO.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByFileMetricValueDTO.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GroupedByModuleMetricValueDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedByModuleMetricValueDTO.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByModuleMetricValueDTO.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GroupedMetricValueDTO.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\GroupedMetricValueDTO.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'MetricValuesSet.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\MetricValuesSet.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.MetricValuesSet.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'NodeTypeSupplier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\NodeTypeSupplier.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.NodeTypeSupplier.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'PayloadSupplier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metricquery\\rest\\tree\\PayloadSupplier.java',
                    packageName: 'org.wickedsource.coderadar.metricquery.rest.tree.PayloadSupplier.java',
                    children: [],
                    dependencies: []
                  }
                ],
                dependencies: []
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'module',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module',
        packageName: 'org.wickedsource.coderadar.module',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain',
            packageName: 'org.wickedsource.coderadar.module.domain',
            children: [
              {
                filename: 'Module.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'ModuleRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\ModuleRepository.java',
                packageName: 'org.wickedsource.coderadar.module.domain.ModuleRepository.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CommitToFileAssociation.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Module.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                        packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\rest',
            packageName: 'org.wickedsource.coderadar.module.rest',
            children: [
              {
                filename: 'ModuleAssociationService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\rest\\ModuleAssociationService.java',
                packageName: 'org.wickedsource.coderadar.module.rest.ModuleAssociationService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CommitToFileAssociation.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'File.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Module.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                        packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Project.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                            packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'CommitToFileAssociatedEvent.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\event\\CommitToFileAssociatedEvent.java',
                    packageName: 'org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitToFileAssociation.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Module.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                            packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'Module.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ModuleRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\ModuleRepository.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.ModuleRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitToFileAssociation.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Module.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                            packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'ModuleController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\rest\\ModuleController.java',
                packageName: 'org.wickedsource.coderadar.module.rest.ModuleController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Module.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'ModuleRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\ModuleRepository.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.ModuleRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitToFileAssociation.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Module.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                            packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'ModuleResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\rest\\ModuleResource.java',
                packageName: 'org.wickedsource.coderadar.module.rest.ModuleResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ModuleResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\rest\\ModuleResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.module.rest.ModuleResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Module.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'project',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project',
        packageName: 'org.wickedsource.coderadar.project',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain',
            packageName: 'org.wickedsource.coderadar.project.domain',
            children: [
              {
                filename: 'InclusionType.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\InclusionType.java',
                packageName: 'org.wickedsource.coderadar.project.domain.InclusionType.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'Project.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProjectDeleter.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectDeleter.java',
                packageName: 'org.wickedsource.coderadar.project.domain.ProjectDeleter.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AnalyzerConfigurationFileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzerConfigurationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzingJobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJobRepository.java',
                    packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'CommitRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ProcessingStatus.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                        packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitToFileAssociationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociationRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ModuleAssociationRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\ModuleAssociationRepository.java',
                    packageName: 'org.wickedsource.coderadar.commit.domain.ModuleAssociationRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FileIdentityRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileIdentityRepository.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.FileIdentityRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepository.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.FileRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'GitLogEntryRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                    packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FilePatternRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                    packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'JobRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                    packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'FindingRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'MetricValueId.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'Commit.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          },
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'MetricValueRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                    packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ModuleRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\ModuleRepository.java',
                    packageName: 'org.wickedsource.coderadar.module.domain.ModuleRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitToFileAssociation.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'File.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                            packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'Module.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                            packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Project.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'ProjectRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'VcsCoordinates.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\VcsCoordinates.java',
                packageName: 'org.wickedsource.coderadar.project.domain.VcsCoordinates.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest',
            packageName: 'org.wickedsource.coderadar.project.rest',
            children: [
              {
                filename: 'ProjectController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectController.java',
                packageName: 'org.wickedsource.coderadar.project.rest.ProjectController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'UserException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectDeleter.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectDeleter.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectDeleter.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'AnalyzerConfigurationFileRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationFileRepository.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'AnalyzerConfigurationRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzerconfig\\domain\\AnalyzerConfigurationRepository.java',
                        packageName: 'org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'AnalyzingJobRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\analyzingjob\\domain\\AnalyzingJobRepository.java',
                        packageName: 'org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJobRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'CommitRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ProcessingStatus.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\ProcessingStatus.java',
                            packageName: 'org.wickedsource.coderadar.job.core.ProcessingStatus.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'CommitToFileAssociationRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociationRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ModuleAssociationRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\ModuleAssociationRepository.java',
                        packageName: 'org.wickedsource.coderadar.commit.domain.ModuleAssociationRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileIdentityRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileIdentityRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.FileIdentityRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FileRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\FileRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.FileRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'GitLogEntryRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\GitLogEntryRepository.java',
                        packageName: 'org.wickedsource.coderadar.file.domain.GitLogEntryRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FilePatternRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\filepattern\\domain\\FilePatternRepository.java',
                        packageName: 'org.wickedsource.coderadar.filepattern.domain.FilePatternRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'JobRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\core\\JobRepository.java',
                        packageName: 'org.wickedsource.coderadar.job.core.JobRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'FindingRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\finding\\FindingRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.finding.FindingRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'MetricValueId.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueId.java',
                            packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'Commit.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\Commit.java',
                                packageName: 'org.wickedsource.coderadar.commit.domain.Commit.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              },
                              {
                                filename: 'File.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                                packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      },
                      {
                        filename: 'MetricValueRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\metric\\domain\\metricvalue\\MetricValueRepository.java',
                        packageName: 'org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ModuleRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\ModuleRepository.java',
                        packageName: 'org.wickedsource.coderadar.module.domain.ModuleRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CommitToFileAssociation.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\commit\\domain\\CommitToFileAssociation.java',
                            packageName: 'org.wickedsource.coderadar.commit.domain.CommitToFileAssociation.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'File.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\file\\domain\\File.java',
                                packageName: 'org.wickedsource.coderadar.file.domain.File.java',
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'Module.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\module\\domain\\Module.java',
                                packageName: 'org.wickedsource.coderadar.module.domain.Module.java',
                                children: [],
                                dependencies: [
                                  {
                                    filename: 'Project.java',
                                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                                    children: [],
                                    dependencies: []
                                  }
                                ]
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'ProjectRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'ProjectResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectResource.java',
                packageName: 'org.wickedsource.coderadar.project.rest.ProjectResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ProjectResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.project.rest.ProjectResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'VcsCoordinates.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\VcsCoordinates.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.VcsCoordinates.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'ProjectVerifier.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'qualityprofile',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile',
        packageName: 'org.wickedsource.coderadar.qualityprofile',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain',
            packageName: 'org.wickedsource.coderadar.qualityprofile.domain',
            children: [
              {
                filename: 'MetricDTO.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricDTO.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricDTO.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'MetricType.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricType.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricType.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'QualityProfile.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfile.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfile.java',
                children: [],
                dependencies: [
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'QualityProfileMetric.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfileMetric.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetric.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'QualityProfileMetricRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfileMetricRepository.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetricRepository.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'QualityProfileRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfileRepository.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfileRepository.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\rest',
            packageName: 'org.wickedsource.coderadar.qualityprofile.rest',
            children: [
              {
                filename: 'QualityProfileController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\rest\\QualityProfileController.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.rest.QualityProfileController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'ProjectVerifier.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\rest\\ProjectVerifier.java',
                    packageName: 'org.wickedsource.coderadar.project.rest.ProjectVerifier.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ProjectRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\ProjectRepository.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.ProjectRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'QualityProfile.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfile.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfile.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'QualityProfileRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfileRepository.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfileRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'QualityProfileResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\rest\\QualityProfileResource.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.rest.QualityProfileResource.java',
                children: [],
                dependencies: [
                  {
                    filename: 'MetricDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricDTO.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricDTO.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'QualityProfileResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\rest\\QualityProfileResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.qualityprofile.rest.QualityProfileResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'Project.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'MetricDTO.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\MetricDTO.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.MetricDTO.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'QualityProfile.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfile.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfile.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'Project.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\Project.java',
                        packageName: 'org.wickedsource.coderadar.project.domain.Project.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'QualityProfileMetric.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\qualityprofile\\domain\\QualityProfileMetric.java',
                    packageName: 'org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetric.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'security',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security',
        packageName: 'org.wickedsource.coderadar.security',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain',
            packageName: 'org.wickedsource.coderadar.security.domain',
            children: [
              {
                filename: 'AccessTokenResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\AccessTokenResource.java',
                packageName: 'org.wickedsource.coderadar.security.domain.AccessTokenResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'ChangePasswordResponseResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\ChangePasswordResponseResource.java',
                packageName: 'org.wickedsource.coderadar.security.domain.ChangePasswordResponseResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'CoderadarUserDetails.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\CoderadarUserDetails.java',
                packageName: 'org.wickedsource.coderadar.security.domain.CoderadarUserDetails.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'InitializeTokenResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\InitializeTokenResource.java',
                packageName: 'org.wickedsource.coderadar.security.domain.InitializeTokenResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'PasswordChangeResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\PasswordChangeResource.java',
                packageName: 'org.wickedsource.coderadar.security.domain.PasswordChangeResource.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ValidPassword.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                    packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'RefreshToken.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshToken.java',
                packageName: 'org.wickedsource.coderadar.security.domain.RefreshToken.java',
                children: [],
                dependencies: [
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'RefreshTokenRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                children: [],
                dependencies: [
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'RefreshTokenResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenResource.java',
                packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenResource.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'service',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service',
            packageName: 'org.wickedsource.coderadar.security.service',
            children: [
              {
                filename: 'CoderadarUserDetailService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\CoderadarUserDetailService.java',
                packageName: 'org.wickedsource.coderadar.security.service.CoderadarUserDetailService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarUserDetails.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\CoderadarUserDetails.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.CoderadarUserDetails.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'PasswordService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'SecretKeyService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\SecretKeyService.java',
                packageName: 'org.wickedsource.coderadar.security.service.SecretKeyService.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'TokenService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'TokenType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                    packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'AuthenticationTokenFilter.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\AuthenticationTokenFilter.java',
            packageName: 'org.wickedsource.coderadar.security.AuthenticationTokenFilter.java',
            children: [],
            dependencies: [
              {
                filename: 'TokenService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'TokenType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                    packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ]
          },
          {
            filename: 'CoderadarCorsConfiguration.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\CoderadarCorsConfiguration.java',
            packageName: 'org.wickedsource.coderadar.security.CoderadarCorsConfiguration.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'CoderadarPasswordValidator.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\CoderadarPasswordValidator.java',
            packageName: 'org.wickedsource.coderadar.security.CoderadarPasswordValidator.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'CoderadarSecurityConfiguration.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\CoderadarSecurityConfiguration.java',
            packageName: 'org.wickedsource.coderadar.security.CoderadarSecurityConfiguration.java',
            children: [],
            dependencies: [
              {
                filename: 'CoderadarConfiguration.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'TokenService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'CoderadarConfiguration.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                    packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'TokenType.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                    packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ]
          },
          {
            filename: 'TokenExpiresException.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenExpiresException.java',
            packageName: 'org.wickedsource.coderadar.security.TokenExpiresException.java',
            children: [],
            dependencies: [
              {
                filename: 'UserException.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\UserException.java',
                packageName: 'org.wickedsource.coderadar.core.rest.validation.UserException.java',
                children: [],
                dependencies: []
              }
            ]
          },
          {
            filename: 'TokenType.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
            packageName: 'org.wickedsource.coderadar.security.TokenType.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'ValidPassword.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
            packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
            children: [],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'user',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user',
        packageName: 'org.wickedsource.coderadar.user',
        children: [
          {
            filename: 'domain',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain',
            packageName: 'org.wickedsource.coderadar.user.domain',
            children: [
              {
                filename: 'User.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'UserLoginResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserLoginResource.java',
                packageName: 'org.wickedsource.coderadar.user.domain.UserLoginResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'UserRegistrationDataResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ValidPassword.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                    packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'UserRepository.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                children: [],
                dependencies: []
              }
            ],
            dependencies: []
          },
          {
            filename: 'rest',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest',
            packageName: 'org.wickedsource.coderadar.user.rest',
            children: [
              {
                filename: 'UserController.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserController.java',
                packageName: 'org.wickedsource.coderadar.user.rest.UserController.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AccessTokenNotExpiredException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\AccessTokenNotExpiredException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.AccessTokenNotExpiredException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RegistrationException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\RegistrationException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.RegistrationException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'TokenService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                    packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CoderadarConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'TokenType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                        packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserLoginResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserLoginResource.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserLoginResource.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRegistrationDataResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ValidPassword.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                        packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'LoginService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\LoginService.java',
                    packageName: 'org.wickedsource.coderadar.user.service.LoginService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'InitializeTokenResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\InitializeTokenResource.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.InitializeTokenResource.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'RefreshToken.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshToken.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.RefreshToken.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'RefreshTokenRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'TokenService.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                        packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CoderadarConfiguration.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                            packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'TokenType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                            packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'UserRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'PasswordChangeService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\PasswordChangeService.java',
                    packageName: 'org.wickedsource.coderadar.user.service.PasswordChangeService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ChangePasswordResponseResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\ChangePasswordResponseResource.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.ChangePasswordResponseResource.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'RefreshTokenRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'PasswordService.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                        packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'RegistrationService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\RegistrationService.java',
                    packageName: 'org.wickedsource.coderadar.user.service.RegistrationService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ResourceNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'UserRegistrationDataResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ValidPassword.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                            packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'UserRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'UserCredentialsResourceAssembler.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserCredentialsResourceAssembler.java',
                        packageName: 'org.wickedsource.coderadar.user.rest.UserCredentialsResourceAssembler.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'AbstractResourceAssembler.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                            packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'PasswordService.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                            packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'UserRegistrationDataResource.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                            children: [],
                            dependencies: [
                              {
                                filename: 'ValidPassword.java',
                                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                                packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                                children: [],
                                dependencies: []
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'TokenRefreshService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\TokenRefreshService.java',
                    packageName: 'org.wickedsource.coderadar.user.service.TokenRefreshService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'RefreshTokenNotFoundException.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\RefreshTokenNotFoundException.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'RefreshToken.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshToken.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.RefreshToken.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'RefreshTokenRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                        packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'User.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                            packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'TokenService.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                        packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CoderadarConfiguration.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                            packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'TokenType.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                            packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'UserRepository.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'UserCredentialsResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserCredentialsResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.user.rest.UserCredentialsResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'PasswordService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                    packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRegistrationDataResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ValidPassword.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                        packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'UserResource.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserResource.java',
                packageName: 'org.wickedsource.coderadar.user.rest.UserResource.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'UserResourceAssembler.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserResourceAssembler.java',
                packageName: 'org.wickedsource.coderadar.user.rest.UserResourceAssembler.java',
                children: [],
                dependencies: [
                  {
                    filename: 'AbstractResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'service',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service',
            packageName: 'org.wickedsource.coderadar.user.service',
            children: [
              {
                filename: 'LoginService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\LoginService.java',
                packageName: 'org.wickedsource.coderadar.user.service.LoginService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'InitializeTokenResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\InitializeTokenResource.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.InitializeTokenResource.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RefreshToken.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshToken.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.RefreshToken.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'RefreshTokenRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'TokenService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                    packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CoderadarConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'TokenType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                        packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'PasswordChangeService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\PasswordChangeService.java',
                packageName: 'org.wickedsource.coderadar.user.service.PasswordChangeService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ChangePasswordResponseResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\ChangePasswordResponseResource.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.ChangePasswordResponseResource.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RefreshTokenRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'PasswordService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                    packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'RegistrationService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\RegistrationService.java',
                packageName: 'org.wickedsource.coderadar.user.service.RegistrationService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'ResourceNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\common\\ResourceNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.common.ResourceNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRegistrationDataResource.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'ValidPassword.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                        packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'UserRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserCredentialsResourceAssembler.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\rest\\UserCredentialsResourceAssembler.java',
                    packageName: 'org.wickedsource.coderadar.user.rest.UserCredentialsResourceAssembler.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'AbstractResourceAssembler.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\AbstractResourceAssembler.java',
                        packageName: 'org.wickedsource.coderadar.core.rest.AbstractResourceAssembler.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'PasswordService.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\PasswordService.java',
                        packageName: 'org.wickedsource.coderadar.security.service.PasswordService.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'UserRegistrationDataResource.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRegistrationDataResource.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.UserRegistrationDataResource.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'ValidPassword.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\ValidPassword.java',
                            packageName: 'org.wickedsource.coderadar.security.ValidPassword.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  }
                ]
              },
              {
                filename: 'TokenRefreshService.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\service\\TokenRefreshService.java',
                packageName: 'org.wickedsource.coderadar.user.service.TokenRefreshService.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RefreshTokenNotFoundException.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\rest\\validation\\RefreshTokenNotFoundException.java',
                    packageName: 'org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RefreshToken.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshToken.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.RefreshToken.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'RefreshTokenRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\domain\\RefreshTokenRepository.java',
                    packageName: 'org.wickedsource.coderadar.security.domain.RefreshTokenRepository.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'User.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                        packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'TokenService.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\service\\TokenService.java',
                    packageName: 'org.wickedsource.coderadar.security.service.TokenService.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CoderadarConfiguration.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\core\\configuration\\CoderadarConfiguration.java',
                        packageName: 'org.wickedsource.coderadar.core.configuration.CoderadarConfiguration.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'TokenType.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\security\\TokenType.java',
                        packageName: 'org.wickedsource.coderadar.security.TokenType.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'User.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\User.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.User.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'UserRepository.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\user\\domain\\UserRepository.java',
                    packageName: 'org.wickedsource.coderadar.user.domain.UserRepository.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'vcs',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs',
        packageName: 'org.wickedsource.coderadar.vcs',
        children: [
          {
            filename: 'git',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git',
            packageName: 'org.wickedsource.coderadar.vcs.git',
            children: [
              {
                filename: 'walk',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk',
                packageName: 'org.wickedsource.coderadar.vcs.git.walk',
                children: [
                  {
                    filename: 'filter',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter',
                    children: [
                      {
                        filename: 'CommitWalkerFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\CommitWalkerFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.CommitWalkerFilter.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CommitProcessor.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'RevCommitWithSequenceNumber.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      },
                      {
                        filename: 'DateRangeCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\DateRangeCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.DateRangeCommitFilter.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'LastKnownCommitFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\LastKnownCommitFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.LastKnownCommitFilter.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'GitCommitFinder.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ],
                    dependencies: []
                  },
                  {
                    filename: 'AnalyzingCommitProcessor.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\AnalyzingCommitProcessor.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.AnalyzingCommitProcessor.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'FileAnalyzer.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\job\\analyze\\FileAnalyzer.java',
                        packageName: 'org.wickedsource.coderadar.job.analyze.FileAnalyzer.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'MetricsProcessor.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\MetricsProcessor.java',
                        packageName: 'org.wickedsource.coderadar.vcs.MetricsProcessor.java',
                        children: [],
                        dependencies: []
                      },
                      {
                        filename: 'ChangeTypeMapper.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\ChangeTypeMapper.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.ChangeTypeMapper.java',
                        children: [],
                        dependencies: []
                      }
                    ]
                  },
                  {
                    filename: 'CommitProcessor.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'CommitWalker.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitWalker.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitWalker.java',
                    children: [],
                    dependencies: [
                      {
                        filename: 'CommitWalkerFilter.java',
                        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\filter\\CommitWalkerFilter.java',
                        packageName: 'org.wickedsource.coderadar.vcs.git.walk.filter.CommitWalkerFilter.java',
                        children: [],
                        dependencies: [
                          {
                            filename: 'CommitProcessor.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\CommitProcessor.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.CommitProcessor.java',
                            children: [],
                            dependencies: []
                          },
                          {
                            filename: 'RevCommitWithSequenceNumber.java',
                            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                            packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                            children: [],
                            dependencies: []
                          }
                        ]
                      }
                    ]
                  },
                  {
                    filename: 'Counter.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\Counter.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.Counter.java',
                    children: [],
                    dependencies: []
                  },
                  {
                    filename: 'RevCommitWithSequenceNumber.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\walk\\RevCommitWithSequenceNumber.java',
                    packageName: 'org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber.java',
                    children: [],
                    dependencies: []
                  }
                ],
                dependencies: []
              },
              {
                filename: 'ChangeTypeMapper.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\ChangeTypeMapper.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.ChangeTypeMapper.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'GitCommitFetcher.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFetcher.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFetcher.java',
                children: [],
                dependencies: [
                  {
                    filename: 'VcsCoordinates.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\project\\domain\\VcsCoordinates.java',
                    packageName: 'org.wickedsource.coderadar.project.domain.VcsCoordinates.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitCommitFinder.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitCommitFinder.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitCommitFinder.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'GitRepositoryChecker.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryChecker.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryChecker.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryChecker.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitRepositoryCloner.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryCloner.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryCloner.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryCloner.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
                    children: [],
                    dependencies: []
                  }
                ]
              },
              {
                filename: 'GitRepositoryResetter.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryResetter.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryResetter.java',
                children: [],
                dependencies: []
              },
              {
                filename: 'GitRepositoryUpdater.java',
                path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\git\\GitRepositoryUpdater.java',
                packageName: 'org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater.java',
                children: [],
                dependencies: [
                  {
                    filename: 'RepositoryUpdater.java',
                    path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
                    packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
                    children: [],
                    dependencies: []
                  }
                ]
              }
            ],
            dependencies: []
          },
          {
            filename: 'MetricsProcessor.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\MetricsProcessor.java',
            packageName: 'org.wickedsource.coderadar.vcs.MetricsProcessor.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'RepositoryChecker.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryChecker.java',
            packageName: 'org.wickedsource.coderadar.vcs.RepositoryChecker.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'RepositoryCloner.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryCloner.java',
            packageName: 'org.wickedsource.coderadar.vcs.RepositoryCloner.java',
            children: [],
            dependencies: []
          },
          {
            filename: 'RepositoryUpdater.java',
            path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\vcs\\RepositoryUpdater.java',
            packageName: 'org.wickedsource.coderadar.vcs.RepositoryUpdater.java',
            children: [],
            dependencies: []
          }
        ],
        dependencies: []
      },
      {
        filename: 'CoderadarApplication.java',
        path: 'C:\\Users\\teklote\\Documents\\git\\coderadar\\coderadar-server\\coderadar-core\\src\\main\\java\\org\\wickedsource\\coderadar\\CoderadarApplication.java',
        packageName: 'org.wickedsource.coderadar.CoderadarApplication.java',
        children: [],
        dependencies: []
      }
    ],
    dependencies: []
  };
  let component;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        StructureMapComponent,
        DependencyTreeComponent,
        DependencyRootComponent
      ],
      imports: [
        BrowserModule,
        HttpClientTestingModule
      ],
      providers: [
        DependencyTreeProvider,
        {provide: APP_INITIALIZER, useFactory: dependencyTreeProviderFactory, deps: [DependencyTreeProvider], multi: true},
        Globals
      ],
    });
    httpTestingController = TestBed.get(HttpTestingController);
    service = TestBed.get(DependencyTreeProvider);
    TestBed.compileComponents();
    httpTestingController.expectOne('http://localhost:8080/getTree').flush(testdata);
    component = TestBed.createComponent(DependencyRootComponent).componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.node).toBe(service.getDependencyTree());
  });

  // Angular default test added when you generate a service using the CLI
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch node', () => {
    expect(service.getDependencyTree()).toBeTruthy();
  });

  it('should confirm node filename exists', () => {
    expect(service.getDependencyTree().filename).toBeTruthy();
  });

  it('should confirm node filename is coderadar as declared in testdata', () => {
    expect(service.getDependencyTree().filename).toBe('coderadar');
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
