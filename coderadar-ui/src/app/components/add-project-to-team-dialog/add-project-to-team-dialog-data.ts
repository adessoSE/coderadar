import {Project} from '../../model/project';
import {Team} from '../../model/team';
import {ProjectRole} from '../../model/project-role';

export class AddProjectToTeamDialogData {
  project: Project;
  teams: Team[];
  teamsForProject: Team[];
  role: ProjectRole;
}


