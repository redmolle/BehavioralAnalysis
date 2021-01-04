import axios from "axios";
import * as api from "../api";

const url = api.baseUrl + "account/";

export const ACTION_TYPES = {
    LOGIN: "LOGIN",
    LOGOUT: "LOGOUT"
};

export const login = (data, onSuccess, onFail) => (dispatch) => {
    axios.post(url + "login", data)
    .then(response => {
        dispatch({
        type: ACTION_TYPES.LOGIN,
        payload: response.data
        });
        onSuccess();
    })
    .catch(error => {
        console.log(error);
        onFail(error);
    });
}

const initialState = {
    userName: "",
    role: "",
    accessToken: "",
    refreshToken: "",
    isLoggedIn: false
};

export const reducer = (state = initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.LOGIN: return {
            ...state,
            userName: action.payload.userName,
            role: action.payload.role,
            accessToken: action.payload.accessToken,
            refreshToken: action.payload.refreshToken,
            isLoggedIn: true,
        };
        
        default: return state;
    }
};