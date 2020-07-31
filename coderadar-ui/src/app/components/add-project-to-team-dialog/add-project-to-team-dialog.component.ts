import {Component, Inject} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AddProjectToTeamDialogData} from './add-project-to-team-dialog-data';

@Component({
  selector: 'app-add-project-to-team-dialog',
  templateUrl: 'add-project-to-team-dialog.html'
})
export class AddProjectToTeamDialogComponent {

  selectedTeams: FormControl = new FormControl();

  constructor(
    public dialogRef: MatDialogRef<AddProjectToTeamDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddProjectToTeamDialogData
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    this.data.teams = this.selectedTeams.value;
  }

}
