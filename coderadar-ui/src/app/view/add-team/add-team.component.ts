import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Team} from '../../model/team';
import {User} from '../../model/user';
import {UserService} from '../../service/user.service';
import {TeamService} from '../../service/team.service';
import {CONFLICT, FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Router} from '@angular/router';
import {Title} from '@angular/platform-browser';

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
  teamNameTaken = false;
  noUsers: boolean;

  constructor(private userService: UserService, private teamService: TeamService, private router: Router,
              private titleService: Title) { }

  ngOnInit() {
    this.getAllUsers();
    this.titleService.setTitle('Coderadar - Add team');
  }

  private getAllUsers() {
    this.userService.listUsers().then(value => {
      this.users = value.body;
      this.users = this.users.filter(value1 => value1.id !== UserService.getLoggedInUser().userId);
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.getAllUsers());
      } else if (error.status && error.status === NOT_FOUND) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  submitForm() {
      this.nameEmpty = this.team.name === undefined || this.team.name.length === 0;
      this.noUsers = this.usersFormControl.value === null || this.usersFormControl.value.length === 0;
      if (!this.nameEmpty && !this.noUsers) {
          this.usersFormControl.value.push(UserService.getLoggedInUser().userId);
          this.teamService.createTeam(this.team.name, this.usersFormControl.value).then(() =>
            this.router.navigate(['/teams']
          )).catch(error => {
              if (error.status && error.status === FORBIDDEN) {
                this.userService.refresh(() => this.getAllUsers());
              } else if (error.status && error.status === CONFLICT) {
                this.teamNameTaken = true;
              }
            });
      }
  }
}
