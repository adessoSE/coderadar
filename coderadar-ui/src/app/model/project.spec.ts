import {Project} from './project';

describe('Project', () => {
  const projectData = {
    id: 1,
    name: 'test',
    vcsUrl: 'https://valid.url',
    vcsUsername: '',
    vcsPassword: '',
    vcsOnline: true,
    startDate: null,
    endDate: null
  };

  it('should create an instance', () => {
    expect(new Project()).toBeTruthy();
  });

  it('should create an instance from project no dates', () => {
    projectData.startDate = null;
    projectData.endDate = null;
    const project = new Project(projectData);
    expect(project.name).toBe('test');
    expect(project.startDate).toBe('first commit');
    expect(project.endDate).toBe('current');
  });

  it('should create an instance from project start date', () => {
    projectData.startDate = '2020-01-01T08:00:00.000Z';
    projectData.endDate = null;
    const project = new Project(projectData);
    expect(project.name).toBe('test');
    expect(project.startDate).toBe('2020-01-01');
    expect(project.endDate).toBe('current');
  });

  it('should create an instance from project end date', () => {
    projectData.startDate = null;
    projectData.endDate = '2020-01-01T08:00:00.000Z';
    const project = new Project(projectData);
    expect(project.name).toBe('test');
    expect(project.startDate).toBe('first commit');
    expect(project.endDate).toBe('2020-01-01');
  });

  it('should create an instance from project start date and end date', () => {
    projectData.startDate = '2019-01-01T08:00:00.000Z';
    projectData.endDate = '2020-01-01T08:00:00.000Z';
    const project = new Project(projectData);
    expect(project.name).toBe('test');
    expect(project.startDate).toBe('2019-01-01');
    expect(project.endDate).toBe('2020-01-01');
  });

  it('should create instance undefined project', () => {
    const project = new Project(undefined);
    expect(project.id).toBe(undefined);
    expect(project.name).toBe(undefined);
    expect(project.vcsUrl).toBe(undefined);
    expect(project.startDate).toBe(undefined);
    expect(project.endDate).toBe(undefined);
  });
});
