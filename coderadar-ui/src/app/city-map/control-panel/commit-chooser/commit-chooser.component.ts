import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommitType} from '../../enum/CommitType';
import {Commit} from '../../../model/commit';
import {Observable} from 'rxjs';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-commit-chooser',
  templateUrl: './commit-chooser.component.html',
  styleUrls: ['./commit-chooser.component.scss']
})
export class CommitChooserComponent implements OnInit {

  constructor(public datepipe: DatePipe) {}

  static readonly COMMIT_HASH_LENGTH = 7;

  @Input() commitType: CommitType;
  @Input() commits: Observable<Commit[]>;
  @Input() selected: Observable<Commit>;
  @Input() loading: boolean;
  @Input() isDisabled: boolean;
  @Input() label: string;

  @Output() changeCommit = new EventEmitter();

  ngOnInit() {
    this.selected.subscribe(value => this.handleCommitChanged(value));
  }


  formatCommit(commit: Commit): string {
    if (commit === null || commit === undefined) {
      return 'empty';
    }
    return commit.hash.substring(0, CommitChooserComponent.COMMIT_HASH_LENGTH) + ', ' +
      commit.author + ', ' + new Date(commit.timestamp).toUTCString();
  }

  filterCommitOptions(value: string, source: {value: Commit, displayValue: string}[]): {value: Commit, displayValue: string}[] {
    if (source === undefined) {
      return [];
    } else if (typeof value !== 'string') {
      return [];
    } else if (value === undefined) {
      return source;
    }
    const lowercaseValue = value.toLowerCase();

    const filteredCommits: any[] = source.filter(option => {
      let score = 0;
      const optionAny = option.value as any;
      if (typeof optionAny !== 'undefined') {
        if (optionAny.author.startsWith(value)) {score += 200; }
        if (optionAny.author.toLowerCase().startsWith(lowercaseValue)) {score += 100; }
        if (optionAny.author.includes(value)) {score += 50; }
        if (optionAny.author.toLowerCase().includes(lowercaseValue)) {score += 25; }
        if (optionAny.name.startsWith(value)) {score += 1000; }
        if (optionAny.name.includes(lowercaseValue)) {score += 500; }
        if (option.displayValue.substring(option.value.author.length + CommitChooserComponent.COMMIT_HASH_LENGTH)
          .toLowerCase().includes(lowercaseValue)) {score += 1000; }
        if (score > 0 || value === '') {
          optionAny.score = score;
          return option;
        }
      }
    });
    return filteredCommits.sort((a, b) => {
      return Math.sign(b.score - a.score);
    });
  }

  handleCommitChanged(chosenModel: Commit) {
    this.changeCommit.emit({commitType: this.commitType, commit: chosenModel});
  }
}
