import React, { useState, useEffect } from "react";
import { connect } from "react-redux";
import * as Actions from '../actions';
import {
    Grid,
    TextField,
    withStyles,
    Button,
} from "@material-ui/core";
import useForm from "../../useForm";
import { useToasts } from "react-toast-notifications";

const styles = theme => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            minWidth: 230,
        }
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 230,
    },
    smMargin: {
        margin: theme.spacing(1)
    }
})

const initialFieldValues = {
    userName: '',
    password: ''
};

const Login = ({classes, ...props}) => {

    const { addToast } = useToasts()

    const validate = (fieldValues = values) => {
        let temp = { ...errors }
        if ('userName' in fieldValues)
            temp.userName = fieldValues.userName ? "" : "Необходимо заполнить поле"
        if ('password' in fieldValues)
            temp.password = fieldValues.password ? "" : "Необходимо заполнить поле"
        setErrors({
            ...temp
        })

        if (fieldValues == values)
            return Object.values(temp).every(x => x == "")
    }

    const {
        values,
        setValues,
        errors,
        setErrors,
        handleInputChange,
        resetForm
    } = useForm(initialFieldValues, validate)

    
    const handleSubmit = e => {
        e.preventDefault();
        if (validate()) {

            const onSuccess = () => {
                resetForm();
                addToast("Авторизация прошла успешно", { appearance: 'success' })
            };

            const onFail = (error) => {
                addToast("Ошибка авторизации", { appearance: 'error' })
            }
            props.login(values, onSuccess, onFail);
        }
    }
    
    return (
        <form autoComplete="off" noValidate className={classes.root} onSubmit={handleSubmit}>
            <Grid
                container
				direction='column'
				justify='center'
				alignItems='center'
            >
                <Grid item xs={6}>
                    <TextField
                        name="userName"
                        variant="outlined"
                        label="Имя пользователя"
                        value={values.userName}
                        onChange={handleInputChange}
                        {...(errors.userName && {error : true, helperText: errors.userName })}
                    />
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        name="password"
                        variant="outlined"
                        label="Пароль"
                        value={values.password}
                        onChange={handleInputChange}
                        {...(errors.password && {error : true, helperText: errors.password })}
                    />
                </Grid>
                <Grid item xs={6}>
                    <Button
                        variant="contained"
                        color="primary"
                        type="submit"
                        className={classes.smMargin}
                    >
                        Войти
                    </Button>
                </Grid>
            </Grid>
        </form>
    );
}

const mapStateToProps = (state) => ({
    userName: state.account.userName,
    role: state.account.role,
    accessToken: state.account.accessToken,
    refreshToken: state.account.refreshToken,
    isLoggedIn: state.account.isLoggedIn
});

const mapActionToProps = {
    login: Actions.login
};

export default connect(mapStateToProps, mapActionToProps)(withStyles(styles)(Login));