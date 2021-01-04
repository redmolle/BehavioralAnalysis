  
import { createStore, applyMiddleware, combineReducers, compose } from "redux";
import thunk from "redux-thunk";
import * as panel from "./panel/actions";
import * as account from "./account/actions";

export const reducers = {
	panel: panel.reducer,
	account: account.reducer
};

export const store = createStore(
	combineReducers(reducers),
	compose(
		applyMiddleware(thunk),
		// window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
	)
);