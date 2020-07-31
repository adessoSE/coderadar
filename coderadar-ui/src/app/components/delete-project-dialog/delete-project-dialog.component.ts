import {Project} from '../../model/project';
import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

class DeleteProjectDialogData {
  project: Project;
}

@Component({
  selector: 'app-delete-project-dialog',
  templateUrl: './delete-project-dialog.component.html'
})
export class DeleteProjectDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteProjectDialogData
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
