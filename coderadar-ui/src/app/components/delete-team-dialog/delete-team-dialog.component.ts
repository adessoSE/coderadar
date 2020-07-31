import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Team} from "../../model/team";

class DeleteTeamDialogData {
  team: Team;
}

@Component({
  selector: 'app-delete-team-dialog',
  templateUrl: './delete-team-dialog.component.html'
})
export class DeleteTeamDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteTeamDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteTeamDialogData
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
