import { Component, OnInit, Inject } from '@angular/core';
import { ProjectService } from 'src/app/service/project.service';
import { ActivatedRoute } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

export interface ConfigurationData {
  projectId: any;
  availableMetrics: [];
}

@Component({
  selector: 'app-hotspot-configuration',
  templateUrl: './hotspot-configuration.component.html',
  styleUrls: ['./hotspot-configuration.component.css']
})
export class HotspotConfigurationComponent implements OnInit {
  metrics: string[] = [];
  selectedMetrics: string[] = ['0', '1', '2'];

  constructor(public dialogRef: MatDialogRef<HotspotConfigurationComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ConfigurationData) { }

  ngOnInit() {
    this.metrics = this.data.availableMetrics;
  }

  public addMetric() {
    this.selectedMetrics.push('' + this.selectedMetrics.length);
  }

  public save() {
    this.dialogRef.close(this.selectedMetrics);
  }

  public close() {
    this.dialogRef.close();
  }

}