import {Contributor} from '../../model/contributor';
import {Component, ElementRef, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

interface ContributorDialogData {
  contributor: Contributor;
}


@Component({
  selector: 'app-contributor-dialog',
  templateUrl: './contributor-dialog.html',
  styleUrls: ['contributor-dialog.css']
})
export class ContributorDialogComponent implements OnInit {

  contributor: Contributor;
  avatarUrl: string;

  constructor(
    public hostElement: ElementRef,
    public dialogRef: MatDialogRef<ContributorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ContributorDialogData
  ) {
    this.contributor = data.contributor;
  }

  getAliases(): string[] {
    return this.contributor.names.filter(value => value !== this.contributor.displayName);
  }

  ngOnInit() {
  }

}
