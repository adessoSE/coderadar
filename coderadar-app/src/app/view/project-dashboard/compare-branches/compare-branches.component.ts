import {Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import {createGitgraph, Orientation} from '@gitgraph/js';
import {Commit} from '../../../model/commit';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {BranchUserApi, GitgraphUserApi} from '@gitgraph/core';
import {Commit as GitGraphCommit} from '@gitgraph/core';

@Component({
  selector: 'app-compare-branches',
  templateUrl: './compare-branches.component.html',
  styleUrls: ['./compare-branches.component.scss']
})
export class CompareBranchesComponent implements OnInit {

  constructor(private cityEffects: AppEffects) {
    this.selectedCommit1 = null;
    this.selectedCommit2 = null;
  }

  @ViewChild('graph', {read: ElementRef})graph: ElementRef;

  @Input() commits: Commit[];
  @Input() project: Project;

  public selectedCommit1: Commit;
  public selectedCommit2: Commit;

  selectedCommit1Element: GitGraphCommit;
  selectedCommit2Element: GitGraphCommit;

  private loadIndex = 15;
  private windowHeight = window.screen.availHeight;

  private gitgraph: GitgraphUserApi<SVGElement>;

  private selectedCommitColor = '#f60';
  private deselectedCommitColor = '#979797';

  master: BranchUserApi<SVGElement>;

  ngOnInit() {
    this.gitgraph = createGitgraph(this.graph.nativeElement, {orientation: Orientation.VerticalReverse});
    this.master = this.gitgraph.branch( 'master');

    for (let i = 0; i < this.loadIndex && i < this.commits.length; i++) {
      if (i === 7) { // dummy commits
        const newFeature = this.gitgraph.branch('new-feature');
        newFeature.commit('Implemented an awesome feature');
        newFeature.commit('Fix tests');

        const newBranch = this.gitgraph.branch('fixes');
        newBranch.commit('Implemented something');
        newBranch.commit('Fix something');

        this.master.merge(newFeature, 'Release new version');
        this.master.merge(newBranch, 'Release new version');
      }
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

      this.cityEffects.firstCommit = this.selectedCommit1;
      this.cityEffects.secondCommit = this.selectedCommit2;
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
}

