import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Team} from '../../model/team';
import {User} from '../../model/user';
import {UserService} from '../../service/user.service';
import {TeamService} from '../../service/team.service';
import {CONFLICT, FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {ActivatedRoute, Router} from '@angular/router';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-edit-team',
  templateUrl: './edit-team.component.html',
  styleUrls: ['./edit-team.component.css']
})
export class EditTeamComponent implements OnInit {

  usersFormControl = new FormControl();
  team: Team = new Team();
  users: User[] = [];
  nameEmpty = false;
  teamNameTaken = false;
  noUsers: boolean;
  teamId: number;
  teamName = '';

  constructor(private userService: UserService, private teamService: TeamService,
              private router: Router, private route: ActivatedRoute,
              private titleService: Title) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params.id;
      this.getAllUsers();
      this.getTeam();
    });
  }

  private getAllUsers() {
    this.userService.listUsers().then(value => {
      this.users = value.body;
      this.users = this.users.filter(value1 => value1.id !== UserService.getLoggedInUser().userId);
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.getAllUsers());
      } else if (error.status && error.status === NOT_FOUND) {
        this.router.navigate(['/teams']);
      }
    });
  }

  submitForm() {
    this.nameEmpty = this.team.name === undefined || this.team.name.length === 0;
    this.noUsers = this.usersFormControl.value === null || this.usersFormControl.value.length === 0;
    if (!this.nameEmpty && !this.noUsers) {
      this.usersFormControl.value.push(UserService.getLoggedInUser().userId);
      this.teamService.editTeam(this.teamId, this.team.name, this.usersFormControl.value).then(() =>
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

  private getTeam() {
    this.teamService.getTeam(this.teamId)
      .then(response => {
        this.team = response.body;
        this.titleService.setTitle('Coderadar - Edit ' + this.team.name);
        this.teamName = this.team.name;
        this.usersFormControl.setValue(this.team.members.map(value => value.id));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getTeam());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/teams']);
        }
      });
  }
}
