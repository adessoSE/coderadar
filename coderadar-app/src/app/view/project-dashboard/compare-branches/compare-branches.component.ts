import {Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import {createGitgraph, Orientation} from '@gitgraph/js';
import {Commit} from '../../../model/commit';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {BranchUserApi, GitgraphUserApi} from '@gitgraph/core';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import {Observable} from 'rxjs';


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
  private selectedCommit1Circle: SVGCircleElement;
  private selectedCommit1Text: SVGTextElement;

  public selectedCommit2: Commit;
  private selectedCommit2Circle: SVGCircleElement;
  private selectedCommit2Text: SVGTextElement;

  public prevSelectedCommit1: Commit;
  private prevSelectedCommit1Circle: SVGCircleElement;
  private prevSelectedCommit1Text: SVGTextElement;

  public prevSelectedCommit2: Commit;
  private prevSelectedCommit2Circle: SVGCircleElement;
  private prevSelectedCommit2Text: SVGTextElement;

  private loadIndex = 15;
  private windowHeight = window.screen.availHeight;

  private gitgraph: GitgraphUserApi<SVGElement>;

  private selectedCommitColor = '#f60';
  private deselectedCommitColor = '#979797';

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

  ngOnInit() {
    this.gitgraph = createGitgraph(this.graph.nativeElement, {orientation: Orientation.VerticalReverse});
    this.redraw();
  }

  redraw() {

    this.gitgraph.clear();
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
      if (value === this.selectedCommit1 || value === this.selectedCommit2) {
        this.master.commit({
          hash: value.name,
          subject: new Date(value.timestamp).toDateString(),
          author: value.author,
          style: {
            dot: {
              color: this.selectedCommitColor
            },
            message: {
              color: this.selectedCommitColor
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

  public handleClickEvent(selectedCommitHash: EventTarget): void {
    let selectedCommitHashSVGCircle: SVGCircleElement;
    let selectedCommitHashSVGText: SVGTextElement;

    if (selectedCommitHash instanceof SVGUseElement) {
      selectedCommitHashSVGCircle = selectedCommitHash.parentElement.getElementsByTagName('defs').item(0)
        .getElementsByTagName('circle').item(0);
      selectedCommitHashSVGText = selectedCommitHash.parentElement.parentElement.getElementsByTagName('g').item(1)
        .getElementsByTagName('text').item(0);

      selectedCommitHashSVGText.style.fill = this.selectedCommitColor;
      selectedCommitHashSVGCircle.style.fill = this.selectedCommitColor;

    } else if (selectedCommitHash instanceof SVGTextElement) {
      selectedCommitHashSVGText = selectedCommitHash;
      selectedCommitHashSVGCircle = selectedCommitHash.parentElement.parentElement.parentElement.getElementsByTagName('g').item(0)
        .getElementsByTagName('defs').item(0).getElementsByTagName('circle').item(0);

      selectedCommitHashSVGCircle.style.fill = this.selectedCommitColor;
      selectedCommitHashSVGText.style.fill = this.selectedCommitColor;

    } else {
      return;
    }


    const hash = CompareBranchesComponent.extractCommitHash(selectedCommitHash);
    if (hash !== null) {
      const selectedCommit = this.commits.find(value1 => value1.name.slice(0, 7) === hash);

      if (this.selectedCommit1 === null && this.selectedCommit2 !== selectedCommit) {
        if (this.selectedCommit1Text !== undefined) {
          this.selectedCommit1Circle.style.fill = this.deselectedCommitColor;
          this.selectedCommit1Text.style.fill = this.deselectedCommitColor;
        }
        this.selectedCommit1 = selectedCommit;
        this.selectedCommit1Circle = selectedCommitHashSVGCircle;
        this.selectedCommit1Text = selectedCommitHashSVGText;
        this.selectedCommit1Circle.style.fill = this.selectedCommitColor;
        this.selectedCommit1Text.style.fill = this.selectedCommitColor;

      } else if (this.selectedCommit1 === selectedCommit) {
        this.selectedCommit1 = null;
        this.selectedCommit1Circle.style.fill = this.deselectedCommitColor;
        this.selectedCommit1Text.style.fill = this.deselectedCommitColor;

        this.prevSelectedCommit1 = selectedCommit;
        this.prevSelectedCommit1Circle = selectedCommitHashSVGCircle;
        this.prevSelectedCommit1Text = selectedCommitHashSVGText;

      } else if (this.selectedCommit2 === selectedCommit) {
        this.selectedCommit2 = null;
        this.selectedCommit2Circle.style.fill = this.deselectedCommitColor;
        this.selectedCommit2Text.style.fill = this.deselectedCommitColor;

        this.prevSelectedCommit2 = selectedCommit;
        this.prevSelectedCommit2Circle = selectedCommitHashSVGCircle;
        this.prevSelectedCommit2Text = selectedCommitHashSVGText;

      } else {
        if (this.selectedCommit2Text !== undefined) {
          this.selectedCommit2Circle.style.fill = this.deselectedCommitColor;
          this.selectedCommit2Text.style.fill = this.deselectedCommitColor;
        }
        this.selectedCommit2 = selectedCommit;
        this.selectedCommit2Circle = selectedCommitHashSVGCircle;
        this.selectedCommit2Text = selectedCommitHashSVGText;
        this.selectedCommit2Circle.style.fill = this.selectedCommitColor;
        this.selectedCommit2Text.style.fill = this.selectedCommitColor;
      }

      this.cityEffects.firstCommit = this.selectedCommit1;
      this.cityEffects.secondCommit = this.selectedCommit2;
    }
  }

  @HostListener('window:scroll', ['$event'])
  handleScroll(event) {
    if (window.pageYOffset > (this.windowHeight + window.screenY - 700)) {
      for (let i = this.loadIndex; i < this.loadIndex + 15 && i < this.commits.length; i++) {
        const value = this.commits[i];
        this.master.commit({
            hash: value.name,
            subject: new Date(value.timestamp).toDateString(),
            author: value.author
          });
      }
      this.loadIndex += 15;
      this.windowHeight = window.pageYOffset + window.screen.height * 1.5;
    }
    const elementsText = document.getElementsByTagName('g').item(0).getElementsByTagName('g').item(1).getElementsByTagName('text');
    const elementsCircle = document.getElementsByTagName('g').item(0).getElementsByTagName('g').item(1).getElementsByTagName('defs');

    if (this.selectedCommit1 !== null) {
      this.selectedCommit1Circle = elementsCircle.item(this.commits.indexOf(this.selectedCommit1)).getElementsByTagName('circle').item(0);
      this.selectedCommit1Text =  elementsText.item(this.commits.indexOf(this.selectedCommit1));
      this.selectedCommit1Circle.style.fill = this.selectedCommitColor;
      this.selectedCommit1Text.style.fill = this.selectedCommitColor;
    }
    if (this.selectedCommit2 !== null) {
      this.selectedCommit2Circle = elementsCircle.item(this.commits.indexOf(this.selectedCommit2)).getElementsByTagName('circle').item(0);
      this.selectedCommit2Text =  elementsText.item(this.commits.indexOf(this.selectedCommit2));
      this.selectedCommit2Circle.style.fill = this.selectedCommitColor;
      this.selectedCommit2Text.style.fill = this.selectedCommitColor;
    }
  }
}

