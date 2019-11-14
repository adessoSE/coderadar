import {Action} from '@ngrx/store';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {CommitType} from '../enum/CommitType';
import {Commit} from '../../model/commit';

export const LOAD_COMMITS = 'LOAD_COMMITS';
export const LOAD_COMMITS_SUCCESS = 'LOAD_COMMITS_SUCCESS';
export const LOAD_COMMITS_ERROR = 'LOAD_COMMITS_ERROR';
export const CHANGE_COMMIT = 'CHANGE_COMMIT';

export function loadCommits(): Action {
  return {
    type: LOAD_COMMITS
  };
}

export function loadCommitsSuccess(commits: Commit[]): IActionWithPayload<Commit[]> {
  return {
    type: LOAD_COMMITS_SUCCESS,
    payload: commits
  };
}

export function loadCommitsError(error: string): IActionWithPayload<string> {
  return {
    type: LOAD_COMMITS_ERROR,
    payload: error
  };
}

export function changeCommit(commitType: CommitType, commit: Commit): IActionWithPayload<{ commitType: CommitType, commit: Commit }> {
  return {
    type: CHANGE_COMMIT,
    payload: {
      commitType,
      commit
    }
  };
}
