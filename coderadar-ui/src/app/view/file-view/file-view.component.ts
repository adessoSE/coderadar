import {AfterViewChecked, Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {MatTooltip, MatTreeNestedDataSource} from '@angular/material';
import {NestedTreeControl} from '@angular/cdk/tree';
import {FileTreeNode} from '../../model/file-tree-node';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {MetricWithFindings} from '../../model/metric-with-findings';
import * as Prism from 'prismjs';

import 'prismjs/components/prism-java';
import 'prismjs/components/prism-markdown';
import 'prismjs/components/prism-asciidoc';
import 'prismjs/components/prism-bash';
import 'prismjs/components/prism-css';
import 'prismjs/components/prism-typescript';
import 'prismjs/components/prism-scss';
import 'prismjs/components/prism-json';
import 'prismjs/components/prism-groovy';
import 'prismjs/components/prism-diff';
import 'prismjs/plugins/diff-highlight/prism-diff-highlight';
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-highlight/prism-line-highlight';
import {Project} from '../../model/project';
import {AppComponent} from '../../app.component';
import {Title} from '@angular/platform-browser';
import {ContributorService} from '../../service/contributor.service';
import {Contributor} from '../../model/contributor';
import {HttpResponse} from '@angular/common/http';
import {FileContentWithMetrics} from '../../model/file-content-with-metrics';

@Component({
  selector: 'app-file-view',
  templateUrl: './file-view.component.html',
  styleUrls: ['./file-view.component.css']
})
export class FileViewComponent implements OnInit, AfterViewChecked {

  @ViewChild('fileView', {read: ElementRef})fileView: ElementRef;
  @ViewChild('tooltip') tooltip: MatTooltip;
  tooltipMessage = '';

  treeControl = new NestedTreeControl<FileTreeNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<FileTreeNode>();
  public projectId: number;
  public commitHash: string;
  public commitHashAbbrev: string;
  public tree: FileTreeNode = null;
  public prevTree: FileTreeNode = null;
  public currentFileContent = '';
  public currentFileMetrics: MetricWithFindings[] = [];
  public currentSelectedFilepath = '';
  public findingsString = '';
  public project: Project = new Project();
  public highlighted = false;
  public showOnlyChangedFiles = false;
  public showDiff = false;
  public fileAuthors: Contributor[] = [];

  constructor(private projectService: ProjectService,
              private contributorService: ContributorService,
              private router: Router,
              private userService: UserService,
              private route: ActivatedRoute,
              private titleService: Title) {
  }

  ngOnInit() {
    this.project.name = '';
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitHash = params.commitHash;
      this.commitHashAbbrev = this.commitHash.substr(0, 7);
      this.getProject();
      this.getFileTree();
    });
  }

  hasChild = (_: number, node: FileTreeNode) => node.children !== null;

  public getFileTree() {
    if (!this.showOnlyChangedFiles) {
      this.showDiff = false;
      this.updateSelectedFile(null, this.currentSelectedFilepath);
    }
    if (this.tree != null && this.prevTree != null) {
      const temp = this.tree;
      this.tree = this.prevTree;
      this.prevTree = temp;
      this.dataSource.data = this.tree.children;
      return;
    }
    this.projectService.getFileTree(this.projectId, this.commitHash, this.showOnlyChangedFiles).then(result => {
      this.prevTree = this.tree;
      this.tree = result.body;
      this.dataSource.data = result.body.children;
    }).catch(err => {
      if (err.status && err.status === FORBIDDEN) {
        this.userService.refresh(() => this.getFileTree());
      } else if (err.status && err.status === NOT_FOUND) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  public updateSelectedFile(node: any, path: string): void {
    this.highlighted = false;
    this.currentFileContent = '';
    if (node === null && path === null) {
      return;
    }
    if (path === null) {
      this.currentSelectedFilepath = this.getFullPath(this.tree.children, node, '');
      this.currentSelectedFilepath = this.currentSelectedFilepath.substr(1, this.currentSelectedFilepath.length);
    } else {
      this.currentSelectedFilepath = path;
    }
    if (this.currentSelectedFilepath === '') {
      return;
    }
    let promise: Promise<HttpResponse<FileContentWithMetrics>> ;
    if (this.showDiff) {
      promise = this.projectService.getFileDiff(this.projectId, this.commitHash, this.currentSelectedFilepath);
    } else {
      promise = this.projectService.getFileContentWithMetrics(this.projectId, this.commitHash, this.currentSelectedFilepath);
    }
    promise.then(value => {
        this.currentFileContent = value.body.content;
        this.currentFileMetrics = value.body.metrics;
        this.findingsString = this.getAllFindings(this.currentFileMetrics);
      }).catch(err => {
      if (err.status && err.status === FORBIDDEN) {
        this.userService.refresh(() => this.updateSelectedFile(node, path));
      } else if (err.status && err.status === NOT_FOUND) {
        this.router.navigate(['/dashboard']);
      }
    });


    this.contributorService.getContributorsForFile(this.projectId, this.currentSelectedFilepath, this.commitHash)
      .then(value => {
        this.fileAuthors = value.body;
      })
      .catch(err => {
        if (err.status && err.status === FORBIDDEN) {
          this.userService.refresh(() => this.updateSelectedFile(node, path));
        } else if (err.status && err.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    if (this.showDiff) {
      this.fileView.nativeElement.children.item(0).lastChild.remove();
    } else {
      this.fileView.nativeElement.children.item(1).remove();
    }
  }


  private getFullPath(children: FileTreeNode[], node: any, path: string): string {
    if (children === null) {
      return '';
    }
    for (const value of children) {
      if (value === node) {
        return path + '/' + value.path;
      } else {
        const newPath = this.getFullPath(value.children, node, path + '/' + value.path);
        if (newPath !== '') {
          return newPath;
        }
      }
    }
    return '';
  }

  ngAfterViewChecked(): void {
    if (!this.highlighted && this.currentFileContent !== '') {
      Prism.highlightAllUnder(this.fileView.nativeElement);
      this.onResize(null);
      const elements: HTMLElement[] = Array.from(this.fileView.nativeElement.children)
          .slice(1, this.fileView.nativeElement.children.length) as HTMLElement[];
      elements.forEach(value => {
        value.style.pointerEvents = 'auto';
        value.onmouseover = (event: MouseEvent) => {
          this.tooltipMessage = this.getTooltipForRange(value.attributes['data-range']);
          this.tooltip.disabled = false;
          this.tooltip.show();
          if (this.tooltip._overlayRef) {
            const tip = this.tooltip._overlayRef.overlayElement;
            if (tip) {
              setTimeout(() => {
                tip.style.left = event.clientX + 'px';
                tip.style.top = event.clientY + 'px';
              });
            }
           }
        };
        value.onmouseleave = () => {
          this.tooltip.hide();
        };
      });
      this.highlighted = true;
    }
  }

  private getTooltipForRange(range: Attr): string {
    const lineStart = +range.value.split('-')[0];
    let findings = '';
    this.currentFileMetrics.forEach(value => {
      const found = false;
      for (const finding of value.findings) {
        if (finding.lineStart === lineStart) {
          findings += finding.message + '\n';
          break;
        }
      }
    });
    return findings;
  }

  getCodeClass() {
    const temp = this.currentSelectedFilepath.split('.');
    const fileExtension = temp[temp.length - 1];
    if (this.showDiff) {
      if (fileExtension === 'gradle') {
        return 'language-diff-groovy diff-highlight';
      }
      return 'language-diff-' + fileExtension + ' diff-highlight';
    } else {
      if (fileExtension === 'gradle') {
        return 'line-numbers language-groovy';
      }
      return 'language-' + fileExtension;
    }
  }

  getAllFindings(metrics: MetricWithFindings[]) {
    if (metrics === null) {
      return '0';
    }
    let result = '';
    for (const m of metrics) {
      m.findings.forEach(value => result += value.lineStart + '-' + value.lineEnd + ',');
    }
    return result.length === 0 ? '0' : result.substr(0, result.length - 1);
  }


  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(this.project.name));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  getPreClass() {
    if (!this.showDiff) {
      return 'line-numbers file-content';
    }  else {
      return 'file-content';
    }
  }
}
