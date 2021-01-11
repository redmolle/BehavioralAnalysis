import { Action, Reducer } from 'redux';
import { AppThunkAction } from '.';

export interface LogState {
    isLoading: boolean;
    page?: number;
    filter: string;
    maxPage: number;
    logs: Log[];
}

interface Response {
    maxPage: number;
    logs: [];
}

export interface Log {
    id: string;
    date: string;
    device: string;
    type: string;
    value: string;
}

interface RequestLogsAction {
    type: 'REQUEST_LOGS';
    page: number;
    filter: string;
}

interface ReceiveLogsAction {
    type: 'RECEIVE_LOGS';
    page: number;
    filter: string;
    maxPage: number;
    logs: Log[];
}

type KnownAction = RequestLogsAction | ReceiveLogsAction;

export const actionCreators = {
    requestLogs: (page: number, filter: string): AppThunkAction<KnownAction> => (dispatch, getState) => {
        const appState = getState();
        if (appState && appState.logs && (page !== appState.logs.page || filter !== appState.logs.filter)) {
            fetch(`api/log/${page}?filter=${filter}`)
                .then(response => response.json() as Promise<Response>)
                .then(data => {
                    dispatch({ type: 'RECEIVE_LOGS', page: page, maxPage: data.maxPage, logs: data.logs, filter: filter });
                })
                .catch(error => console.log(error));

            dispatch({ type: 'REQUEST_LOGS', page: page, filter: filter });
        }
    }
};

const unloadedState: LogState = { filter: "none", maxPage: 1, logs: [], isLoading: false };

export const reducer: Reducer<LogState> = (state: LogState | undefined, incomingAction: Action): LogState => {
    if (state === undefined) {
        return unloadedState;
    }

    const action = incomingAction as KnownAction;
    switch (action.type) {
        case 'REQUEST_LOGS':
            return {
                ...state,
                page: action.page,
                filter: action.filter,
                isLoading: true
            };
        case 'RECEIVE_LOGS':
            if (action.page === state.page && action.filter === state.filter) {
                return {
                    page: action.page,
                    filter: action.filter,
                    maxPage: action.maxPage,
                    logs: action.logs,
                    isLoading: false
                };
            }
            break;
    }

    return state;
};