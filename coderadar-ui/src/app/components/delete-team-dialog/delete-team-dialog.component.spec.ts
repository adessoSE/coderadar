import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTeamDialogComponent } from './delete-team-dialog.component';

describe('DeleteProjectDialogComponent', () => {
  let component: DeleteTeamDialogComponent;
  let fixture: ComponentFixture<DeleteTeamDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteTeamDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteTeamDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
