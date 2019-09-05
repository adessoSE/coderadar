import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {AnalyzerConfiguration} from '../../model/analyzer-configuration';
import {FilePattern} from '../../model/file-pattern';
import {FORBIDDEN, UNPROCESSABLE_ENTITY} from 'http-status-codes';
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
  deletedFilePatterns: FilePattern[];

  // Fields for input binding
  filePatternIncludeInput;
  filePatternExcludeInput;
  modulesInput;
  modules: Module[];
  deletedModules: Module[];
  // Error fields
  noAnalyzersForJob: boolean;
  noPatternsForJob: boolean;
  analyzersExist: boolean;
  projectId: any;

  constructor(private snackBar: MatSnackBar, private router: Router, private userService: UserService,  private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.projectName = '';
    this.filePatternIncludeInput = '';
    this.filePatternExcludeInput = '';
    this.modulesInput = '';
    this.modules = [];
    this.deletedModules = [];
    this.analyzers = [];
    this.filePatterns = [];
    this.deletedFilePatterns = [];
    this.noAnalyzersForJob = false;
    this.noPatternsForJob = false;
    this.analyzersExist = false;
  }

  ngOnInit(): void {
    this.analyzersExist = false;
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getAvailableAnalyzers();
      this.getModulesForProject();
      this.getProjectName();
      this.getProjectFilePatterns();
      this.getProjectAnalyzers();
    });
  }

  /**
   * Is called when the form is submitted.
   * Does input validation and calls the appropriate submit method for each part of the form.
   */
  submitForm(): void {
    this.noAnalyzersForJob = true;
    Promise.all([
      this.submitAnalyzerConfigurations(),
      this.submitFilePatterns(),
      this.submitModules()
    ]).then(() => {
      this.openSnackBar('Configuration saved!', '🞩');
      this.router.navigate(['/dashboard']);
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh().then(() => this.submitForm());
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  /**
   * Constructs a new FilePatterns object with whatever is in filePatternIncludeInput
   * and adds it to filePatterns.
   */
  addToPatterns(type: string): void {
    let input = '';
    if (type === 'INCLUDE') {
      input = this.filePatternIncludeInput;
      this.filePatternIncludeInput = '';
    } else {
      input = this.filePatternExcludeInput;
      this.filePatternExcludeInput = '';
    }
    if (input.trim() !== '' && this.filePatterns.filter(m => m.pattern === input).length === 0) {
      const deletedFilePatterns = this.deletedFilePatterns.filter(m => m.pattern === input);
      if (deletedFilePatterns.length !== 0) {
        this.filePatterns.push(deletedFilePatterns[0]);
        this.deletedFilePatterns.splice(this.deletedFilePatterns.indexOf(deletedFilePatterns[0]), 1);
      } else {
        const pattern = new FilePattern();
        pattern.pattern = input;
        pattern.inclusionType = type;
        this.filePatterns.push(pattern);
      }
    }
  }

  /**
   * Constructs a new Module object with whatever is in modulesInput
   * and adds it to modules.
   */
  addToModules(): void {
    if (this.modulesInput.trim() !== '' && this.modules.filter(m => m.path === this.modulesInput).length === 0) {
      const deletedModules = this.deletedModules.filter(m => m.path === this.modulesInput);
      if (deletedModules.length !== 0) {
        this.modules.push(deletedModules[0]);
        this.deletedModules.splice(this.deletedModules.indexOf(deletedModules[0]), 1);
      } else {
        const module = new Module(null, this.modulesInput);
        this.modules.push(module);
      }
      this.modulesInput = '';
    }
  }

  /**
   * Removes the Module from this.modules and adds it this.deletedModules
   */
  addToDeletedModules(module: Module): void {
    this.modules.splice(this.modules.indexOf(module), 1);
    if (module.id != null) {
      this.deletedModules.push(module);
    }
  }

  /**
   * Removes the FilePattern from this.filePatterns and adds it this.deletedFilePatterns
   */
  addToDeletedFilePatterns(filePattern: FilePattern): void {
    this.filePatterns.splice(this.filePatterns.indexOf(filePattern), 1);
    if (filePattern.id != null) {
      this.deletedFilePatterns.push(filePattern);
    }
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
          this.userService.refresh().then(() => this.getModulesForProject());
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
        this.analyzersExist = true;
      }
    })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProjectAnalyzers());
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
        response.body.forEach(a => this.analyzers.push(new AnalyzerConfiguration(a, false)));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getAvailableAnalyzers());
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
          this.userService.refresh().then(() => this.getProjectFilePatterns());
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
          this.userService.refresh().then(() => this.getProjectName());
        }
      });
  }

  /**
   * Calls ProjectService.setProjectFilePatterns().
   * Sends the refresh token if access is denied and repeats the request.
   */
  private submitFilePatterns(): void {
    this.deletedFilePatterns.forEach(pattern => this.deleteFilePattern(pattern));
    this.filePatterns.forEach(pattern => this.submitFilePattern(pattern));
  }

  /**
   * Calls submitModule or deleteModule for every string in this.modules (as the REST API doesn't allow to send them all at once).
   */
  private submitModules(): void {
    this.deletedModules.forEach(module => this.deleteModule(module));
    this.modules.forEach(module => this.submitModule(module));
  }

  /**
   * Calls ProjectService.addProjectModule() or editProjectModule()
   * depending on whether or not the module is new.
   * Sends the refresh token if access is denied and repeats the request.
   * @param module The module to add to the project
   */
  private submitModule(module: Module): void {
    if (module.id == null) {
      this.projectService.addProjectModule(this.projectId, module).catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.submitModule(module));
        }
      });
    } else if (module.id >= 0) {
      this.projectService.editProjectModule(this.projectId, module).catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.submitModule(module));
        }
      });
    }
  }

  /**
   * Calls ProjectService.deleteProjectModule()
   * Sends the refresh token if access is denied and repeats the request.
   * @param module The module to delete from the project
   */
  private deleteModule(module: Module): void {
    this.projectService.deleteProjectModule(this.projectId, module).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh().then(() => this.submitModule(module));
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
    if (this.analyzersExist) {
      this.projectService.editAnalyzerConfigurationForProject(this.projectId, analyzerConfiguration)
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.submitAnalyzerConfiguration(analyzerConfiguration));
          }
        });
    } else {
      this.projectService.addAnalyzerConfigurationToProject(this.projectId, analyzerConfiguration)
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.submitAnalyzerConfiguration(analyzerConfiguration));
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
    this.projectService.deleteProjectFilePattern(this.projectId, pattern).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh().then(() => this.deleteFilePattern(pattern));
      }
    });
  }

  private submitFilePattern(pattern: FilePattern) {
    if (pattern.id === null) {
      this.projectService.addProjectFilePattern(this.projectId, pattern).catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.submitFilePattern(pattern));
        }
      });
    }
  }
}
