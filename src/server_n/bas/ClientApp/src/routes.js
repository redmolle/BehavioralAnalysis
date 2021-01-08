import React from "react";
import { useHistory } from "react-router";

export const routes = {
    home: "/",
    dash: "/dash",
    account: "/account"
}

const useRouting = () => {
    const history = useHistory();

    const changeRoute = (route) => {
        history.push(route  ? route : routes.home);
    }
    
	return {
		changeRoute
	};
};

export default useRouting;