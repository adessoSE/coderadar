import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../shared/reducers';
import {changeCommit, loadCommits} from './control-panel.actions';
import {FocusService} from '../service/focus.service';
import {ViewType} from '../enum/ViewType';
import {CommitType} from '../enum/CommitType';
import {Observable, of} from 'rxjs';
import {Commit} from '../../model/commit';
import {map} from 'rxjs/operators';
import { AppEffects } from '../shared/effects';

@Component({
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.scss']
})
export class ControlPanelComponent implements OnInit {

  commitTypes: any = {
    left: CommitType.LEFT,
    right: CommitType.RIGHT
  };

  commits$: Observable<Commit[]>;
  leftCommit$: Observable<Commit>;
  rightCommit$: Observable<Commit>;
  commitsLoading$: Observable<boolean>;

  uniqueFileList$: Observable<string[]>;

  activeViewType$: Observable<ViewType>;

  // disable the second commit chooser for demo purposes
  disableRightSelect: true;

  constructor(private store: Store<fromRoot.AppState>, private focusService: FocusService,
              private cityEffects: AppEffects) {
  }

  ngOnInit() {
    if (this.store !== undefined) {
      this.store.dispatch(loadCommits());

      this.commits$ = this.store.select(fromRoot.getCommits).pipe(map(elements => elements.sort((a, b) => {
        if (a.timestamp === b.timestamp) {
          return 0;
        } else if (a.timestamp > b.timestamp) {
          return -1;
        } else {
          return 1;
        }
      })), map((elements => elements.filter(val => val.analyzed))));

      this.commitsLoading$ = this.store.select(fromRoot.getCommitsLoading);

      if (this.cityEffects.firstCommit !== null && this.cityEffects.secondCommit !== null) {
        this.leftCommit$ = of(this.cityEffects.firstCommit);
        this.rightCommit$ = of(this.cityEffects.secondCommit);
      } else {
        this.leftCommit$ = this.store.select(fromRoot.getLeftCommit);
        this.rightCommit$ = this.store.select(fromRoot.getRightCommit);
      }
      this.uniqueFileList$ = this.store.select(fromRoot.getUniqueFileList);
      this.activeViewType$ = this.store.select(fromRoot.getActiveViewType);
    }
  }

  handleCommitChanged(payload: { commitType: CommitType, commit: Commit }) {
    this.store.dispatch(changeCommit(payload.commitType, payload.commit));
  }

  handleSearchStarted(chosenItem: string) {
    this.focusService.focusElement(chosenItem);
  }
}
