import { Component, OnInit } from '@angular/core';
import { ProjectService } from 'src/app/service/project.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Project } from 'src/app/model/project';
import { FORBIDDEN } from 'http-status-codes';

@Component({
  selector: 'app-goal-configuration',
  templateUrl: './goal-configuration.component.html',
  styleUrls: ['./goal-configuration.component.css']
})
export class GoalConfigurationComponent implements OnInit {
  conditions = [];
  selectedMetric: string;
  selectedCondition: string;
  availableMetrics: string[] = [];
  projectId;
  dataSource;
  fileMetricSubsciption: Subscription;

  constructor(private projectService: ProjectService, private route: ActivatedRoute) { }

  ngOnInit() {
  }

}
