import axios from "axios";
import * as api from "./api";
import SHA256 from "crypto-js/sha256";

const url = api.baseUrl + "account/";

export const ACTION_TYPES = {
	LOGIN: "LOGIN",
	LOGOUT: "LOGOUT",
};

export const login = (data, onSuccess=undefined, onFail=undefined) => (dispatch) => {
	axios
		.post(url + "login", {username: data.userName, password: data.password})
		.then((response) => {
			dispatch({
				type: ACTION_TYPES.LOGIN,
				payload: response.data,
			});
			if (onSuccess !== undefined) {
				onSuccess();
			}
		})
		.catch((error) => {
			if (onFail !== undefined) {
				onFail(error);
			}
		});
};

export const logout = (accessToken, onSuccess=undefined, onFail=undefined) => (dispatch) => {
	axios
		.post(url + "logout", {}, api.headers(accessToken))
		.then((response) => {})
		.catch((error) => {})
		.finally(() => {
			dispatch({
				type: ACTION_TYPES.LOGOUT,
            });
            if (onSuccess !== undefined) {
                onSuccess();
            }
		})
};

export const refresh = (accessToken, refreshToken, onSuccess=undefined, onFail=undefined) => (
	dispatch
) => {
	axios
		.post(
			url + "refresh-token",
			{ refreshToken: refreshToken },
			api.headers(accessToken)
		)
		.then((response) => {
			dispatch({
				type: ACTION_TYPES.LOGIN,
				payload: response.data,
			});
			if (onSuccess !== undefined) {
				onSuccess();
			}
		})
		.catch((error) => {
			dispatch({
				type: ACTION_TYPES.LOGOUT
			});
			
			if (onFail !== undefined) {
				onFail(error);
			}
		});
};

const initialState = {
	userName: "",
	role: "",
	accessToken: "",
	refreshToken: "",
	isLoggedIn: false,
};

export const reducer = (state = initialState, action) => {
	switch (action.type) {
		case ACTION_TYPES.LOGIN:
			return {
				...state,
				userName: action.payload.userName,
				role: action.payload.role,
				accessToken: action.payload.accessToken,
				refreshToken: action.payload.refreshToken,
				isLoggedIn: true,
			};

		case ACTION_TYPES.LOGOUT:
			return initialState;

		default:
			return state;
	}
};
