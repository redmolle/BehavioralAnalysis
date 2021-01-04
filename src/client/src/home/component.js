import React from "react";
import { withStyles, Paper } from "@material-ui/core";
import Panel from "../panel/component";
import Login from "../account/login/component";
import { connect } from "react-redux";

const styles = theme => ({
    paper: {
        margin: theme.spacing(2),
        padding: theme.spacing(2)
    }
});

const Home = ({ classes, ...props }) => {
    return (
        <Paper className={classes.paper}>
            {props.isLoggedIn ? <Panel/> : <Login/>}
        </Paper>
    )
};

const mapStateToProps = (state) => ({
    isLoggedIn: state.account.isLoggedIn
});

const mapActionToProps = {
};

export default connect(mapStateToProps, mapActionToProps)(withStyles(styles)(Home));