import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Project} from '../model/project';
import {Router} from '@angular/router';
import {UserService} from './user.service';
import {FilePatterns} from '../model/file-patterns';
import {AnalyzerConfiguration} from '../model/analyzer-configuration';
import {Commit} from '../model/commit';
import {Module} from '../model/module';
import {AppComponent} from '../app.component';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private userService: UserService, private router: Router, private httpClient: HttpClient) { }

  private apiURL = AppComponent.getApiUrl();

  /**
   * Gets all the projects from the server.
   * Sends a GET request to /projects
   */
  public getProjects(): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects?page=0&size=2147483647', {observe: 'response'}).toPromise();
  }

  /**
   * Gets a project from the database.
   * Sends a GET request to /projects/{id}
   * @param id The id of the project.
   */
  public getProject(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id , {observe: 'response'}).toPromise();
  }

  /**
   * Adds a new project.
   * Sends a POST request to /projects.
   * @param project The project to add.
   */
  public addProject(project: Project): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'projects', JSON.stringify(project), {observe: 'response'}).toPromise();
  }

  /**
   * Edits an existing project.
   * Sends a POST request to /projects/{id}
   * @param project A Project object containing the changes.
   */
  public editProject(project: Project): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'projects/' + project.id, JSON.stringify(project), {observe: 'response'}).toPromise();
  }

  /**
   * Deletes a project from the database (as long as it's not being analyzed at the moment).
   * Sends a DELETE request to /projects/{id}
   * @param id The id of the project to delete.
   */
  public deleteProject(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.delete(this.apiURL + 'projects/' + id, {observe: 'response'}).toPromise();
  }

  /**
   * Gets the file patterns for a project given it's id.
   * Sends a GET request to /projects/{id}/files
   * @param id The id of the project.
   */
  public getProjectFilePatterns(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/files', {observe: 'response'}).toPromise();
  }

  /**
   * Sets the file patterns for a project.
   * Sends a POST request to /projects/{id}/files
   * @param id The id of the project.
   * @param patterns an array of FilePatterns objects.
   */
  public setProjectFilePatterns(id: number, patterns: FilePatterns[]): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/files', {filePatterns: patterns},
      {observe: 'response'}).toPromise();
  }

  /**
   * Gets the modules for a projects given it's id.
   * Sends a GET request to /projects/{id}/modules
   * @param id The id of the  project.
   */
  public getProjectModules(id: number): Promise<HttpResponse<Module[]>> {
    return this.httpClient.get<Module[]>(this.apiURL + 'projects/' + id + '/modules', {observe: 'response'}).toPromise();
  }

  /**
   * Adds a module to a project.
   * Sends a POST request to /projects/{id}/modules
   * @param id The id of the project.
   * @param module The name (path) of the module.
   */
  public addProjectModule(id: number, module: Module): Promise<HttpResponse<any>> {
      return this.httpClient.post(this.apiURL + 'projects/' + id + '/modules', {modulePath: module.modulePath},
        {observe: 'response'}).toPromise();
  }

  /**
   * Edits a module of a project.
   * Sends a POST request to /projects/{id}/modules/{moduleId}
   * @param id The id of the project.
   * @param module The name (path) of the module.
   */
  public editProjectModule(id: number, module: Module): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/modules/' + module.id, JSON.stringify(module),
      {observe: 'response'}).toPromise();
  }

  /**
   * Deletes a module of a project.
   * Sends a DELETE request to /projects/{id}/modules/{moduleId}
   * @param id The id of the project.
   * @param module The name (path) of the module.
   */
  public deleteProjectModule(id: number, module: Module): Promise<HttpResponse<any>> {
    return this.httpClient.delete(this.apiURL + 'projects/' + id + '/modules/' + module.id, {observe: 'response'}).toPromise();
  }

  /**
   * Returns all of the configured analyzer for a project.
   * Sends a GET request to /projects/{id}/analyzers
   * @param id The id of the project.
   */
  public getProjectAnalyzers(id: number): Promise<HttpResponse<AnalyzerConfiguration[]>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/analyzers', {observe: 'response'}).toPromise();
  }

  /**
   * Adds an analyzer configuration to a project.
   * Sends POST request to /projects/{id}/analyzers
   * @param id The id of the project.
   * @param analyzer The analyzer configuration to add.
   */
  public addAnalyzerConfigurationToProject(id: number, analyzer: AnalyzerConfiguration): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzers', JSON.stringify(analyzer), {observe: 'response'}).toPromise();
  }

  /**
   * Modifies an existing analyzer configuration for a project.
   * Sends a POST request to /projects/{id}/analyzers/{analyzerId}
   * @param id The id of the project.
   * @param analyzer The analyzerConfiguration to modify.
   */
  public editAnalyzerConfigurationForProject(id: number, analyzer: AnalyzerConfiguration): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzers/' + analyzer.id, JSON.stringify(analyzer),
      {observe: 'response'}).toPromise();
  }

  /**
   * Gets all of the available analyzers in coderadar.
   * Sends a GET request to /analyzers.
   */
  public getAnalyzers(): Promise<HttpResponse<AnalyzerConfiguration[]>> {
    return this.httpClient.get<AnalyzerConfiguration[]>(this.apiURL + 'analyzers', {observe: 'response'}).toPromise();
  }

  /**
   * Gets the running analyzing job for a project.
   * Sends a GET request to /projects/{id}/analyzingJob
   * @param id The project id.
   */
  public getAnalyzingJob(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/analyzingJob', {observe: 'response'}).toPromise();
  }

  /**
   * Start a new analyzing for a project.
   * Sends a POST request to /projects/{id}/analyzingJob
   * @param id The id of the project.
   * @param rescanProject Rescan all commits if true.
   */
  public startAnalyzingJob(id: number, rescanProject: boolean): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzingJob', {fromDate: 0, active: true, rescan: rescanProject},
      {observe: 'response'}).toPromise();
  }

  /**
   * Start a new analyzing for a project.
   * Sends a POST request to /projects/{id}/analyzingJob
   * @param id The id of the project.
   */
  public stopAnalyzingJob(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzingJob', {fromDate: 0, active: false, rescan: false},
      {observe: 'response'}).toPromise();
  }

  /**
   * Gets all available commits for a project.
   * Sends a GET request to /projects/{id}/commits
   * @param id The project id.
   */
  public getCommits(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/commits?page=0&size=2147483647', {observe: 'response'}).toPromise();
  }

  /**
   * Gets the metric values for a commit given it's name and the metric names.
   * Sends a POST request to /projects/{id}/metricvalues/perCommit
   * @param id The project id.
   * @param commitName The name (hash) of the commit.
   * @param metricsNames The names of the wanted metrics.
   */
  public getCommitsMetricValues(id: number, commitName: string, metricsNames: string[]): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/metricvalues/perCommit',
      {commit: commitName, metrics: metricsNames}, {observe: 'response'}).toPromise();
  }

  /**
   * Returns all metrics that have been measured for a project.
   * @param id The project id.
   */
  public getAvailableMetrics(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get(this.apiURL + 'projects/' + id + '/metrics', {observe: 'response'}).toPromise();
  }
}
