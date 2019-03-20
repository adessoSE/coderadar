import {Action} from '@ngrx/store';
import {ICommit} from '../interfaces/ICommit';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {CommitType} from '../../model/enum/CommitType';

export const LOAD_COMMITS = 'LOAD_COMMITS';
export const LOAD_COMMITS_SUCCESS = 'LOAD_COMMITS_SUCCESS';
export const LOAD_COMMITS_ERROR = 'LOAD_COMMITS_ERROR';
export const CHANGE_COMMIT = 'CHANGE_COMMIT';
export const ADD_SCREENSHOT = 'ADD_SCREENSHOT';
export const CLEAR_SCREENSHOTS = 'CLEAR_SCREENSHOTS';

export function loadCommits(): Action {
    return {
        type: LOAD_COMMITS
    };
}

export function loadCommitsSuccess(commits: ICommit[]): IActionWithPayload<ICommit[]> {
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

export function changeCommit(commitType: CommitType, commit: ICommit): IActionWithPayload<{commitType: CommitType, commit: ICommit}> {
    return {
        type: CHANGE_COMMIT,
        payload: {
            commitType,
            commit
        }
    };
}

export function addScreenshot(screenshotObject: any): IActionWithPayload<any> {
    return {
        type: ADD_SCREENSHOT,
        payload: screenshotObject
    };
}

export function clearScreenshots(): Action {
    return {
        type: CLEAR_SCREENSHOTS
    };
}
