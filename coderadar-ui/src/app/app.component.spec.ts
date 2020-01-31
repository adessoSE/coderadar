import {TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {RouterTestingModule} from '@angular/router/testing';

describe('AppComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'coderadar'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('coderadar');
  });

  it('should trim project name', () => {
    expect(AppComponent.trimProjectName('projectName')).toBe('projectName');
    expect(AppComponent.trimProjectName('this is a really long project name which is longer than 50 characters'))
      .toBe('this is a really long project name which is longer...');
    expect(AppComponent.trimProjectName(null)).toBe('');
    expect(AppComponent.trimProjectName(undefined)).toBe('');
    expect(AppComponent.trimProjectName('')).toBe('');
  });

  it('should trim project name to length', () => {
    expect(AppComponent.trimProjectNameToLength('projectName', 12)).toBe('projectName');
    expect(AppComponent.trimProjectNameToLength('longProjectName', 12)).toBe('longProjectN...');
    expect(AppComponent.trimProjectNameToLength(null, 12)).toBe('');
    expect(AppComponent.trimProjectNameToLength(undefined, 12)).toBe('');
    expect(AppComponent.trimProjectNameToLength('', 12)).toBe('');
    expect(AppComponent.trimProjectNameToLength('test', -1)).toBe('...');
  });
});
