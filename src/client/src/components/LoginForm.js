import React from "react";
import { connect } from "react-redux";
import * as AccountService from "../services/AccountService";
import {
	TextField,
	withStyles,
	Button,
	OutlinedInput,
	InputAdornment,
	IconButton,
	InputLabel,
	FormControl,
} from "@material-ui/core";
import useForm from "./useForm";
import { toast } from "react-toastify";
import useRouting, { routes } from "../routes";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import clsx from "clsx";
import GridItem from "./GridItem";
import GridContainer from "./GridContainer";

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

const LoginForm = ({ classes, ...props }) => {
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

	const {
		values,
		setValues,
		errors,
		setErrors,
		handleInputChange,
		resetForm,
	} = useForm(initialFieldValues, validate);

	const { changeRoute } = useRouting();

	const handleSubmit = (e) => {
		e.preventDefault();
		if (validate()) {
			const onSuccess = () => {
				toast.success(`Добро пожаловать, ${values.userName}!`);
				resetForm();
				changeRoute(routes.dash);
			};

			const onFail = (error) => {
				toast.error("Произошла ошибка во время входа в аккаунт!");
			};
			props.login(values.userName, values.password, onSuccess, onFail);
		} else {
			validate();
			const errorMessage = () => {
				let tmp = [];
				for (var e in errors) {
					if (e) {
						tmp.push(e);
					}
				}
				return tmp.join(", ");
			};
			toast.error(
				"Ошибка!" +
					Object.keys(errors)
						.map((k) => errors[k])
						.filter((e) => e)
						.join(",")
			);
		}
	};

	const handleClickShowPassword = () => {
		setValues({ ...values, showPassword: !values.showPassword });
	};

	return (
		<div>
			<form
				autoComplete='off'
				noValidate
				className={classes.root}
				onSubmit={handleSubmit}>
				<GridContainer>
					<GridItem>
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
					</GridItem>
					<GridItem>
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
											{values.showPassword ? <Visibility /> : <VisibilityOff />}
										</IconButton>
									</InputAdornment>
								}
								notched
								{...(errors.password && {
									error: true,
								})}
							/>
						</FormControl>
					</GridItem>
					<GridItem>
						<Button
							variant='contained'
							color='primary'
							type='submit'
							className={classes.smMargin}>
							Войти
						</Button>
					</GridItem>
				</GridContainer>
			</form>
		</div>
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
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(LoginForm));
