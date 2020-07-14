import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {Commit} from '../../../model/commit';
import {AppComponent} from '../../../app.component';
import {MatPaginator, MatSnackBar, PageEvent} from '@angular/material';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {FORBIDDEN, UNPROCESSABLE_ENTITY} from 'http-status-codes';
import {changeCommit, setCommits} from '../../../city-map/control-panel/control-panel.actions';
import {changeActiveFilter, setMetricMapping} from '../../../city-map/control-panel/settings/settings.actions';
import {CommitType} from '../../../city-map/enum/CommitType';
import * as fromRoot from '../../../city-map/shared/reducers';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {Title} from '@angular/platform-browser';
import {ProjectService} from '../../../service/project.service';
import {Store} from '@ngrx/store';
import {Branch} from '../../../model/branch';
import {loadAvailableMetrics} from '../../../city-map/visualization/visualization.actions';
import {Contributor} from "../../../model/contributor";

@Component({
  selector: 'app-commit-list',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.scss']
})
export class ListViewComponent implements OnInit, OnChanges {

  @Input() project: Project;
  @Input() commits: Commit[];
  commitsFiltered: Commit[];
  commitsAnalyzed;
  @Input() branches: Branch[];
  @Input() waiting: boolean;
  @Input() contributors: Contributor[];
  @Output() branchOutput = new EventEmitter();
  @Output() contributorOutput = new EventEmitter();

  appComponent = AppComponent;
  projectId;

  pageEvent: PageEvent;
  @ViewChild('paginator1') paginator1: MatPaginator;
  @ViewChild('paginator2') paginator2: MatPaginator;

  pageSizeOptions: number[] = [15, 25, 50, 100];

  selectedCommit1: Commit;
  selectedCommit2: Commit;
  selectedBranch = 'master';
  selectedContributor: Contributor = null;

  // These are needed for the deselection css to work
  prevSelectedCommit1: Commit;
  prevSelectedCommit2: Commit;

  pageSize = 15;
  public startDate: string = null;
  public endDate: string = null;

  constructor(private snackBar: MatSnackBar, private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute, private store: Store<fromRoot.AppState>,
              private cityEffects: AppEffects) {
    this.project = new Project();
    this.commits = [];
    this.commitsFiltered = [];
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    this.pageEvent = new PageEvent();
    this.pageEvent.pageSize = this.pageSize;
    this.pageEvent.pageIndex = 0;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.cityEffects.currentProjectId  = this.projectId;
      this.showCommitsInRange();
    });
  }

  startAnalysis(id: number, branch: string) {
    this.projectService.startAnalyzingJob(id, branch).then(() => {
      this.openSnackBar('Analysis started!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.startAnalyzingJob(id, branch));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        if (error.error.errorMessage === 'Cannot analyze project without analyzers') {
          this.openSnackBar('Cannot analyze, no analyzers configured for this project!', '🞩');
        } else if (error.error.errorMessage === 'Cannot analyze project without file patterns') {
          this.openSnackBar('Cannot analyze, no file patterns configured for this project!', '🞩');
        } else {
          this.openSnackBar('Analysis cannot be started! Try again later!', '🞩');
        }
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
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
    return new Date(timestamp).toLocaleDateString();
  }

  /**
   * Formats the title text according to the project start and end dates.
   */
  getTitleText(): string {
    const projectName = this.appComponent.trimProjectName(this.project.name);
    if (this.project.startDate === 'first commit') {
      return 'Showing commits for project ' + projectName + ' (' + this.commits.length + ')';
    } else {
      return 'Showing commits for project ' + projectName + ' from ' + this.project.startDate + ' up until today'
        + ' (' + this.commits.length + ')';
    }
  }

  resetAnalysis(id: number) {
    this.commitsAnalyzed = 0;
    this.projectService.resetAnalysis(id).then(() => {
      this.openSnackBar('Analysis results deleted!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.resetAnalysis(id));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis results cannot be deleted! Try again later!', '🞩');
      }
    });
  }

  stopAnalysis(id: number) {
    this.projectService.stopAnalyzingJob(id).then(() => {
      this.openSnackBar('Analysis stopped!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.stopAnalyzingJob(id));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis stopped!', '🞩');
      }
    });
  }

  selectCard(selectedCommit: Commit): void {
    if ((this.selectedCommit1 === null || this.selectedCommit1 === undefined)
      && this.selectedCommit2 !== selectedCommit) {
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
    this.store.dispatch(setCommits(this.commits));
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

  ngOnChanges(changes: SimpleChanges): void {
    this.showCommitsInRange();
    let selectedCommit1Id = null;
    if (this.selectedCommit1 !== null && this.selectedCommit1 !== undefined) {
      selectedCommit1Id = this.selectedCommit1.hash;
    }
    let selectedCommit2Id = null;
    if (this.selectedCommit2 !== null && this.selectedCommit2 !== undefined) {
      selectedCommit2Id = this.selectedCommit2.hash;
    }
    if (this.commitsAnalyzed > 0) {
      this.store.dispatch(loadAvailableMetrics());
    }
    if (selectedCommit1Id != null) {
      this.selectedCommit1 = this.commitsFiltered.find(value => value.hash === selectedCommit1Id);
    }``
    if (selectedCommit2Id != null) {
      this.selectedCommit2 = this.commitsFiltered.find(value => value.hash === selectedCommit2Id);
    }
  }

  showCommitsInRange() {
    if(this.commits.length > 0) {
      let endDate: Date;
      if (this.endDate === null || this.endDate.length === 0) {
        endDate = new Date(this.commits[0].timestamp);
      } else {
        endDate = new Date(this.endDate);
      }
      let startDate: Date;
      if (this.startDate === null || this.startDate.length === 0) {
        startDate = new Date(this.commits[this.commits.length - 1].timestamp);
      } else {
        startDate = new Date(this.startDate);
      }
      this.commitsFiltered = this.commits.filter(value =>
        value.timestamp >= startDate.getTime() && value.timestamp <= (endDate.getTime() + 24 * 60 * 60 * 1000));
    } else {
      this.commitsFiltered = [];
    }
    this.commitsAnalyzed = this.commitsFiltered.filter(value => value.analyzed).length;
  }
}
