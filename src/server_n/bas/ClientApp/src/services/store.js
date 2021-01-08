import { createStore, applyMiddleware, combineReducers, compose } from "redux";
import thunk from "redux-thunk";
import * as dashboard from "./DashboardService";
import * as account from "./AccountService";

export const reducers = {
	dashboard: dashboard.reducer,
	account: account.reducer,
};

export const store = createStore(
	combineReducers(reducers),
	compose(
		applyMiddleware(thunk)
		// window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
	)
);
