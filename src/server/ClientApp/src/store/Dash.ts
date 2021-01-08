import { Action, Reducer } from 'redux';
import { AppThunkAction } from './';

export interface DashState {
    logs: Log[];
    isLoading: boolean;
}

export interface Log {
    value: string;
    type: string;
    created: string;
    deviceId: string;
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
    requestLogs: () : AppThunkAction<KnownAction> => (dispatch, getState) => {
        const appState = getState();
        if (appState && appState.dash) {
            fetch(`logger/all`)
            .then(response => response.json() as Promise<Log[]>)
            .then(data => {
                dispatch({type: 'RECEIVE_LOGS', logs: data });
            });

            dispatch({type: 'REQUEST_LOGS' });
        }
    }

};

const unloadedState: DashState = { logs: [], isLoading: false };

export const reducer: Reducer<DashState> = (state: DashState | undefined, incomingAction: Action): DashState => {
    if (state === undefined) {
        return unloadedState;
    }

    const action = incomingAction as KnownAction;
    switch (action.type) { 
        case 'REQUEST_LOGS':
            return {
                ...state,
                isLoading: true;
            };

        case 'RECEIVE_LOGS':
                return {
                    logs: action.logs,
                    isLoading: false
                };
    }

    return state;
}