import {Component, ElementRef, HostListener, Input, OnInit, Output, ViewChild} from '@angular/core';
import {createGitgraph, Orientation} from '@gitgraph/js';
import {Commit} from '../../../model/commit';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {BranchUserApi, GitgraphUserApi} from '@gitgraph/core';
import {Commit as GitGraphCommit} from '@gitgraph/core';
import {changeActiveFilter, setMetricMapping} from '../../../city-map/control-panel/settings/settings.actions';
import {changeCommit, setCommits} from '../../../city-map/control-panel/control-panel.actions';
import {CommitType} from '../../../city-map/enum/CommitType';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../../city-map/shared/reducers';
import {Router} from '@angular/router';
import {Branch} from '../../../model/branch';

@Component({
  selector: 'app-compare-branches',
  templateUrl: './compare-branches.component.html',
  styleUrls: ['./compare-branches.component.scss']
})
export class CompareBranchesComponent implements OnInit {

  constructor(private cityEffects: AppEffects, private store: Store<fromRoot.AppState>, private router: Router) {
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
  }

  @ViewChild('graph', {read: ElementRef})graph: ElementRef;

  @Input() commits: Commit[];
  @Input() project: Project;
  @Input() branches: Branch[];

  public selectedCommit1: Commit;
  public selectedCommit2: Commit;

  private selectedCommit1Element: GitGraphCommit;
  private selectedCommit2Element: GitGraphCommit;

  private loadIndex = 15;
  private windowHeight = window.screen.availHeight;

  private gitgraph: GitgraphUserApi<SVGElement>;

  private selectedCommitColor = '#f60';
  private deselectedCommitColor = '#979797';

  public startDate: string = null;
  public endDate: string = null;

  master: BranchUserApi<SVGElement>;

  ngOnInit() {
    //this.commits = this.commits.filter(commit => commit.analyzed);
    if (this.commits.length === 0) {
      return;
    }
    this.gitgraph = createGitgraph(this.graph.nativeElement, {orientation: Orientation.VerticalReverse});
    this.master = this.gitgraph.branch( 'master');

    for (let i = 0; i < this.loadIndex && i < this.commits.length; i++) {
/*      if (i === 7) { // dummy commits
        const newFeature = this.gitgraph.branch('new-feature');
        newFeature.commit('Implemented an awesome feature');
        newFeature.commit('Fix tests');

        const newBranch = this.gitgraph.branch('fixes');
        newBranch.commit('Implemented something');
        newBranch.commit('Fix something');

        this.master.merge(newFeature, 'Release new version');
        this.master.merge(newBranch, 'Release new version');
      }*/
      const value = this.commits[i];
      this.master.commit({
        hash: value.name,
        subject: new Date(value.timestamp).toDateString(),
        author: value.author,
        onClick: commit => {
          this.handleClickEvent(commit);
        },
        onMessageClick: commit => {
          this.handleClickEvent(commit);
        }
      });
    }
  }

  public handleClickEvent(commitElement: GitGraphCommit): void {

    const selectedCommit = this.commits.find(value1 => value1.name === commitElement.hash);

    if (this.selectedCommit1 === null && this.selectedCommit2 !== selectedCommit) {
      this.selectedCommit1 = selectedCommit;

      if (this.selectedCommit1Element !== undefined) {
        this.selectedCommit1Element.style.message.color = this.deselectedCommitColor;
        this.selectedCommit1Element.style.color = this.deselectedCommitColor;
        this.selectedCommit1Element.style.dot.color = this.deselectedCommitColor;
      }

      this.selectedCommit1Element = commitElement;

      commitElement.style.message.color = this.selectedCommitColor;
      commitElement.style.color = this.selectedCommitColor;
      commitElement.style.dot.color = this.selectedCommitColor;

    } else if (this.selectedCommit1 === selectedCommit) {
      this.selectedCommit1 = null;

      commitElement.style.message.color = this.deselectedCommitColor;
      commitElement.style.color = this.deselectedCommitColor;
      commitElement.style.dot.color = this.deselectedCommitColor;

    } else if (this.selectedCommit2 === selectedCommit) {
      this.selectedCommit2 = null;

      commitElement.style.message.color = this.deselectedCommitColor;
      commitElement.style.color = this.deselectedCommitColor;
      commitElement.style.dot.color = this.deselectedCommitColor;
    } else {
      this.selectedCommit2 = selectedCommit;

      if (this.selectedCommit2Element !== undefined) {
        this.selectedCommit2Element.style.message.color = this.deselectedCommitColor;
        this.selectedCommit2Element.style.color = this.deselectedCommitColor;
        this.selectedCommit2Element.style.dot.color = this.deselectedCommitColor;
      }

      this.selectedCommit2Element = commitElement;

      commitElement.style.message.color = this.selectedCommitColor;
      commitElement.style.color = this.selectedCommitColor;
      commitElement.style.dot.color = this.selectedCommitColor;
    }
    // @ts-ignore
    this.gitgraph._onGraphUpdate();
  }

  @HostListener('window:scroll', ['$event'])
  handleScroll() {
    if (window.pageYOffset > (this.windowHeight + window.screenY - 700)) {
      for (let i = this.loadIndex; i < this.loadIndex + 15 && i < this.commits.length; i++) {
        const value = this.commits[i];
        this.master.commit({
          hash: value.name,
          subject: new Date(value.timestamp).toDateString(),
          author: value.author,
          onClick: commit => {
            this.handleClickEvent(commit);
          },
          onMessageClick: commit => {
            this.handleClickEvent(commit);
          }
        });
      }
      this.loadIndex += 15;
      this.windowHeight = window.pageYOffset + window.screen.height * 1.5;
    }
  }

  showCommitsInRange() {

    let endDate: Date;
    if (this.endDate === null) {
      endDate = new Date(this.commits[0].timestamp);
    } else {
      endDate = new Date(this.endDate);
    }

    let startDate: Date;
    if (this.startDate === null) {
      startDate = new Date(this.commits[this.commits.length - 1].timestamp);
    } else {
      startDate = new Date(this.startDate);
    }

    const filteredCommits: Commit[] = this.commits.filter(value =>
      value.timestamp >= startDate.getTime() && value.timestamp <= endDate.getTime());

    this.gitgraph.clear();
    this.master = this.gitgraph.branch( 'master');

    filteredCommits.forEach(value => {
      this.master.commit({
        hash: value.name,
        subject: new Date(value.timestamp).toDateString(),
        author: value.author,
        onClick: commit => {
          this.handleClickEvent(commit);
        },
        onMessageClick: commit => {
          this.handleClickEvent(commit);
        }
      });
    });
    this.loadIndex = this.commits.length;
  }

  startCityView() {
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
    this.router.navigate(['/city/' + this.project.id]);
  }
}

