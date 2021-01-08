import logo from "./logo.svg";
import "./App.css";
import React from "react";
import { Provider } from "react-redux";
import { Container, withStyles } from "@material-ui/core";
import { ToastContainer, toast } from "react-toastify";
import { Route, BrowserRouter, Switch } from "react-router-dom";
import { store } from "./store/store";
import Home from "./components/Home";
import Menu from "./components/Menu";
import Account from "./components/Account";
import Dash from "./components/Dash";
import "react-toastify/dist/ReactToastify.css";
import { routes } from "./routes";

const App = ({ classes, ...props }) => {
	toast.configure();
	return (
		<Provider store={store}>
			<BrowserRouter>
				<Container maxWidth='lg'>
					<Switch>
						<Menu>
								<Route exact path={routes.home} component={Home} />
								<Route path={routes.dash} component={Dash} />
								<Route path={routes.account} component={Account} />
						</Menu>
						<ToastContainer />
					</Switch>
				</Container>
			</BrowserRouter>
		</Provider>
	);
};

export default App;
