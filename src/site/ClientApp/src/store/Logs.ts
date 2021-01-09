import { Action, Reducer } from 'redux';
import { AppThunkAction } from '.';

export interface LogState {
    isLoading: boolean;
    page?: number;
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
}

interface ReceiveLogsAction {
    type: 'RECEIVE_LOGS';
    page: number;
    maxPage: number;
    logs: Log[];
}

type KnownAction = RequestLogsAction | ReceiveLogsAction;

export const actionCreators = {
    requestLogs: (page: number): AppThunkAction<KnownAction> => (dispatch, getState) => {
        const appState = getState();
        if (appState && appState.logs && page !== appState.logs.page) {
            fetch(`api/log/${page}`)
                //.then(response => response.text())
                //.then(text => console.log(text))
                .then(response => response.json() as Promise<Response>)
                .then(data => {
                    dispatch({ type: 'RECEIVE_LOGS', page: page, maxPage: data.maxPage, logs: data.logs });
                })
                .catch(error => console.log(error));

            dispatch({ type: 'REQUEST_LOGS', page: page });
        }
    }
};

const unloadedState: LogState = { maxPage: 1, logs: [], isLoading: false };

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
                isLoading: true
            };
        case 'RECEIVE_LOGS':
            console.log(action);

            if (action.page === state.page) {
                return {
                    page: action.page,
                    maxPage: action.maxPage,
                    logs: action.logs,
                    isLoading: false
                };
            }
            break;
    }

    return state;
};