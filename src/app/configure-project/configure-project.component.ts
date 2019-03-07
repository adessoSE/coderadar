import { Component, OnInit } from '@angular/core';
import {Project} from '../project';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../user.service';
import {ProjectService} from '../project.service';
import {AnalyzerConfiguration} from '../analyzer-configuration';
import {FilePatterns} from '../file-patterns';

@Component({
  selector: 'app-configure-project',
  templateUrl: './configure-project.component.html',
  styleUrls: ['./configure-project.component.css']
})
export class ConfigureProjectComponent implements OnInit {

  project: Project = new Project();
  private projectId: any;

  analyzers: AnalyzerConfiguration[] = [];
  filePatterns: FilePatterns[] = [];
  filePatternIncludeInput = '';
  filePatternExcludeInput = '';
  modulesInput = '';
  modules: string[] = [];
  startScan = false;
  noAnalyzersForJob = false;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.getAnalyzersFromService();
  }

  sendFilePatterns() {
    this.projectService.setProjectFilePatterns(this.projectId, this.filePatterns).then().catch(error => {
      console.log(error);
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(() => this.sendFilePatterns());
        }
      }
    });
  }

  sendModules() {
    this.modules.forEach(module => this.sendModule(module));
  }


  sendModule(module: string) {
    this.projectService.addProjectModule(this.projectId, module).then().catch(error => {
      console.log(error);
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(() => this.sendModule(module));
        }
      }
    });
  }

  sendAnalzyerConfiugurations() {
    this.analyzers.forEach(analyzer => this.sendAnalzyerConfiuguration(analyzer));
  }

  sendAnalzyerConfiuguration(analyzerConfiguration: AnalyzerConfiguration) {
    this.projectService.addAnalyzerConfigurationToProject(this.projectId, analyzerConfiguration).then().catch(error => {
      console.log(error);
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(() => this.sendAnalzyerConfiuguration(analyzerConfiguration));
        }
      }
    });
  }

  submitForm() {
    this.noAnalyzersForJob = false;
    if ( this.analyzers.filter(analyzer => analyzer.enabled).length === 0 ){
      this.noAnalyzersForJob = true;
      return;
    }
    this.sendAnalzyerConfiugurations();
    this.sendFilePatterns();
    this.sendModules();
    if (this.startScan) {
      this.projectService.startAnalyzingJob(this.projectId).catch(error => console.log(error));
    }
    this.router.navigate(['/dashboard']);
  }

  addToIncludedPatterns() {
    const pattern = new FilePatterns();
    pattern.pattern = this.filePatternIncludeInput;
    pattern.fileSetType = 'SOURCE';
    pattern.inclusionType = 'INCLUDE';
    this.filePatterns.push(pattern);
    this.filePatternIncludeInput = '';
  }

  addToExcludedPatterns() {
    const pattern = new FilePatterns();
    pattern.pattern = this.filePatternExcludeInput;
    pattern.fileSetType = 'SOURCE';
    pattern.inclusionType = 'EXCLUDE';
    this.filePatterns.push(pattern);
    this.filePatternExcludeInput = '';
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
    });
  }

  private getAnalyzersFromService() {
    this.projectService.getAnalyzers()
      .then(response => {
        console.log(response);
        response.body.forEach(a => this.analyzers.push(new AnalyzerConfiguration(a.analyzerName, false))); })
      .catch(error => {
        if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(response => this.getAnalyzersFromService());
        }
      }});
  }

  formatAnalyzerName(name: string) {
    return name.split('.').pop();
  }
}
