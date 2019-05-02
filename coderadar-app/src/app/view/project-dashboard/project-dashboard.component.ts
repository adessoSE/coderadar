import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {PageEvent} from '@angular/material';
import {AppComponent} from '../../app.component';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../city-map/shared/reducers';
import {changeCommit, loadCommits} from '../../city-map/control-panel/control-panel.actions';
import {CommitType} from '../../city-map/enum/CommitType';
import {changeActiveFilter, setMetricMapping} from '../../city-map/control-panel/settings/settings.actions';
import {Observable} from 'rxjs';
import {take} from 'rxjs/operators';


@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.scss']
})
export class ProjectDashboardComponent implements OnInit {

  appComponent = AppComponent;

  projectId;
  commits: Commit[];
  commitsAnalyzed = 0;
  project: Project;

  pageEvent: PageEvent;

  pageSizeOptions: number[] = [5, 10, 25, 100];

  selectedCommit1: Commit;
  selectedCommit2: Commit;

  actions = [{ name: 'Complexity Hot-spots', icon: 'bar_chart', onclick: () => this.startComplexityAnalysis()},
            { name: 'Modification Hot-spots', icon: 'low_priority', onclick: () => this.startComplexityAnalysis() },
            { name: 'Know-how Hot-spots', icon: 'group', onclick: () => this.startComplexityAnalysis() }];

  pageSize = 25;

  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute,
              private store: Store<fromRoot.AppState>) {
    this.project = new Project();
    this.commits = [];
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    this.pageEvent = new PageEvent();
    this.pageEvent.pageSize = 25;
    this.pageEvent.pageIndex = 0;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getCommits();
      this.getProject();
    });
  }

  /**
   * Return 'yes' for true and 'no' for false.
   * @param arg boolean
   */
  booleanToString(arg: boolean): string {
    if (arg) {
      return 'yes';
    } else {
      return 'no';
    }
  }

  /**
   * Constructs a date from a timestamp and returns it in string form.
   * @param timestamp The timestamp to construct a date from.
   */
  timestampToDate(timestamp: number): string {
    const date = new Date(timestamp);
    return date.toDateString() + ' ' + date.toLocaleTimeString();
  }

  /**
   * Formats the title text according to the project start and end dates.
   */
  getTitleText(): string {
    const projectName = this.appComponent.trimProjectName(this.project.name);
    if (this.project.startDate === 'first commit' && this.project.endDate === 'current') {
      return 'Showing commits for project ' + projectName + ' (' + this.commits.length + ')';
    } else if (this.project.startDate !== 'first commit' && this.project.endDate === 'current') {
      return 'Showing commits for project ' + projectName + ' from ' + this.project.startDate + ' up until today'
        + ' (' + this.commits.length + ')';
    } else if (this.project.startDate === 'first commit' && this.project.endDate !== 'current') {
      return 'Showing commits for project ' + projectName + ' from '
        + new Date(this.commits[this.commits.length - 1].timestamp).toLocaleDateString() + ' up until ' + this.project.endDate
        + ' (' + this.commits.length + ')';
    } else {
      return 'Showing commits for project ' + projectName + ' from '
        + this.project.startDate + ' up until ' + this.project.endDate + ' (' + this.commits.length + ')';
    }
  }

  /**
   * Gets all commits for this project from the service and saves them in this.commits.
   */
  private getCommits(): void {
    const id = this.store.select(fromRoot.getProjectId);
    id.pipe(take(1)).subscribe(result => {
      if (result === this.projectId && this.commits.length === 0) {
        this.setCommits(this.store.select(fromRoot.getCommits));
      } else {
        this.store.dispatch(loadCommits(this.projectId));
        this.setCommits(this.store.select(fromRoot.getCommits));
      }
    });
  }


  private setCommits(commits: Observable<Commit[]>) {
    commits.subscribe(result => {
      this.commits = result;
      this.commits.sort((a, b) => {
        if (a.timestamp === b.timestamp) {
          return 0;
        } else if (a.timestamp > b.timestamp) {
          return -1;
        } else {
          return 1;
        }
      });
      this.commitsAnalyzed = 0;
      this.commits.forEach(value => {
        if (value.analyzed) {
          this.commitsAnalyzed++;
        }
      });
    });
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(this.project.name));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  /**
   * Selects one of the two commits to compare.
   * @param selectedCommit The Commit to select
   */
  selectCard(selectedCommit: Commit): void {
    if (this.selectedCommit1 === null && this.selectedCommit2 !== selectedCommit) {
      this.selectedCommit1 = selectedCommit;
    } else if (this.selectedCommit1 === selectedCommit) {
      this.selectedCommit1 = null;
    } else if (this.selectedCommit2 === selectedCommit) {
      this.selectedCommit2 = null;
    } else {
      this.selectedCommit2 = selectedCommit;
    }
  }

  /**
   * Opens the 3D city-map with the complexity preset (LOC+LOC+Complexity, without unmodified) for the selected commits.
   * Switches the commit positions if the first one is newer than the second one.
   */
  startComplexityAnalysis(): void {
    this.store.dispatch(setMetricMapping({
      colorMetricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck',
      groundAreaMetricName: 'coderadar:size:sloc:java',
      heightMetricName: 'coderadar:size:sloc:java',
    }));
    this.store.dispatch(changeActiveFilter({
      added: true,
      deleted: true,
      modified: true,
      renamed: true,
      unmodified: false
    }));
    if (this.selectedCommit1.timestamp > this.selectedCommit2.timestamp) {
      const temp = this.selectedCommit1;
      this.selectedCommit1 = this.selectedCommit2;
      this.selectedCommit2 = temp;
    }
    this.store.dispatch(changeCommit(CommitType.LEFT, this.selectedCommit1));
    this.store.dispatch(changeCommit(CommitType.RIGHT, this.selectedCommit2));
    this.router.navigate(['/city/' + this.projectId]);
  }
}
