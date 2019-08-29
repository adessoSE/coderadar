export interface IGoalResponse {
    goals: Goal[];
}
export class GoalResponse implements IGoalResponse {
    goals: Goal[];
}

export class Goal {
    metricDefinition: string;
    conditionType: ConditionType;
    conditionValue: number;
    metricValue: number;
    payload: any;

    /**
     * @todo move to service
     */
    public isSuccessful(metricValue: number, conditionValue: number) {
        switch (this.conditionType) {
            case ConditionType.LESSTHEN: return metricValue < conditionValue ? true : false;
            case ConditionType.GREATERTHEN: return metricValue > conditionValue ? true : false;
            default: return false;
        }
    }
}

export enum ConditionType {
    LESSTHEN = '<',
    GREATERTHEN = '>'
}
