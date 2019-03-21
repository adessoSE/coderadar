import * as ControlPanelActions from './control-panel.actions';
import {ActionReducer} from '@ngrx/store';
import {ICommit} from '../interfaces/ICommit';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {CommitType} from '../../model/enum/CommitType';
import * as _ from 'lodash';

export interface ControlPanelState {
    commits: ICommit[];
    commitsLoading: boolean;
    leftCommit: ICommit;
    rightCommit: ICommit;
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
            newState.commits = _.sortBy(action.payload, (commit: ICommit) => commit.timestamp);
            newState.commitsLoading = false;
            newState.leftCommit = newState.commits[0];
            newState.rightCommit = newState.commits[1];
            console.log(newState);
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

        case ControlPanelActions.ADD_SCREENSHOT:
            newState = Object.assign({}, state);
            newState.screenshots = [...state.screenshots, action.payload];
            return newState;

        case ControlPanelActions.CLEAR_SCREENSHOTS:
            newState = Object.assign({}, state);
            newState.screenshots = [];
            return newState;

        default:
            return state;
    }
};

export const getCommits = (state: ControlPanelState) => state.commits;

export const getCommitsLoading = (state: ControlPanelState) => state.commitsLoading;

export const getLeftCommit = (state: ControlPanelState) => state.leftCommit;

export const getRightCommit = (state: ControlPanelState) => state.rightCommit;

export const getScreenshots = (state: ControlPanelState) => state.screenshots;
