import * as ControlPanelActions from './control-panel.actions';
import {ActionReducer} from '@ngrx/store';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {CommitType} from '../../model/enum/CommitType';
import * as _ from 'lodash';
import {Commit} from '../../model/commit';

export interface ControlPanelState {
    commits: Commit[];
    commitsLoading: boolean;
    leftCommit: Commit;
    rightCommit: Commit;
    screenshots: any[];
}

const initialState: ControlPanelState = {
    commits: [],
    commitsLoading: false,
    leftCommit: null,
    rightCommit: null,
    screenshots: []
};

export const ControlPanelReducer: ActionReducer<ControlPanelState> = (state = initialState, action: IActionWithPayload<any>) => {
    let newState;
    switch (action.type) {
        case ControlPanelActions.LOAD_COMMITS:
            newState = Object.assign({}, state);
            newState.commitsLoading = true;
            return newState;

        case ControlPanelActions.LOAD_COMMITS_SUCCESS:
            newState = Object.assign({}, state);
            newState.commits = _.sortBy(action.payload, (commit: Commit) => commit.timestamp);
            newState.commitsLoading = false;
            newState.leftCommit = newState.commits[0];
            newState.rightCommit = newState.commits[1];
            return newState;

        case ControlPanelActions.LOAD_COMMITS_ERROR:
            newState = Object.assign({}, state);
            newState.commitsLoading = false;
            console.error(`Error while loading commits: ${action.payload}`);
            return state;

        case ControlPanelActions.CHANGE_COMMIT:
            newState = Object.assign({}, state);

            if (action.payload.commitType === CommitType.LEFT) {
                newState.leftCommit = action.payload.commit;
            } else if (action.payload.commitType === CommitType.RIGHT) {
                newState.rightCommit = action.payload.commit;
            } else {
                throw new Error(`Invalid CommitType ${action.payload.commitType}!`);
            }

            return newState;

        default:
            return state;
    }
};

export const getCommits = (state: ControlPanelState) => state.commits;

export const getCommitsLoading = (state: ControlPanelState) => state.commitsLoading;

export const getLeftCommit = (state: ControlPanelState) => state.leftCommit;

export const getRightCommit = (state: ControlPanelState) => state.rightCommit;
