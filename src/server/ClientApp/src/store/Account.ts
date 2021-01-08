import { Action, Reducer } from 'redux';
import { AppThunkAction } from './';

export interface AccountState {
    account?: Account;
    isLoggedIn: boolean;
}

export interface Account {
    userName: string;
    role: string;
    accessToken: string;
    refreshToken: string;
}

interface RequestLoginAction {
    type: 'REQUEST_LOGIN';
    userName: string;
}

interface ReceiveLoginAction {
    type: 'RECEIVE_LOGIN';
    account: Account;
}

interface RequestRefreshAction {
    type: 'REQUEST_REFRESH_TOKEN';
    account: Account;
}

interface ReceiveRefreshToken {
    type: 'RECEIVE_REFRESH_TOKEN';
    account: Account;
}

type KnownAction = RequestLoginAction 
| ReceiveLoginAction
| RequestRefreshAction
| ReceiveRefreshToken;

export const actionCreators = {
    login: (userName: string, password: string) : AppThunkAction<KnownAction> => (dispatch, getState) => {
        const appState = getState();
        if (appState && appState.account && userName !== appState.account.account?.userName) {
            fetch(`account/login`)
            .then(response => response.json() as Promise<Account>)
            .then(data => {
                dispatch({type: 'RECEIVE_LOGIN', account: data });
            });

            dispatch({type: 'REQUEST_LOGIN', userName: userName });
        }
    }

};

const unloadedState: AccountState = { account: undefined, isLoggedIn: false };

export const reducer: Reducer<AccountState> = (state: AccountState | undefined, incomingAction: Action): AccountState => {
    if (state === undefined) {
        return unloadedState;
    }

    const action = incomingAction as KnownAction;
    switch (action.type) { 
        case 'REQUEST_LOGIN':
            return {
                account: state.account,
                isLoggedIn: false
            }

        case 'RECEIVE_LOGIN':
            if (action.account.userName === state.account?.userName) {
                return {
                    account: action.account,
                    isLoggedIn: true
                }
            }
            break;
    }

    return state;
}