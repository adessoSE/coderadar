import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "./merge-dialog-data";

@Component({
  selector: 'app-dialog-overview-example-dialog',
  templateUrl: 'merge-dialog.component.html',
})
export class MergeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<MergeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
