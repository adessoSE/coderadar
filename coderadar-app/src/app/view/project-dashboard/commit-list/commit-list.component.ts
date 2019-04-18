import {Component, Input, OnInit} from '@angular/core';
import {Commit} from '../../../model/commit';
import {AppComponent} from '../../../app.component';
import {PageEvent} from '@angular/material';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';

@Component({
  selector: 'app-commit-list',
  templateUrl: './commit-list.component.html',
  styleUrls: ['./commit-list.component.scss']
})
export class CommitListComponent implements OnInit {

  @Input() project: Project;
  @Input() commits: Commit[];
  @Input() commitsAnalyzed;

  appComponent = AppComponent;

  pageEvent: PageEvent;

  pageSizeOptions: number[] = [5, 10, 25, 100];

  selectedCommit1: Commit;
  selectedCommit2: Commit;

  // These are needed for the deselection css to work
  prevSelectedCommit1: Commit;
  prevSelectedCommit2: Commit;

  pageSize = 5;

  constructor(private cityEffects: AppEffects) {
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    this.pageEvent = new PageEvent();
    this.pageEvent.pageSize = 5;
    this.pageEvent.pageIndex = 0;
  }

  ngOnInit() {
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

}
