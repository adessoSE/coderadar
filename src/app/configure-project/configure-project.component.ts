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

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.getAnalyzersFromService();
  }

  submitForm() {
    /*this.projectService.addProject(this.project).then(response => {
      console.log(response);
      this.router.navigate(['/dashboard']);
    }).catch(response => {
      if (response.status) {
        if (response.status === 403) {
          this.userService.refresh().then(r => this.submitForm());
        }
      }
    });*/
    console.log(this.analyzers);
    // this.router.navigate(['/dashboard']);
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
