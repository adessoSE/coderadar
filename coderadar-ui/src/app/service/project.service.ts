import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Project} from '../model/project';
import {FilePattern} from '../model/file-pattern';
import {AnalyzerConfiguration} from '../model/analyzer-configuration';
import {Commit} from '../model/commit';
import {Module} from '../model/module';
import {AppComponent} from '../app.component';
import {IMetricMapping} from '../city-map/interfaces/IMetricMapping';
import {INode} from '../city-map/interfaces/INode';
import {MetricValue} from '../model/metric-value';
import {Branch} from '../model/branch';
import {CommitLog} from '../model/commit-log';
import {FileTreeNode} from '../model/file-tree-node';
import {FileContentWithMetrics} from '../model/file-content-with-metrics';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiURL = AppComponent.getApiUrl();

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Gets all the projects from the server.
   * Sends a GET request to /projects
   */
  public getProjects(): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects', {observe: 'response'}).toPromise();
  }

  /**
   * Gets a project from the database.
   * Sends a GET request to /projects/{id}
   * @param id The id of the project.
   */
  public getProject(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id, {observe: 'response'}).toPromise();
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
   * Sends a GET request to /projects/{id}/filePatterns
   * @param id The id of the project.
   */
  public getProjectFilePatterns(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/filePatterns', {observe: 'response'}).toPromise();
  }

  /**
   * Sets the file pattern for a project.
   * Sends a POST request to /projects/{id}/filePatterns
   * @param id The id of the project.
   * @param pattern a FilePattern objects.
   */
  public addProjectFilePattern(id: number, pattern: FilePattern): Promise<HttpResponse<any>> {
      return this.httpClient.post(this.apiURL + 'projects/' + id + '/filePatterns', pattern,
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
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/modules', {path: module.path},
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
  public getAnalyzers(): Promise<HttpResponse<string[]>> {
    return this.httpClient.get<string[]>(this.apiURL + 'analyzers', {observe: 'response'}).toPromise();
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
   * @param branch The branch to analyze.
   */
  public startAnalyzingJob(id: number, branch: string): Promise<HttpResponse<any>> {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/' + branch + '/analyze', {},
      {observe: 'response'}).toPromise();
  }

  /**
   * Start a new analyzing for a project.
   * Sends a POST request to /projects/{id}/analyzingJob
   * @param id The id of the project.
   */
  public stopAnalyzingJob(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get(this.apiURL + 'projects/' + id + '/stopAnalysis',
      {observe: 'response'}).toPromise();
  }

  /**
   * Gets all available commits for a project.
   * Sends a GET request to /projects/{id}/{branchName}/commits
   * @param id The project id.
   * @param branch The branch to use for getting the commits.
   */
  public getCommits(id: number, branch: string): Promise<HttpResponse<Commit[]>> {
    return this.httpClient.get<Commit[]>(this.apiURL + 'projects/' + id + '/' + branch + '/commits', {observe: 'response'}).toPromise();
  }

  /**
   * Gets the commit log
   * Sends a GET request to /projects/{id}/commitLog
   * @param id The project id.
   */
  public getCommitLog(id: number): Promise<HttpResponse<CommitLog[]>> {
    return this.httpClient.get<CommitLog[]>(this.apiURL + 'projects/' + id + '/commitLog', {observe: 'response'}).toPromise();
  }

  /**
   * Gets the metric values for a commit given it's name and the metric names.
   * Sends a POST request to /projects/{id}/metricvalues/perCommit
   * @param id The project id.
   * @param commitName The name (hash) of the commit.
   * @param metricsNames The names of the wanted metrics.
   */
  public getCommitsMetricValues(id: number, commitName: string, metricsNames: string[]): Promise<HttpResponse<MetricValue[]>> {
    return this.httpClient.post<MetricValue[]>(this.apiURL + 'projects/' + id + '/metricvalues/perCommit',
      {commit: commitName, metrics: metricsNames}, {observe: 'response'}).toPromise();
  }

  /**
   * Returns all metrics that have been measured for a project.
   * @param id The project id.
   */
  public getAvailableMetrics(id: number): Promise<HttpResponse<any>> {
    return this.httpClient.get(this.apiURL + 'projects/' + id + '/metrics', {observe: 'response'}).toPromise();
  }

  /**
   * Returnsthe delta three of a project given two commits and a metric mapping
   * @param firstCommit The first commit
   * @param secondCommit The second commit
   * @param metricMapping The metric mapping
   * @param projectId The project id.
   */
  public getDeltaTree(firstCommit: Commit, secondCommit: Commit, metricMapping: IMetricMapping, projectId: number):
    Promise<HttpResponse<INode>> {
    const body = {
      commit1: firstCommit.name,
      commit2: secondCommit.name,
      metrics: [metricMapping.heightMetricName, metricMapping.groundAreaMetricName, metricMapping.colorMetricName]
    };

    return this.httpClient.post<INode>(this.apiURL + 'projects/' + projectId + '/metricvalues/deltaTree', body,
      {observe: 'response'}).toPromise();
  }

  deleteProjectFilePattern(projectId: any, pattern: FilePattern) {
    return this.httpClient.delete(this.apiURL + 'projects/' + projectId + '/filePatterns/' + pattern.id, {observe: 'response'}).toPromise();
  }

  /**
   * Reset the analysis results of a project.
   * Sends a POST request to /projects/{id}/analyze/reset
   * @param id The id of the project.
   */
  resetAnalysis(id: number) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyze/reset', {}, {observe: 'response'}).toPromise();
  }



  /**
   * Returns all of the branches for a project.
   * Sends a GET request to /projects/{id}/branches
   * @param id The id of the project.
   */
  public getProjectBranches(id: number): Promise<HttpResponse<Branch[]>> {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/branches', {observe: 'response'}).toPromise();
  }

  /**
   * Get the dependencyTree for to build a levelized structure map for a specific commit.
   * Sends a GET request to /analyzers/{projectId}/structureMap/{commitName}
   * @param projectId The project id.
   * @param commitName The name (hash) of the commit.
   */
  public getDependencyTree(projectId: number, commitName: string) {
    return this.httpClient.get(this.apiURL + 'analyzers/' + projectId + '/structureMap/' + commitName, {observe: 'response'}).toPromise();
  }

  /**
   * Get the dependencyTree to compare two given commits in a levelized structure map.
   * Sends GET requerst to /analyzers/{projectId}/structureMap/{commitName1}/{commitName2}
   * @param projectId The project id.
   * @param commitName1 The name (hash) of the first commit.
   * @param commitName2 The name (hash) of the second commit.
   */
  public getCompareTree(projectId: number, commitName1: string, commitName2: string) {
    return this.httpClient.get(this.apiURL + 'analyzers/' + projectId + '/structureMap/' + commitName1 + '/' + commitName2,
      {observe: 'response'}).toPromise();
  }

  public getFileTree(projectId: number, commitHash: string): Promise<HttpResponse<FileTreeNode>> {
    return this.httpClient.get<FileTreeNode>(this.apiURL + 'projects/' + projectId + '/files/tree/' + commitHash,
      {observe: 'response'}).toPromise();
  }

  getFileContentWithMetrics(projectId: any, commitHash: any, filepath: string): Promise<HttpResponse<FileContentWithMetrics>> {
    return this.httpClient.post<FileContentWithMetrics>(this.apiURL + 'projects/' + projectId + '/files/content',
      {commitHash, filepath}, {observe: 'response'}).toPromise();
  }
}
