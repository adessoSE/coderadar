import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {HttpClient} from '@angular/common/http';
import {AppComponent} from '../../app.component';
import {FORBIDDEN} from 'http-status-codes';
import {MatDialogRef} from '@angular/material/dialog';
import {ShutdownDialogComponent} from '../../components/shutdown-dialog/shutdown-dialog.component';
import {MatDialog} from '@angular/material';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sidenav-content',
  templateUrl: './sidenav-content.component.html'
})
export class SidenavContentComponent implements OnInit {
  userIsPlatformAdmin = UserService.getLoggedInUser().platformAdmin;
  dialogRef: MatDialogRef<ShutdownDialogComponent>;

  constructor(private httpClient: HttpClient, private userService: UserService, private dialog: MatDialog,
              private router: Router) { }

  ngOnInit() {}

  openShutdownDialog() {
    this.dialogRef = this.dialog.open(ShutdownDialogComponent);

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.shutdown();
      }
    });
  }

  shutdown() {
    if (this.userIsPlatformAdmin === true) {
      this.httpClient.get(AppComponent.getApiUrl() + 'shutdown').toPromise()
        .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.shutdown());
        }
      });
      this.router.navigate(['/login']);
    }
  }
}
