import { Component, OnInit } from '@angular/core';
import {Team} from "../../model/team";
import {TeamService} from "../../service/team.service";
import {UserService} from "../../service/user.service";
import {FORBIDDEN} from "http-status-codes";

@Component({
  selector: 'app-teams-dashboard',
  templateUrl: './teams-dashboard.component.html',
  styleUrls: ['./teams-dashboard.component.css']
})
export class TeamsDashboardComponent implements OnInit {
  waiting = false;

  teams: Team[] = [];

  constructor(private teamService: TeamService, private userService: UserService) { }

  ngOnInit() {
    this.getTeams();
  }

  private getTeams() {
    this.teamService.listTeamsForUser(UserService.getLoggedInUser().userId).then(value =>
      this.teams = value.body
    ) .catch(e => {
      if (e.status && e.status === FORBIDDEN) { //TODO: UNAUTHORIZED
        this.userService.refresh(() => this.getTeams());
      }
    });
  }

}
