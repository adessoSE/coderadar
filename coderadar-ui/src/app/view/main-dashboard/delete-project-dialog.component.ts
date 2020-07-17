import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Project} from "../../model/project";

class DeleteProjectDialogData {
  project: Project;
}

@Component({
  selector: 'app-delete-project-dialog',
  templateUrl: 'delete-project-dialog.html'
})
export class ConfirmDeleteProjectDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteProjectDialogData
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
