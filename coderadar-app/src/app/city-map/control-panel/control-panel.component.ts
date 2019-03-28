import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../shared/reducers';
import {changeCommit, loadCommits} from './control-panel.actions';
import {FocusService} from '../service/focus.service';
import {ViewType} from '../enum/ViewType';
import {CommitType} from '../enum/CommitType';
import {Observable} from 'rxjs';
import {Commit} from '../../model/commit';

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
  screenShots$: Observable<any[]>;

  // disable the second commit chooser for demo purposes
  disableRightSelect: true;

  constructor(private store: Store<fromRoot.AppState>, private focusService: FocusService) {
  }

  ngOnInit() {
    if (this.store !== undefined) {
      this.store.dispatch(loadCommits());

      this.commits$ = this.store.select(fromRoot.getCommits);
      this.commitsLoading$ = this.store.select(fromRoot.getCommitsLoading);
      this.leftCommit$ = this.store.select(fromRoot.getLeftCommit);
      this.rightCommit$ = this.store.select(fromRoot.getRightCommit);

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
