import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AppEffects} from '../../city-map/shared/effects';

@Component({
  selector: 'app-city-view',
  templateUrl: './city-view.component.html',
  styleUrls: ['./city-view.component.scss']
})
export class CityViewComponent implements OnInit {

  constructor(private route: ActivatedRoute, private cityEffects: AppEffects) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.cityEffects.currentProjectId = params.id;
    });
  }

}
