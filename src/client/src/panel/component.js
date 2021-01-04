import React, { useState, useEffect } from "react";
import { connect } from "react-redux";
import * as Actions from './actions';
import {
	TableContainer,
	Table,
	TableBody,
	TableRow,
	TableCell,
    Paper,
    Grid,
    withStyles,
	TableHead,
} from "@material-ui/core";


const styles = theme => ({
    root: {
        "& .MuiTableCell-head": {
            fontSize: "1.25rem"
        }
    },
    paper: {
        margin: theme.spacing(2),
        padding: theme.spacing(2)
    },
    cell_long: {
        fontSize: "15px",
        width: 600,
        minWidth: 1
    },
    cell_short: {
        fontSize: "15px",
        width: 100,
        verticalAlign: 'top'
    },
});

const Panel = ({ classes, ...props}) => {

    useEffect(() => {
        setTimeout(() => {
            props.getAllLogs();
        }, 1000);
    });

    return (
        <Paper className={classes.paper} elevation={3}>
            <Grid container>
                <Grid item xs={6}>
                    <TableContainer>
                        <Table>
                            <TableHead className={classes.root}>
                                <TableRow>
                                    <TableCell className={classes.cell_short}>Дата</TableCell>
                                    <TableCell className={classes.cell_long}>Значение</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {
                                    props.logList.map((record, index) => {
                                        return (
                                            <TableRow key={index} hover>
                                                <TableCell className={classes.cell_short}>{record.created}</TableCell>
                                                <TableCell className={classes.cell_long}>{record.value}</TableCell>
                                            </TableRow>
                                        );
                                    })
                                }
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Grid>
            </Grid>
        </Paper>
    )
}

const mapStateToProps = (state) => ({
    logList: state.panel.list
});

const mapActionToProps = {
    getAllLogs: Actions.getAll
};

export default connect(mapStateToProps, mapActionToProps)(withStyles(styles)(Panel));