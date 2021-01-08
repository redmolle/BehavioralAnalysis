import React from "react";
import { connect } from "react-redux";
import * as AccountService from "../services/AccountService";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import { Typography, withStyles } from "@material-ui/core";
import { toast } from "react-toastify";

const styles = (theme) => ({
	root: {
		"& .MuiTextField-root": {
			margin: theme.spacing(1),
			minWidth: 230,
		},
	},
	formControl: {
		margin: theme.spacing(1),
		minWidth: 230,
	},
	smMargin: {
		margin: theme.spacing(1),
	},
});

const LogoutForm = ({ classes, ...props }) => {

	const handleSubmit = (e) => {
		e.preventDefault();

		const onSuccess = () => {
			toast.success("Вы успешно вышли из аккаунта");
		};

		const onFail = (error) => {
			toast.error("Произошла ошибка во время выхода из системы");
		};

		props.logout(props.accessToken, onSuccess, onFail);
	};

	return (
		<div>
			<form
				autoComplete='off'
				noValidate
				className={classes.root}
				onSubmit={handleSubmit}>
				<Grid container direction='column' justify='center' alignItems='center'>
					<Grid item xs={12}>
						<Typography>
							{props.userName} [{props.role}] [{props.accessToken}]
						</Typography>
					</Grid>
					<Grid item xs={12}>
						<Button
							variant='contained'
							color='primary'
							type='submit'
							className={classes.smMargin}>
							Выйти
						</Button>
					</Grid>
				</Grid>
			</form>
		</div>
	);
};

const mapStateToProps = (state) => ({
	userName: state.account.userName,
	role: state.account.role,
	accessToken: state.account.accessToken,
	refreshToken: state.account.refreshToken,
});

const mapActionToProps = {
	logout: AccountService.logout,
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(LogoutForm));
