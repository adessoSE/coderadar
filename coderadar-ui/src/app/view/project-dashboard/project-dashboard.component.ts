import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import { AppEffects } from 'src/app/city-map/shared/effects';
import {faClone, faSquare} from '@fortawesome/free-regular-svg-icons';
import {PageEvent} from '@angular/material';
import {AppComponent} from '../../app.component';


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

  // These are needed for the deselection css to work
  prevSelectedCommit1: Commit;
  prevSelectedCommit2: Commit;

  pageSize = 5;
  waiting = false;

  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute,
              private cityEffects: AppEffects) {
    this.project = new Project();
    this.commits = [];
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    this.pageEvent = new PageEvent();
    this.pageEvent.pageSize = 5;
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
  private getCommits(): void {
    this.waiting = true;
    this.projectService.getCommits(this.projectId)
      .then(response => {
        this.commits = response.body;
        this.commits.forEach(c => {
          if (c.analyzed) {
            this.commitsAnalyzed++;
          }
        });
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
          this.userService.refresh(() => this.getCommits());
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

    this.cityEffects.firstCommit = this.selectedCommit1;
    this.cityEffects.secondCommit = this.selectedCommit2;
  }
}
