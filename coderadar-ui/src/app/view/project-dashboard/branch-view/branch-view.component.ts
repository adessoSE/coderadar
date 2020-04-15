import {Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {createGitgraph, templateExtend, TemplateName} from '@gitgraph/js';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {Commit as GitGraphCommit, GitgraphUserApi} from '@gitgraph/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../../city-map/shared/reducers';
import {Router} from '@angular/router';
import {Branch} from '../../../model/branch';
import {CommitLog} from '../../../model/commit-log';
import {changeActiveFilter, setMetricMapping} from '../../../city-map/control-panel/settings/settings.actions';
import {changeCommit, setCommits} from '../../../city-map/control-panel/control-panel.actions';
import {CommitType} from '../../../city-map/enum/CommitType';
import {loadAvailableMetrics} from '../../../city-map/visualization/visualization.actions';
import * as _ from 'lodash';

@Component({
  selector: 'app-compare-branches',
  templateUrl: './branch-view.component.html',
  styleUrls: ['./branch-view.component.scss']
})
export class BranchViewComponent implements OnInit, OnChanges {

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
    const filtered: any = _.cloneDeep(this.commitLog.filter(value =>
      value.author.timestamp >= startDate.getTime() && value.author.timestamp <= (endDate.getTime() + 24 * 60 * 60 * 1000)));
    filtered.forEach(value => value.author.name = '');
    filtered.forEach(value => value.parents.forEach(parent => {
      if (filtered.find(value1 => value1.hash.localeCompare(parent) === 0) === undefined) {
        value.parents = value.parents.filter(value1 => value1.localeCompare(parent));
      }
    }));
    this.gitGraph.clear();
    filtered.forEach(commit => {
      commit.onClick = (val) => this.handleClickEvent(val);
      commit.onMessageClick = (val) => this.handleClickEvent(val);
      commit.onMouseOver = () => document.body.style.cursor = 'pointer';
      commit.onMouseOut = () => document.body.style.cursor = 'default';
    });
    this.gitGraph.import(filtered);
  }

  private initTree() {
    if (this.commitLog.length === 0) {
      return;
    }
    this.gitGraph = createGitgraph(this.graph.nativeElement, {
/*      compareBranchesOrder: ((branchNameA, branchNameB) => {
        return branchNameA === 'master' ? -1 : 1;
      }),*/ // Always show the master branch on the left??
      template: templateExtend(TemplateName.Metro,
        {
          colors: ['#979797', '#008fb5', '#f1c109', '#bf6356', '#b87bbf', '#86bf56', '#7ab8be']
        })});
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
    if (this.selectedCommit1.author.timestamp > this.selectedCommit2.author.timestamp) {
      const temp = this.selectedCommit1;
      this.selectedCommit1 = this.selectedCommit2;
      this.selectedCommit2 = temp;
    }
    const commits = this.commitLog.map(value => {
      return {name: value.hash, author: value.author.name,
        authorEmail: value.author.email, comment: value.subject, analyzed: value.analyzed,
        timestamp: value.author.timestamp};
    });
    this.store.dispatch(loadAvailableMetrics());
    this.store.dispatch(setCommits(commits));
    this.store.dispatch(changeCommit(CommitType.LEFT, commits.find(value => value.name.localeCompare(this.selectedCommit1.hash) === 0)));
    this.store.dispatch(changeCommit(CommitType.RIGHT, commits.find(value => value.name.localeCompare(this.selectedCommit2.hash) === 0)));
    this.store.dispatch(changeActiveFilter({
      added: true,
      deleted: true,
      modified: true,
      renamed: true,
      unmodified: false
    }));

    this.cityEffects.isLoaded = true;
    this.router.navigate(['/city/' + this.project.id]);
  }

  ngOnChanges(changes: SimpleChanges): void {
    let selectedCommit1Id = null;
    if (this.selectedCommit1 !== null && this.selectedCommit1 !== undefined) {
      selectedCommit1Id = this.selectedCommit1.hash;
    }
    let selectedCommit2Id = null;
    if (this.selectedCommit2 !== null && this.selectedCommit2 !== undefined) {
      selectedCommit2Id = this.selectedCommit2.hash;
    }
    if (selectedCommit1Id != null) {
      this.selectedCommit1 = this.commitLog.find(value => value.hash === selectedCommit1Id);
    }
    if (selectedCommit2Id != null) {
      this.selectedCommit2 = this.commitLog.find(value => value.hash === selectedCommit2Id);
    }
  }
}

