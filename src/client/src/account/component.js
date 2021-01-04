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
import Login from "./login/component";
import Logout from "./logout/component";

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

const Account = ({classes, ...props}) => {

    return (
        <div>
            {
                props.isLoggedIn ? <Logout/> : <Login/>
            }
        </div>
    )
}

const mapStateToProps = (state) => ({
    isLoggedIn: state.account.isLoggedIn
});

const mapActionToProps = {
};

export default connect(mapStateToProps, mapActionToProps)(withStyles(styles)(Account));