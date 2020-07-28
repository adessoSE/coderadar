import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {DialogData} from '../../view/configure-project/configure-project.component';

@Component({
  selector: 'app-contributor-merge-dialog',
  templateUrl: './contributor-merge-dialog.component.html'
})
export class ContributorMergeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ContributorMergeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
