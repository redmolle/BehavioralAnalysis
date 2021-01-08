import React from "react";
import { connect } from "react-redux";
import { withStyles } from "@material-ui/core";
import LoginForm from "../components/LoginForm";
import LogoutForm from "../components/LogoutForm";

const styles = (theme) => ({
});

const Account = ({ classes, ...props }) => {
	return <div>{!props.isLoggedIn ? <LoginForm /> : <LogoutForm />}</div>;
};

const mapStateToProps = (state) => ({
	isLoggedIn: state.account.isLoggedIn
});

const mapActionToProps = {
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(Account));
