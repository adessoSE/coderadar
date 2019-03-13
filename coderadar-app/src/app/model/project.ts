export class Project {
  public id: number;
  public name: string;
  public vcsUrl: string;
  public vcsUser: string;
  public vcsPassword: string;
  public vcsOnline = true; // always online
  public startDate: string;
  public endDate: string;
  public analysisActive: boolean;

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
      this.analysisActive = true;

      if (project.startDate !== null) {
        const startDate = new Date(project.startDate);
        startDate.setDate(startDate.getDate() + 1);
        this.startDate = startDate.toISOString().split('T')[0];
      } else {
        this.startDate = 'first commit';
      }

      if (project.endDate !== null) {
        const endDate = new Date(project.endDate);
        endDate.setDate(endDate.getDate() + 1);
        this.endDate = endDate.toISOString().split('T')[0];
      } else {
        this.endDate = 'current';
      }
    }
  }
}
