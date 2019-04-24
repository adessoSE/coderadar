import {AfterViewInit, Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import {createGitgraph, Orientation} from '@gitgraph/js';
import {Commit} from '../../../model/commit';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {GitgraphUserApi, BranchUserApi} from '@gitgraph/core';
import {CdkScrollable, ScrollDispatcher} from '@angular/cdk/scrolling';
import {MatSidenavContainer, ScrollDirection} from '@angular/material';
import {Directionality} from '@angular/cdk/bidi';


@Component({
  selector: 'app-compare-branches',
  templateUrl: './compare-branches.component.html',
  styleUrls: ['./compare-branches.component.scss']
})
export class CompareBranchesComponent implements OnInit, AfterViewInit {


  constructor(private cityEffects: AppEffects,  private scrollDispatcher: ScrollDispatcher) {
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

  private loadIndex = 15;
  private windowHeight = window.screen.availHeight;

  private gitgraph: GitgraphUserApi<SVGElement>;

  master: BranchUserApi<SVGElement>;

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

  ngAfterViewInit() {
/*    this.scrollDispatcher.scrolled().subscribe(x => console.log(this.sidenavContainer.scrollable.measureScrollOffset('start')));
    this.sidenavContainer.scrollable.elementScrolled().subscribe(x => {
      console.log(x);
    });*/
  }

  ngOnInit() {
    this.gitgraph = createGitgraph(this.graph.nativeElement, {orientation: Orientation.VerticalReverse});
    this.redraw();

  }

  redraw() {

    this.gitgraph.clear();
    this.master = this.gitgraph.branch('master');

    for (let i = 0; i < this.loadIndex && i < this.commits.length; i++) {
      if (i === 7) { // dummy commits
        const newFeature = this.gitgraph.branch('new-feature');
        newFeature.commit('Implement an awesome feature');
        newFeature.commit('Fix tests');

        const newBranch = this.gitgraph.branch('fixes');
        newBranch.commit('Implemented someting');
        newBranch.commit('Fix something');

        this.master.merge(newFeature, 'Release new version');
        this.master.merge(newBranch, 'Release new version');
      }
      const value = this.commits[i];
      if (value === this.selectedCommit1 || value === this.selectedCommit2) {
        this.master.commit({
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
        this.master.commit({
          hash: value.name,
          subject: new Date(value.timestamp).toDateString(),
          author: value.author
        });
      }
    }


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

  @HostListener('window:scroll', ['$event'])
  handleScroll(event) {
    if (window.pageYOffset > (this.windowHeight + window.screenY - 700)) {
      for (let i = this.loadIndex; i < this.loadIndex + 15 && i < this.commits.length; i++) {
        const value = this.commits[i];
        if (value === this.selectedCommit1 || value === this.selectedCommit2) {
          this.master.commit({
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
          this.master.commit({
            hash: value.name,
            subject: new Date(value.timestamp).toDateString(),
            author: value.author
          });
        }
      }
      this.loadIndex += 15;
      this.windowHeight = window.pageYOffset + window.screen.height * 1.5;
    }

  }
}

