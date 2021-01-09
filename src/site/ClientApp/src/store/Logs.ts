import { Action, Reducer } from 'redux';
import { AppThunkAction } from '.';

export interface LogState {
    isLoading: boolean;
    logs: Log[];
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
}

interface ReceiveLogsAction {
    type: 'RECEIVE_LOGS';
    logs: Log[];
}

type KnownAction = RequestLogsAction | ReceiveLogsAction;

export const actionCreators = {
    requestLogs: (): AppThunkAction<KnownAction> => (dispatch, getState) => {
        const appState = getState();
        if (appState && appState.logs) {
            fetch(`log`)
                .then(response => response.json() as Promise<Log[]>)
                .then(data => {
                    dispatch({ type: 'RECEIVE_LOGS', logs: data });
                });

            dispatch({ type: 'REQUEST_LOGS' });
        }
    }
};

const unloadedState: LogState = { logs: [], isLoading: false };

export const reducer: Reducer<LogState> = (state: LogState | undefined, incomingAction: Action): LogState => {
    if (state === undefined) {
        return unloadedState;
    }

    const action = incomingAction as KnownAction;
    switch (action.type) {
        case 'REQUEST_LOGS':
            return {
                ...state,
                isLoading: true
            };
        case 'RECEIVE_LOGS':
            return {
                logs: action.logs,
                isLoading: false
            };
    }

    return state;
};