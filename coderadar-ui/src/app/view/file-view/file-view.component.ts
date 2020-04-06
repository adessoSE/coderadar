import { Component, OnInit } from '@angular/core';
import {MatTreeNestedDataSource} from '@angular/material';
import {NestedTreeControl} from '@angular/cdk/tree';
import {FileTreeNode} from '../../model/file-tree-node';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';

@Component({
  selector: 'app-file-view',
  templateUrl: './file-view.component.html',
  styleUrls: ['./file-view.component.css']
})
export class FileViewComponent implements OnInit {

  treeControl = new NestedTreeControl<FileTreeNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<FileTreeNode>();
  private projectId: any;
  public commitHash: any;
  public tree: FileTreeNode;

  constructor(private projectService: ProjectService,
              private router: Router,
              private userService: UserService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitHash = params.commitHash;
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
}
