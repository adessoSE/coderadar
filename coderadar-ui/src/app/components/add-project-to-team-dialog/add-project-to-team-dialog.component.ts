import {Component, Inject, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AddProjectToTeamDialogData} from './add-project-to-team-dialog-data';
import {ProjectRole} from '../../model/project-role';

@Component({
  selector: 'app-add-project-to-team-dialog',
  templateUrl: 'add-project-to-team-dialog.html'
})
export class AddProjectToTeamDialogComponent implements OnInit {

  selectedTeams: FormControl = new FormControl();
  roleSelected = true;

  constructor(
    public dialogRef: MatDialogRef<AddProjectToTeamDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddProjectToTeamDialogData
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    this.roleSelected = this.data.role !== undefined;
    if (this.roleSelected) {
      this.data.teams = this.selectedTeams.value;
      this.dialogRef.close(this.data);
    }
  }

  ngOnInit(): void {
    this.data.teams = this.data.teams.filter(t1 => this.data.teamsForProject.find(
      t2 => t2.name === t1.name) === undefined);
    this.data.teamsForProject.forEach(value => this.data.teams.push(value));
    this.selectedTeams.setValue(this.data.teamsForProject);
  }

}
