import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {CommitType} from '../../enum/CommitType';
import {Commit} from '../../../model/commit';

@Component({
  selector: 'app-commit-chooser',
  templateUrl: './commit-chooser.component.html',
  styleUrls: ['./commit-chooser.component.scss']
})
export class CommitChooserComponent implements OnInit, OnChanges {

  @Input() commitType: CommitType;
  @Input() commits: Commit[];
  @Input() selected: Commit;
  @Input() loading: boolean;
  @Input() isDisabled: boolean;
  @Input() label: string;

  @Output() changeCommit = new EventEmitter();

  constructor() {}

  ngOnInit() {
    if (this.selected !== null && this.selected !== undefined) {
      this.handleCommitChanged(this.selected);
    }
  }

  handleCommitChanged(chosenModel: Commit) {
    this.changeCommit.emit({commitType: this.commitType, commit: chosenModel});
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.selected !== null && this.selected !== undefined) {
      this.handleCommitChanged(this.selected);
    }
  }
}
