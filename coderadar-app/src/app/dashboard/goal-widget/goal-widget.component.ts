import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpfetcherService } from '../services/httpfetcher.service';
import { IGoalResponse, GoalResponse } from '../interfaces/goal';
import { GoalConfigurationComponent } from '../dialogs/goal-configuration/goal-configuration.component';

@Component({
  selector: 'app-goal-widget',
  templateUrl: './goal-widget.component.html',
  styleUrls: ['./goal-widget.component.scss']
})
export class GoalWidgetComponent implements OnInit, OnDestroy {
  goalIsSuccessful = true;
  configDialog: MatDialogRef<GoalConfigurationComponent, any>;

  constructor(public dialog: MatDialog, private service: HttpfetcherService) { }

  ngOnInit() {
  }

  ngOnDestroy() {
  }

  openGoalConfigurationDialog(): void {
    this.configDialog = this.dialog.open(GoalConfigurationComponent, {});
  }
}
