import { Component, OnInit } from '@angular/core';
import {Team} from '../../model/team';
import {TeamService} from '../../service/team.service';
import {UserService} from '../../service/user.service';
import {FORBIDDEN} from 'http-status-codes';
import {Project} from '../../model/project';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DeleteTeamDialogComponent} from '../../components/delete-team-dialog/delete-team-dialog.component';

@Component({
  selector: 'app-teams-dashboard',
  templateUrl: './teams-dashboard.component.html',
  styleUrls: ['./teams-dashboard.component.css']
})
export class TeamsDashboardComponent implements OnInit {
  waiting = false;
  dialogRef: MatDialogRef<DeleteTeamDialogComponent>;
  teams: Team[] = [];

  constructor(private teamService: TeamService, private userService: UserService, private dialog: MatDialog) { }

  ngOnInit() {
    this.getTeams();
  }

  private getTeams() {
    this.teamService.listTeamsForUser(UserService.getLoggedInUser().userId).then(value =>
      this.teams = value.body
    ) .catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh(() => this.getTeams());
      }
    });
  }

  openProjectDeletionDialog(teamToBeDeleted: Project) {
    this.dialogRef = this.dialog.open(DeleteTeamDialogComponent, {
      data: {
        team: teamToBeDeleted
      }
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteTeam(result);
      }
    });
  }

  private deleteTeam(team: Team) {
    this.teamService.deleteTeam(team.id)
      .then(() => {
        const index = this.teams.indexOf(team, 0);
        if (this.teams.indexOf(team, 0) > -1) {
          this.teams.splice(index, 1);
        }
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.deleteTeam(team));
        }
      });
  }
}
