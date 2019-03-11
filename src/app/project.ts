export class Project {
  public id: number;
  public name: string;
  public vcsUrl: string;
  public vcsUser: string;
  public vcsPassword: string;
  public vcsOnline = true;
  public startDate: string;
  public endDate: string;


  /**
   * Construct a new project from the object delivered by the server.
   * If no object is passed as an argument an empty project is constructed.
   * @param project An object parsed from the server-returned JSON.
   */
  constructor(project?: any) {
    if (project !== undefined) {
      this.id = project.id;
      this.name = project.name;
      this.vcsUrl = project.vcsUrl;
      this.vcsUser = project.vcsUser;
      this.vcsPassword = project.vcsPassword;

      if (project.startDate !== null) {
        this.startDate = new Date(project.startDate[0], project.startDate[1] - 1, project.startDate[2]).toDateString();
      } else {
        this.startDate = 'first commit';
      }

      if (project.endDate !== null) {
        this.endDate = new Date(project.endDate[0], project.endDate[1] - 1, project.endDate[2]).toDateString();
      } else {
        this.endDate = 'current';
      }
    }
  }
}
