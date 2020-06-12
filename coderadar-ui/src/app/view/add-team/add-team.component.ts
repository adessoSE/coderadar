import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";
import {Team} from "../../model/team";
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {TeamService} from "../../service/team.service";
import {FORBIDDEN, NOT_FOUND} from "http-status-codes";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.css']
})
export class AddTeamComponent implements OnInit {

  usersFormControl = new FormControl();
  team: Team = new Team();
  users: User[] = [];
  nameEmpty = false;

  constructor(private userService: UserService, private teamService: TeamService, private router: Router) { }

  ngOnInit() {
    this.getAllUsers();
  }

  private getAllUsers() {
    this.userService.listUsers().then(value => {
      this.users = value.body;
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.getAllUsers());
      } else if (error.status && error.status === NOT_FOUND) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  submitForm() {
      this.nameEmpty = this.team.name == undefined || this.team.name.length === 0;
      if(!this.nameEmpty) {
          this.teamService.createTeam(this.team.name, this.usersFormControl.value).then(() =>
            this.router.navigate(['/teams']
          )).catch(error => {
              if (error.status && error.status === FORBIDDEN) {
                this.userService.refresh(() => this.getAllUsers());
              } else if (error.status && error.status === NOT_FOUND) {
                this.router.navigate(['/dashboard']);
              }
            }); //TODO: Identical team names
      }
  }
}
