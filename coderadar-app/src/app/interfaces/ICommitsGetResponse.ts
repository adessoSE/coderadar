import {ICommit} from './ICommit';

export interface ICommitsGetResponse {
    _embedded: {
        commitResourceList: ICommit[]
    };
}
