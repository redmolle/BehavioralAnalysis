import logo from "./logo.svg";
import "./App.css";
import React from "react";
import { Provider } from "react-redux";
import { Container, withStyles } from "@material-ui/core";
import { ToastContainer, toast } from "react-toastify";
import { Route, BrowserRouter, Switch } from "react-router-dom";
import { store } from "./services/store";
import Home from "./views/Home";
import Menu from "./components/Menu";
import Account from "./views/Account";
import Dashboard from "./views/Dashboard";
import "react-toastify/dist/ReactToastify.css";
import Paper from "@material-ui/core/Paper";
import { routes } from "./routes";

const styles = (theme) => ({
	paper: {
		margin: theme.spacing(2),
		padding: theme.spacing(2),
		width: '100%',
	},
});

const App = ({ classes, ...props }) => {
	toast.configure();
	return (
		<Provider store={store}>
			<BrowserRouter>
				<Container maxWidth='lg'>
					<Switch>
						<Menu>
							<Paper style={{ height: 500, width: '100%' }} className={classes.paper} elevation={3}>
								<Route exact path={routes.home} component={Home} />
								<Route path={routes.dash} component={Dashboard} />
								<Route path={routes.account} component={Account} />
							</Paper>
						</Menu>
						<ToastContainer />
					</Switch>
				</Container>
			</BrowserRouter>
		</Provider>
	);
};

export default withStyles(styles)(App);
