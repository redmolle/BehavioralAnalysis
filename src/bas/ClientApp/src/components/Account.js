import React, { useState } from "react";
import { connect } from "react-redux";
import { Button, FormControl, Grid, IconButton, InputAdornment, InputLabel, OutlinedInput, TextField, Typography, withStyles } from "@material-ui/core";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import { toast } from "react-toastify";
import useRouting, { routes } from "../routes";
import * as AccountService from "../store/Account";
import clsx from "clsx";

const styles = (theme) => ({
	formControl: {
		margin: theme.spacing(1),
		minWidth: 230,
	},
	smMargin: {
		margin: theme.spacing(1),
	},

	textField: {
		width: "25ch",
	},
	margin: {
		margin: theme.spacing(1),
	},
});

const initialFieldValues = {
	userName: "",
	password: "",
	showPassword: false,
};

const Account = ({ classes, ...props }) => {
    const [values, setValues] = useState(initialFieldValues);
    const [errors, setErrors] = useState({});
    const { changeRoute } = useRouting();

    const validate = (fieldValues = values) => {
        let temp = { ...errors };
        if ("userName" in fieldValues)
            temp.userName = fieldValues.userName ? "" : "Необходимо заполнить поле";
        if ("password" in fieldValues)
            temp.password = fieldValues.password ? "" : "Необходимо заполнить поле";
        setErrors({
            ...temp,
        });

        if (fieldValues === values)
            return Object.values(temp).every((x) => x === "");
    };

    const resetForm = () => {
		setValues({
			...initialFieldValues,
		});
		setErrors({});
	};

	const handleInputChange = (e) => {
		const { name, value } = e.target;
        const fieldValue = { [name]: value };
		setValues({
			...values,
			...fieldValue,
		});
		validate(fieldValue);
    };

    const handleClickShowPassword = () => {
		setValues({ ...values, showPassword: !values.showPassword });
	};

	const handleLogin = (e) => {
		e.preventDefault();
		if (validate()) {
			const onSuccess = () => {
				toast.success(`Добро пожаловать, ${values.userName}!`);
				resetForm();
				changeRoute(routes.dash);
			};

			const onFail = (error) => {
				console.log(JSON.stringify(error));
				toast.error("Произошла ошибка во время входа в аккаунт!");
			};
			props.login(values, onSuccess, onFail);
		} else {
			toast.error("Ошибка!");
		}
	};

	const handleLogout = (e) => {
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
		<form
			autoComplete='off'
			noValidate
			className={classes.root}
			onSubmit={!props.isLoggedIn ? handleLogin : handleLogout}>
			<Grid
			container
			direction='column'
			justify='center'
			alignItems='center'>
				{!props.isLoggedIn ? (
					<div>
						<Grid item xs={12}>
							<TextField
								name='userName'
								variant='outlined'
								label='Имя пользователя'
								value={values.userName}
								onChange={handleInputChange}
								{...(errors.userName && {
									error: true,
								})}
							/>
						</Grid>
						<Grid item xs={12}>
							<FormControl
								className={clsx(classes.margin, classes.textField)}
								variant='outlined'>
								<InputLabel htmlFor='outlined-adornment-password'>
									Password
								</InputLabel>
								<OutlinedInput
									id='outlined-adornment-password'
									type={values.showPassword ? "text" : "password"}
									value={values.password}
									onChange={handleInputChange}
									name='password'
									endAdornment={
										<InputAdornment position='end'>
											<IconButton
												aria-label='toggle password visibility'
												onClick={handleClickShowPassword}
												onMouseDown={(e) => e.preventDefault()}
												edge='end'>
												{values.showPassword ? (
													<Visibility />
												) : (
													<VisibilityOff />
												)}
											</IconButton>
										</InputAdornment>
									}
									notched
									{...(errors.password && {
										error: true,
									})}
								/>
							</FormControl>
						</Grid>
					</div>
				) : (
					<Grid item xs={12}>
						<Typography>
							{props.userName} [{props.role}] [{props.accessToken}]
						</Typography>
					</Grid>
				)}
				<Grid item xs={12}>
					<Button
						variant='contained'
						color='primary'
						type='submit'
						className={classes.smMargin}>
						{!props.isLoggedIn ? "Войти" : "Выйти"}
					</Button>
				</Grid>
			</Grid>
		</form>
	);
};

const mapStateToProps = (state) => ({
	userName: state.account.userName,
	role: state.account.role,
	accessToken: state.account.accessToken,
	refreshToken: state.account.refreshToken,
	isLoggedIn: state.account.isLoggedIn,
});

const mapActionToProps = {
	login: AccountService.login,
	logout: AccountService.logout,
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(Account));
