export interface History {
    points: DataPoint[];
}

export interface DataPoint {
    type: string;
    x: number[];
    y: number;
}
