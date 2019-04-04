import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {AnalyzerConfiguration} from '../../model/analyzer-configuration';
import {FilePatterns} from '../../model/file-patterns';
import {FORBIDDEN} from 'http-status-codes';
import {Module} from '../../model/module';

@Component({
  selector: 'app-configure-project',
  templateUrl: './configure-project.component.html',
  styleUrls: ['./configure-project.component.css']
})
export class ConfigureProjectComponent implements OnInit {


  private projectId: any;
  projectName: string;

  analyzers: AnalyzerConfiguration[];
  filePatterns: FilePatterns[];

  // Fields for input binding
  filePatternIncludeInput;
  filePatternExcludeInput;
  modulesInput;
  modules: Module[];
  deletedModules: Module[];
  startScan: boolean;

  // Error fields
  noAnalyzersForJob: boolean;
  analyzersExist: boolean;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.projectName = '';
    this.filePatternIncludeInput = '';
    this.filePatternExcludeInput = '';
    this.modulesInput = '';
    this.modules = [];
    this.deletedModules = [];
    this.analyzers = [];
    this.filePatterns = [];
    this.startScan = false;
    this.noAnalyzersForJob = false;
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
      }})
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
        response.body.forEach(a => this.analyzers.push(new AnalyzerConfiguration(a.analyzerName, false)));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getAvailableAnalyzers());
        }});
  }

  /**
   * Gets all of the configured file patterns for the current project and saves them in this.filePatterns.
   * Sends the refresh token if access is denied and repeats the request.
   */
  private getProjectFilePatterns(): void {
    this.projectService.getProjectFilePatterns(this.projectId)
      .then(response => this.filePatterns = response.body.filePatterns)
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
      .then(response => this.projectName = response.body.name)
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProjectName());
        }
    });
  }

  /**
   * Is called when the form is submitted.
   * Does input validation and calls the appropriate submit method for each part of the form.
   */
  submitForm(): void {
    this.noAnalyzersForJob = this.analyzers.filter(analyzer => analyzer.enabled).length === 0 && this.startScan === true;
    if (!this.noAnalyzersForJob ) {
      this.noAnalyzersForJob = true;
      this.submitAnalyzerConfigurations();
      this.submitFilePatterns();
      this.submitModules();
      if (this.startScan) {
        this.projectService.startAnalyzingJob(this.projectId, true).catch();
      }
      this.router.navigate(['/dashboard']);
    }
  }

  /**
   * Calls ProjectService.setProjectFilePatterns().
   * Sends the refresh token if access is denied and repeats the request.
   */
  private submitFilePatterns(): void {
    this.projectService.setProjectFilePatterns(this.projectId, this.filePatterns)
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.submitFilePatterns());
        }
      });
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
   * Constructs a new FilePatterns object (INCLUDE) with whatever is in filePatternIncludeInput
   * and adds it to filePatterns.
   */
  addToIncludedPatterns(): void {
    if (this.filePatternIncludeInput.trim() !== '' &&
      this.filePatterns.filter(p => p.pattern === this.filePatternIncludeInput).length === 0) {
      const pattern = new FilePatterns();
      pattern.pattern = this.filePatternIncludeInput;
      pattern.inclusionType = 'INCLUDE';
      this.filePatterns.push(pattern);
      this.filePatternIncludeInput = '';
    }
  }

  /**
   * Constructs a new FilePatterns object (EXCLUDE) with whatever is in filePatternIncludeInput
   * and adds it to filePatterns.
   */
  addToExcludedPatterns(): void {
    if (this.filePatternExcludeInput.trim() !== '' &&
      this.filePatterns.filter(p => p.pattern === this.filePatternExcludeInput).length === 0) {
      const pattern = new FilePatterns();
      pattern.pattern = this.filePatternExcludeInput;
      pattern.inclusionType = 'EXCLUDE';
      this.filePatterns.push(pattern);
      this.filePatternExcludeInput = '';
    }
  }

  /**
   * Constructs a new Module object (EXCLUDE) with whatever is in modulesInput
   * and adds it to modules.
   */
  addToModules(): void {
    if (this.modulesInput.trim() !== '' && this.modules.filter(m => m.modulePath === this.modulesInput).length === 0) {
      const deletedModules = this.deletedModules.filter(m => m.modulePath === this.modulesInput);
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
   * Removes the Module from this.modules and adds it this.deleteModules
   */
  addToDeletedModules(module: Module): void {
    this.modules.splice(this.modules.indexOf(module), 1);
    if (module.id != null) {
      this.deletedModules.push(module);
    }
  }
}
