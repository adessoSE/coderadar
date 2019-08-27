import { MetricDefinition } from './metric';

import { validateHorizontalPosition } from '@angular/cdk/overlay';
import { extend } from 'webdriver-js-extender';

export enum IssueSeverties {
    MINOR = 'Minor',
    MAJOR = 'Major',
    CRITICAL = 'Critical',
    INFO = 'Info',
    BLOCKER = 'Blocker'
}

export enum IssueTypes {
    CODE_SMELL = 'Code smell',
    BUG = 'Bug',
    VULNERABILITY = 'Vulnerability'
}

// Facets are type and severity
export interface Facet {
    property: string;
    values: Metric[];
}

export interface Metric {
    val: string;
    count: number;
}

export interface Issues {
  total: number;
  types: FacetValue[];
  severties: FacetValue[];
  issues: Issue[];
}

export interface FacetValue {
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

export class IssueConfiguration {
    severity: string;
    type: string;
    condition: string;
    conditionValue: number;
}

// TODO auslagern
export enum ConditionType {
    MAX = 'max',
    MIN = 'min',
    lessThan = '<',
    greaterThan = '>'
}

export interface IGoalResponse {
    goals: Goal[];
}
export class GoalResponse implements IGoalResponse{
    goals: Goal[];

}

export class Goal {
    metricDefinition: MetricDefinition;
    conditionType: ConditionType;
    conditionValue: number;
    metricValue: number;
    payload: any;
    isSuccessful: boolean;
}
