export enum IssueSevertyEnum {
    MINOR = 'Minor',
    MAJOR = 'Major',
    CRITICAL = 'Critical',
    INFO = 'Info',
    BLOCKER = 'Blocker'
}

export enum IssueTypeEnum {
    CODE_SMELL = 'Code smell',
    BUG = 'Bug',
    VULNERABILITY = 'Vulnerability'
}

export interface Issues {
    total: number;
    types: IssueFacet[];
    severties: IssueFacet[];
    issues: Issue[];
}

export interface IssueFacet {
    val: string;
    count: number;
    countOnNewCode: number;
}

export interface Issue {
    creationDate: Date;
    severity: string;
    type: string;
    component: string;
    line: number;
    status: string;
    message: string;
    assignee: string;
}
