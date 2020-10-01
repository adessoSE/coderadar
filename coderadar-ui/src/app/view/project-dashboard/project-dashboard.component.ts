import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {AppComponent} from '../../app.component';
import {Branch} from '../../model/branch';
import {Subscription, timer} from 'rxjs';
import {AppEffects} from '../../city-map/shared/effects';
import * as fromRoot from '../../city-map/shared/reducers';
import {Store} from '@ngrx/store';
import {loadAvailableMetrics} from '../../city-map/visualization/visualization.actions';
import {CommitLog} from '../../model/commit-log';
import {Contributor} from '../../model/contributor';
import {ContributorService} from '../../service/contributor.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.scss']
})
export class ProjectDashboardComponent implements OnInit, OnDestroy {

  appComponent = AppComponent;

  projectId;
  commits: Commit[];
  commitLog: CommitLog[];
  branches: Branch[];
  contributors: Contributor[];
  commitsAnalyzed = 0;
  project: Project;
  selectedBranch;
  selectedContributor: Contributor = new Contributor();
  waiting = false;
  updateCommitsTimer: Subscription;
  roles: string[] = [];
  analysisStatus = false;

  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute, private cityEffects: AppEffects,
              private store: Store<fromRoot.AppState>, private contributorService: ContributorService, private location: Location) {
    this.project = new Project();
    this.commits = [];
    this.selectedContributor.emailAddresses = [''];
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.selectedBranch = params.branch;
      this.getCommitTree();
      this.getCommits(true);
      if (this.commits.length === 0) {
        this.waiting = true;
      }
      this.getProject();
      this.getBranchesInProject();
      this.getContributors();
      // Schedule a task to check if all commits are analyzed and update them if they're not
      this.updateCommitsTimer = timer(4000, 8000).subscribe(() => {
        this.getCommits(false);
        this.getCommitTree();
        if (this.commits.length === 0) {
          this.getCommits(true);
        }
      });
      this.cityEffects.currentProjectId  = this.projectId;
    });
  }

  /**
   * Gets all commits for this project from the service and saves them in this.commits.
   */
  public getCommits(displayLoadingIndicator: boolean): void {
    this.projectService.getAnalyzingStatus(this.projectId).then(value => this.analysisStatus = value.body.status);
    this.waiting = displayLoadingIndicator;
    this.projectService.getCommitsForContributor(this.projectId, this.selectedBranch, this.selectedContributor.emailAddresses[0])
      .then(response => {
        this.commitsAnalyzed = 0;
        this.commits = response.body;
        this.commits.forEach(c => {
          if (c.analyzed) {
            this.commitsAnalyzed++;
          }
        });
        if (this.commitsAnalyzed > 0) {
          this.store.dispatch(loadAvailableMetrics());
        }
        this.waiting = false;
      })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh(() => this.getCommits(true));
        }
      });
  }

  private getBranchesInProject() {
    this.projectService.getProjectBranches(this.projectId)
      .then(response => {
        if (response.body.length === 0) {
          this.branches = [];
        } else {
          this.branches = response.body;
        }
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getBranchesInProject());
        }
      });
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProjectWithRolesForUser(this.projectId, UserService.getLoggedInUser().userId)
      .then(response => {
        this.project = new Project(response.body.project);
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(this.project.name));
        this.roles = response.body.roles;
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  ngOnDestroy(): void {
    this.updateCommitsTimer.unsubscribe();
  }

  handleBranchChange($event: any) {
    this.location.go(this.location.path(false).replace(this.selectedBranch, $event));
    this.selectedBranch = $event;
    this.getCommits(false);
  }

  private getCommitTree() {
    this.projectService.getCommitLog(this.projectId).then(value => {
      this.commitLog = value.body;
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.getCommitTree());
      }
    });
  }

  public getDefaultStartDate(): string {
    const date = new Date(this.commitLog[0].author.timestamp);
    date.setDate(date.getDate() - 30);
    return date.toISOString().split('T')[0];
  }

  private getContributors() {
    this.contributorService.getContributorsForProject(this.projectId)
      .then(value => {
        this.contributors = value.body;
      })
      .catch( error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getContributors());
        }
      });
  }

  handleContributorChange($event: any) {
    if ($event === undefined) {
      this.selectedContributor = new Contributor();
      this.selectedContributor.emailAddresses = [''];
    } else {
      this.selectedContributor = $event;
    }
    this.getCommits(false);
  }
}
