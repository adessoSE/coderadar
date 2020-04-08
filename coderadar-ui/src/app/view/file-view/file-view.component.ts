import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material';
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
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-highlight/prism-line-highlight';
import {Project} from '../../model/project';
import {AppComponent} from '../../app.component';
import {Title} from '@angular/platform-browser';
import {element} from 'protractor';

@Component({
  selector: 'app-file-view',
  templateUrl: './file-view.component.html',
  styleUrls: ['./file-view.component.css']
})
export class FileViewComponent implements OnInit, AfterViewChecked {

  @ViewChild('fileView', {read: ElementRef})fileView: ElementRef;


  treeControl = new NestedTreeControl<FileTreeNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<FileTreeNode>();
  public projectId: number;
  public commitHash: string;
  public commitHashAbbrev: string;
  public tree: FileTreeNode;
  public currentFileContent = '';
  public currentFileMetrics: MetricWithFindings[] = [];
  public currentSelectedFilepath = '';
  public findingsString = '';
  public project: Project = new Project();

  constructor(private projectService: ProjectService,
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

  private getFileTree() {
    this.projectService.getFileTree(this.projectId, this.commitHash).then(result => {
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

  public updateSelectedFile(node: any): void {
    this.currentSelectedFilepath = this.getFullPath(this.tree.children, node, '');
    this.currentSelectedFilepath = this.currentSelectedFilepath.substr(1, this.currentSelectedFilepath.length);
    this.projectService.getFileContentWithMetrics(this.projectId, this.commitHash, this.currentSelectedFilepath)
      .then(value => {
        this.currentFileContent = value.body.content;
        this.currentFileMetrics = value.body.metrics;
        this.findingsString = this.getAllFindings(this.currentFileMetrics);
      }).catch(err => {
      if (err.status && err.status === FORBIDDEN) {
        this.userService.refresh(() => this.updateSelectedFile(node));
      } else if (err.status && err.status === NOT_FOUND) {
        this.router.navigate(['/dashboard']);
      }
    });
  }


  private getFullPath(children: FileTreeNode[], node: any, path: string): string {
    if (children === null) {
      return '';
    }
    for (const value of children) {
      if (value === node) {
        return path += '/' + value.path;
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
      Prism.highlightAllUnder(this.fileView.nativeElement);
  }

  getLanguageClass() {
    const temp = this.currentSelectedFilepath.split('.');
    const fileExtension = temp[temp.length - 1];
    if (fileExtension === 'gradle') {
      return 'language-groovy';
    }
    return 'language-' + fileExtension;
  }

  getAllFindings(metrics: MetricWithFindings[]) {
    let result = '';
    for (const m of metrics) {
      const findings = [];
      m.findings.forEach(value => result += value.lineStart + '-' + value.lineEnd + ',');
    }
    result.substr(0, result.length - 2);
    return result.length === 0 ? '0' : result;
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
}
