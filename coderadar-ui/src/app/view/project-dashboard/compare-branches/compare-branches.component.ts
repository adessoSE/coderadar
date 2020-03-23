import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {createGitgraph, templateExtend, TemplateName} from '@gitgraph/js';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {Commit as GitGraphCommit, GitgraphUserApi} from '@gitgraph/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../../city-map/shared/reducers';
import {Router} from '@angular/router';
import {Branch} from '../../../model/branch';
import {CommitLog} from '../../../model/commit-log';

@Component({
  selector: 'app-compare-branches',
  templateUrl: './compare-branches.component.html',
  styleUrls: ['./compare-branches.component.scss']
})
export class CompareBranchesComponent implements OnInit {

  constructor(private cityEffects: AppEffects, private store: Store<fromRoot.AppState>, private router: Router) {
  }

  @ViewChild('graph', {read: ElementRef})graph: ElementRef;

  @Input() public commitLog: CommitLog[] = [];

  @Input() public startDate: string;
  public endDate: string = null;

  @Input() project: Project;
  @Input() branches: Branch[];

  public selectedCommit1: CommitLog;
  public selectedCommit2: CommitLog;
  private selectedCommit1Element: GitGraphCommit;
  private selectedCommit2Element: GitGraphCommit;
  private gitGraph: GitgraphUserApi<SVGElement>;

  private selectedCommitColor = '#f60';

  ngOnInit() {
    this.initTree();
    this.showCommitsInRange();
  }

  public handleClickEvent(commitElement: any): void {
    const selectedCommit = this.commitLog.find(value1 => value1.hash === commitElement.hash);
    if (this.selectedCommit1 === null && this.selectedCommit2 !== selectedCommit) {
      this.selectedCommit1 = selectedCommit;

      if (this.selectedCommit1Element !== undefined) {
        this.selectedCommit1Element.style.message.color = commitElement.color;
        this.selectedCommit1Element.style.color = commitElement.color;
        this.selectedCommit1Element.style.dot.color = commitElement.color;
      }

      this.selectedCommit1Element = commitElement;

      commitElement.prevColor = commitElement.style.color;
      commitElement.style.message.color = this.selectedCommitColor;
      commitElement.style.color = this.selectedCommitColor;
      commitElement.style.dot.color = this.selectedCommitColor;

    } else if (this.selectedCommit1 === selectedCommit) {
      this.selectedCommit1 = null;

      commitElement.prevColor = commitElement.style.color;
      commitElement.style.message.color = commitElement.color;
      commitElement.style.color = commitElement.color;
      commitElement.style.dot.color = commitElement.color;

    } else if (this.selectedCommit2 === selectedCommit) {
      this.selectedCommit2 = null;

      commitElement.prevColor = commitElement.style.color;
      commitElement.style.message.color = commitElement.color;
      commitElement.style.color = commitElement.color;
      commitElement.style.dot.color = commitElement.color;
    } else {
      this.selectedCommit2 = selectedCommit;

      if (this.selectedCommit2Element !== undefined) {
        commitElement.prevColor = commitElement.style.color;
        this.selectedCommit2Element.style.message.color = commitElement.color;
        this.selectedCommit2Element.style.color = commitElement.color;
        this.selectedCommit2Element.style.dot.color = commitElement.color;
      }

      this.selectedCommit2Element = commitElement;

      commitElement.style.message.color = this.selectedCommitColor;
      commitElement.style.color = this.selectedCommitColor;
      commitElement.style.dot.color = this.selectedCommitColor;

    }
    // @ts-ignore
    this.gitGraph._onGraphUpdate();
  }

  showCommitsInRange() {
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
    let endDate: Date;
    if (this.endDate === null || this.endDate.length === 0) {
      endDate = new Date(this.commitLog[0].author.timestamp);
    } else {
      endDate = new Date(this.endDate);
    }
    let startDate: Date;
    if (this.startDate === null || this.startDate.length === 0) {
      startDate = new Date(this.commitLog[this.commitLog.length - 1].author.timestamp);
    } else {
      startDate = new Date(this.startDate);
    }
    const filtered = JSON.parse(JSON.stringify(this.commitLog.filter(value =>
      value.author.timestamp >= startDate.getTime() && value.author.timestamp <= (endDate.getTime() + 24 * 60 * 60 * 1000))));
    filtered.forEach(value => value.parents.forEach(parent => {
      if (filtered.find(value1 => value1.hash.localeCompare(parent) === 0) === undefined) {
        value.parents = value.parents.filter(value1 => value1.localeCompare(parent));
      }
    }));
    this.gitGraph.clear();
    filtered.forEach(commit => {
      commit.onClick = (val) => this.handleClickEvent(val);
      commit.onMessageClick = (val) => this.handleClickEvent(val);
    });
    this.gitGraph.import(filtered);
    // @ts-ignore
    this.gitGraph._graph.commits.forEach(val => console.log(val));
/*
    filteredCommits.forEach(value => {
      this.gitgraph.commit({
        hash: value.hash,
        subject: new Date(value.timestamp).toDateString(),
        author: value.author,
        onClick: commit => {
          this.handleClickEvent(commit);
        },
        onMessageClick: commit =>
      });
    });*/
  }

  private initTree() {
    if (this.commitLog.length === 0) {
      return;
    }
    this.commitLog.forEach(value => value.author.name = '');
    this.gitGraph = createGitgraph(this.graph.nativeElement, {template: templateExtend(TemplateName.Metro,
        {
          colors: ['#979797', '#008fb5', '#f1c109', '#bf5249', '#b87bbf', '#86bf56', '#7ab8be']
        })});
  }
}

