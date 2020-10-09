import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {User} from '../../model/user';
import {FORBIDDEN} from 'http-status-codes';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {

  users: User[] = [];
  waiting = false;

  constructor(private userService: UserService, private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.getUsers();
  }

  private getUsers() {
    this.userService.listUsers().then(value => {
      this.users = value.body;
    }).catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh(() => this.getUsers());
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  setPermission(id: number, isAdmin: boolean) {
    if (!isAdmin && id === UserService.getLoggedInUser().userId) {
      if (this.users.filter(value => value.platformAdmin).length <= 1) {
        this.openSnackBar('There must always be at least one platform admin!', '🞩');
        return;
      }
    }

    this.userService.setUserPlatformPermission(id, isAdmin)
      .then(value => {
        this.getUsers();
        if (!isAdmin && id === UserService.getLoggedInUser().userId) {
          this.router.navigate(['/login']);
        }
      })
      .catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh(() => this.setPermission(id, isAdmin));
      }
    });
  }

  deleteUser(userId: number) {
    if (userId === UserService.getLoggedInUser().userId) {
      if (this.users.filter(value => value.platformAdmin).length <= 1) {
        this.openSnackBar('There must always be at least one platform admin!', '🞩');
        return;
      }
    }

    this.waiting = true;
    this.userService.deleteUser(userId)
      .then(() => {
        this.getUsers();
        this.waiting = false;
        if (userId === UserService.getLoggedInUser().userId) {
          this.userService.logout();
          this.router.navigate(['/login']);
        }
      })
      .catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh(() => this.deleteUser(userId));
      }
    });
  }
}
