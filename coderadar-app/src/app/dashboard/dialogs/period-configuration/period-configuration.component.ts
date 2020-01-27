import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-period-configuration',
  templateUrl: './period-configuration.component.html',
  styleUrls: ['./period-configuration.component.css']
})
export class PeriodConfigurationComponent implements OnInit {
  startDate = new FormControl(new Date());
  endDate = new FormControl(new Date());

  constructor(public dialogRef: MatDialogRef<PeriodConfigurationComponent>) { }

  close() {
    this.dialogRef.close();
  }

  save() {
    const dialogResult = {startDate: this.startDate, endDate: this.endDate};
    this.dialogRef.close(dialogResult);
  }

  ngOnInit() {
  }

}
