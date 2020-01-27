export interface FetchedTodos {
    project: string;
    numberOfTodosTotal: number;
    numberOfTodosLeakPeriod: number;
    todos: SingleTodo[];
}

export interface SingleTodo {
    file: string;
    extractedTodo: string;
    author: string;
    committedAt: Date;
}