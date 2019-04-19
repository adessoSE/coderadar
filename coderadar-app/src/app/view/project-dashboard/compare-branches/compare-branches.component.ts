import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {createGitgraph, Orientation} from '@gitgraph/js';
import {Commit} from '../../../model/commit';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {GitgraphUserApi} from '@gitgraph/core';


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

  public prevSelectedCommit1: Commit;
  public prevSelectedCommit2: Commit;

  private gitgraph: GitgraphUserApi<SVGElement>;

  private static extractCommitHash(markup: any): string {
    let result = '';
    if (markup.tagName === 'text') {
      for (let i = 0; markup.innerHTML.charAt(i) !== ' '; i++) {
        result += markup.innerHTML.charAt(i);
      }
      return result;
    } else if (markup.tagName === 'use') {
      for (let i = 1; i < 8; i++) {
        result += markup.attributes.getNamedItem('href').value.charAt(i);
      }
      return result;
    }
    return null;
  }

  ngOnInit() {
    this.gitgraph = createGitgraph(this.graph.nativeElement, {orientation: Orientation.VerticalReverse});
    this.redraw();
  }

  redraw() {

    this.gitgraph.clear();
    const master = this.gitgraph.branch('master');

    this.commits.forEach(value => {
      if (value === this.selectedCommit1 || value === this.selectedCommit2) {
        master.commit({
          hash: value.name,
          subject: new Date(value.timestamp).toDateString(),
          author: value.author,
          style: {
            dot: {
              color: '#f60',
            },
            message: {
              color: '#f60'
            },
          }
        });
      } else {
        master.commit({
          hash: value.name,
          subject: new Date(value.timestamp).toDateString(),
          author: value.author
        });
      }
    });

    const newFeature = this.gitgraph.branch('new-feature');
    newFeature.commit('Implement an awesome feature');
    newFeature.commit('Fix tests');

    const newBranch = this.gitgraph.branch('fixes');
    newBranch.commit('Implemented someting');
    newBranch.commit('Fix something');

    master.merge(newFeature, 'Release new version');
    master.merge(newBranch, 'Release new version');
  }

  public selectCommit(selectedCommitHash: EventTarget): void {
    if (!(selectedCommitHash instanceof SVGTextElement) &&
      !(selectedCommitHash instanceof SVGUseElement)) {
      return;
    }
    const hash = CompareBranchesComponent.extractCommitHash(selectedCommitHash);
    if (hash !== null) {
      const selectedCommit = this.commits.find(value1 => value1.name.slice(0, 7) === hash);
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
      this.redraw();
    }
  }
}

