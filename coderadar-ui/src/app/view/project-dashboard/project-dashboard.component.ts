import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import { AppEffects } from 'src/app/city-map/shared/effects';
import {faClone, faSquare} from '@fortawesome/free-regular-svg-icons';
import {MatPaginator, PageEvent} from '@angular/material';
import {AppComponent} from '../../app.component';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../city-map/shared/reducers';
import {changeActiveFilter, setMetricMapping} from '../../city-map/control-panel/settings/settings.actions';
import {changeCommit, loadCommits} from '../../city-map/control-panel/control-panel.actions';
import {CommitType} from '../../city-map/enum/CommitType';
import {Observable, Subscription, timer} from 'rxjs';
import {loadAvailableMetrics} from '../../city-map/visualization/visualization.actions';

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.scss']
})
export class ProjectDashboardComponent implements OnInit, OnDestroy {

  appComponent = AppComponent;

  projectId;
  commits: Commit[];
  commitsAnalyzed = 0;
  project: Project;

  pageEvent: PageEvent;
  @ViewChild('paginator1') paginator1: MatPaginator;
  @ViewChild('paginator2') paginator2: MatPaginator;

  pageSizeOptions: number[] = [5, 10, 25, 100];

  selectedCommit1: Commit;
  selectedCommit2: Commit;

  // These are needed for the deselection css to work
  prevSelectedCommit1: Commit;
  prevSelectedCommit2: Commit;

  pageSize = 15;
  waiting = false;

  updateCommitsTimer: Subscription;

  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute, private store: Store<fromRoot.AppState>,
              private cityEffects: AppEffects) {
    this.project = new Project();
    this.commits = [];
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    this.pageEvent = new PageEvent();
    this.pageEvent.pageSize = this.pageSize;
    this.pageEvent.pageIndex = 0;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getCommits(true);
      this.getProject();
      // Schedule a task to check if all commits are analyzed and update them if they're not
      this.updateCommitsTimer = timer(4000, 8000).subscribe(() => {
        if (this.commitsAnalyzed < this.commits.length) {
          this.getCommits(false);
        }
      });

      this.cityEffects.currentProjectId  = this.projectId;
      this.store.dispatch(loadCommits());
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

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

  /**
   * Constructs a date from a timestamp and returns it in string form.
   * @param timestamp The timestamp to construct a date from.
   */
  timestampToDate(timestamp: number): string {
    return new Date(timestamp).toLocaleDateString();
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
  private getCommits(displayLoadingIndicator: boolean): void {
    this.waiting = displayLoadingIndicator;
    this.projectService.getCommits(this.projectId)
      .then(response => {
        this.commitsAnalyzed = 0;
        let selectedCommit1Id = null;
        if (this.selectedCommit1 !== null) {
          selectedCommit1Id = this.selectedCommit1.name;
        }
        let selectedCommit2Id = null;
        if (this.selectedCommit2 !== null) {
          selectedCommit2Id = this.selectedCommit2.name;
        }
        this.commits = response.body;
        this.commits.forEach(c => {
          if (c.analyzed) {
            this.commitsAnalyzed++;
          }
        });
        if (this.commitsAnalyzed > 0) {
          this.store.dispatch(loadAvailableMetrics());
        }
        if (selectedCommit1Id != null) {
          this.selectedCommit1 = this.commits.find(value => value.name === selectedCommit1Id);
        }
        if (selectedCommit2Id != null) {
          this.selectedCommit2 = this.commits.find(value => value.name === selectedCommit2Id);
        }
        this.commits.sort((a, b) => {
          if (a.timestamp === b.timestamp) {
            return 0;
          } else if (a.timestamp > b.timestamp) {
            return -1;
          } else {
            return 1;
          }
        });
        this.waiting = false;
      })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh(() => this.getCommits(true));
        }
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
          this.userService.refresh(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  selectCard(selectedCommit: Commit): void {
    if (this.selectedCommit1 === null && this.selectedCommit2 !== selectedCommit) {
      this.selectedCommit1 = selectedCommit;
    } else if (this.selectedCommit1 === selectedCommit) {
      this.selectedCommit1 = null;
      this.prevSelectedCommit1 = selectedCommit;
    } else if (this.selectedCommit2 === selectedCommit) {
      this.selectedCommit2 = null;
      this.prevSelectedCommit2 = selectedCommit;
    } else {
      this.selectedCommit2 = selectedCommit;
    }
  }

  syncPaginators(event: PageEvent) {
    this.paginator2.pageIndex = event.pageIndex;
    this.paginator2.pageSize = event.pageSize;
    this.paginator1.pageIndex = event.pageIndex;
    this.paginator1.pageSize = event.pageSize;
    this.pageEvent = event;
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
    if (this.selectedCommit1.timestamp > this.selectedCommit2.timestamp) {
      const temp = this.selectedCommit1;
      this.selectedCommit1 = this.selectedCommit2;
      this.selectedCommit2 = temp;
    }
    this.store.dispatch(changeCommit(CommitType.LEFT, this.selectedCommit1));
    this.store.dispatch(changeCommit(CommitType.RIGHT, this.selectedCommit2));
    this.store.dispatch(changeActiveFilter({
      added: true,
      deleted: true,
      modified: true,
      renamed: true,
      unmodified: false
    }));

    this.cityEffects.isLoaded = true;
    this.router.navigate(['/city/' + this.projectId]);
  }

  ngOnDestroy(): void {
    this.updateCommitsTimer.unsubscribe();
  }

}
