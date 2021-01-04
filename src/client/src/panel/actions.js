import axios from "axios";
import * as api from "../api";

const url = api.baseUrl + "logger/";

export const ACTION_TYPES = {
    GET_ALL: "GET_ALL",
    GET_BY_TYPE: "GET_BY_TYPE"
};

export const getAll = () => (dispatch) => {
    axios.get(url + "all")
    .then(response => dispatch({
            type: ACTION_TYPES.GET_ALL,
            payload: response.data
    }))
    .catch(error => console.log(error));
}

const initialState = {
    list: []
};

export const reducer = (state=initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.GET_ALL:
            return {
                ...state,
                list: action.payload
            };
        case ACTION_TYPES.GET_BY_TYPE:
            return {
                ...state,
                list: action.payload
            };
        default:
            return state;
    };
};