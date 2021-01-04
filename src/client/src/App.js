import logo from './logo.svg';
import './App.css';
import { Provider } from "react-redux";
import { Container } from "@material-ui/core";
import { ToastProvider } from "react-toast-notifications";
import { Route, BrowserRouter, Switch } from "react-router-dom";
import { store } from "./store";
import Home from "./home/component";
import Panel from "./panel/component";
import Login from "./account/login/component";

function App() {
  return (
		<Provider store={store}>
			<BrowserRouter>
				<ToastProvider autoDismiss={true}>
					<Container maxWidth='lg'>
						<Switch>
							<Route exact path='/' component={Home}/>
							<Route path='/panel' component={Panel}/>
							<Route path='/login' component={Login}/>
						</Switch>
					</Container>
				</ToastProvider>
			</BrowserRouter>
		</Provider>
  );
}

export default App;
