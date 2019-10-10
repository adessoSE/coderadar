import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {AnalyzerConfiguration} from '../../model/analyzer-configuration';
import {FilePattern} from '../../model/file-pattern';
import {CONFLICT, FORBIDDEN, UNPROCESSABLE_ENTITY} from 'http-status-codes';
import {Module} from '../../model/module';
import {Title} from '@angular/platform-browser';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-configure-project',
  templateUrl: './configure-project.component.html',
  styleUrls: ['./configure-project.component.scss']
})
export class ConfigureProjectComponent implements OnInit {


  projectName: string;
  analyzers: AnalyzerConfiguration[];
  filePatterns: FilePattern[];

  // Fields for input binding
  filePatternIncludeInput;
  filePatternExcludeInput;
  modulesInput;
  modules: Module[];
  processing = false;

  projectId: any;
  moduleExists = false;

  constructor(private snackBar: MatSnackBar, private router: Router, private userService: UserService,  private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.projectName = '';
    this.filePatternIncludeInput = '';
    this.filePatternExcludeInput = '';
    this.modulesInput = '';
    this.modules = [];
    this.analyzers = [];
    this.filePatterns = [];
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getProjectAnalyzers();
      this.getModulesForProject();
      this.getProjectName();
      this.getProjectFilePatterns();
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  /**
   * Gets all of the modules for the current project and saves them in this.modules.
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getModulesForProject(): void {
    this.projectService.getProjectModules(this.projectId)
      .then(response => this.modules = response.body)
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getModulesForProject());
        }
      });
  }

  /**
   * Gets all of the configured analyzers for this project and saves them in this.analyzers.
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getProjectAnalyzers(): void {
    this.projectService.getProjectAnalyzers(this.projectId).then(response => {
      if (response.body.length > 0) {
        this.analyzers = response.body;
      }
      this.getAvailableAnalyzers();
    })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProjectAnalyzers());
        }
      });
  }

  /**
   * Gets all available analyzers in coderadar in saves them in this.analyzers
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getAvailableAnalyzers(): void {
    this.projectService.getAnalyzers()
      .then(response => {
        response.body.forEach(a => {
          if (this.analyzers.find(value => value.analyzerName === a) === undefined) {
            this.analyzers.push(new AnalyzerConfiguration(a, false));
          }
        });
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getAvailableAnalyzers());
        }
      });
  }

  /**
   * Gets all of the configured file patterns for the current project and saves them in this.filePatterns.
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getProjectFilePatterns(): void {
    this.projectService.getProjectFilePatterns(this.projectId)
      .then(response => {
        if (response.body.length === 0) {
          this.filePatterns = [];
        } else {
          this.filePatterns = response.body;
        }
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProjectFilePatterns());
        }
      });
  }

  /**
   * Gets the current project name and saves in this.projectName.
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getProjectName(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.projectName = response.body.name;
        this.titleService.setTitle('Coderadar - Configure ' + this.projectName);
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProjectName());
        }
      });
  }

  /**
   * Calls ProjectService.addProjectModule()
   * depending on whether or not the module is new.
   * Sends the refresh token if access is denied and repeats the request.
   */
  public submitModule(): void {
    this.moduleExists = false;

    const module: Module = new Module(null, this.modulesInput);
    this.processing = true;
    this.projectService.addProjectModule(this.projectId, module).then(response => {
        module.id = response.body.id;
        this.processing = false;
        this.modules.push(module);
        this.modulesInput = '';
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.submitModule());
        }
        if (error.status && error.status === CONFLICT) {
          this.moduleExists = true;
          this.processing = false;
        }
        if (error.status && error.status === UNPROCESSABLE_ENTITY) {
          this.processing = false;
          this.openSnackBar('Cannot edit the project! Try again later', '🞩');
        }
      });
  }

  /**
   * Calls ProjectService.deleteProjectModule()
   * Sends the refresh token if access is denied and repeats the request.
   * @param module The module to delete from the project
   */
  private deleteModule(module: Module): void {
    this.processing = true;
    this.projectService.deleteProjectModule(this.projectId, module)
      .then(() => {
        this.processing = false;
        this.modules = this.modules.filter(value => value.path !== module.path);
      }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.deleteModule(module));
      }
      if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.processing = false;
        this.openSnackBar('Cannot edit the project! Try again later', '🞩');
      }
    });
  }

  /**
   * Calls submitAnalyzerConfiguration for each AnalyzerConfiguration in this.analyzers
   * (as the REST API doesn't allow to send them all at once).
   */
  private submitAnalyzerConfigurations(): void {
    this.analyzers.forEach(analyzer => this.submitAnalyzerConfiguration(analyzer));
  }

  /**
   * Calls ProjectService.editAnalyzerConfigurationForProject() or addAnalyzerConfigurationToProject depending on whether or not
   * the project had previously registered analyzers.
   *
   * @param analyzerConfiguration The configuration to add to the project.
   */
  private submitAnalyzerConfiguration(analyzerConfiguration: AnalyzerConfiguration): void {
    if (analyzerConfiguration.id !== undefined) {
      this.projectService.editAnalyzerConfigurationForProject(this.projectId, analyzerConfiguration)
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh(() => this.submitAnalyzerConfiguration(analyzerConfiguration));
          } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
            this.openSnackBar('Cannot edit the project! Try again later', '🞩');
          }
        });
    } else {
      this.projectService.addAnalyzerConfigurationToProject(this.projectId, analyzerConfiguration)
        .then(value => {
          analyzerConfiguration.id = value.body.id;
        })
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh(() => this.submitAnalyzerConfiguration(analyzerConfiguration));
          } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
            this.openSnackBar('Cannot edit the project! Try again later', '🞩');
          }
        });
    }

  }

  /**
   * Calls ProjectService.deleteProjectFilePattern()
   * Sends the refresh token if access is denied and repeats the request.
   * @param pattern The pattern to delete from the project
   */
  private deleteFilePattern(pattern: FilePattern): void {
    this.processing = true;
    this.projectService.deleteProjectFilePattern(this.projectId, pattern)
      .then(() => {
        this.processing = false;
        this.filePatterns = this.filePatterns.filter(value => value !== pattern);
      })
      .catch(error => {
        this.processing = false;
        if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.deleteFilePattern(pattern));
      }
    });
  }

  public submitFilePattern(type: string) {
    const pattern = new FilePattern();
    pattern.inclusionType = type;
    if (type === 'INCLUDE') {
      pattern.pattern = this.filePatternIncludeInput;
    } else if (type === 'EXCLUDE') {
      pattern.pattern = this.filePatternExcludeInput;
    }

    this.processing = true;
    this.projectService.addProjectFilePattern(this.projectId, pattern).then((value) => {
      this.processing = false;
      pattern.id = value.body.id;
      this.filePatterns.push(pattern);
      if (type === 'INCLUDE') {
        this.filePatternIncludeInput = '';
      } else if (type === 'EXCLUDE') {
        this.filePatternExcludeInput = '';
      }
    })
      .catch(error => {
        this.processing = false;
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.submitFilePattern(type));
        }
      });
  }
}
