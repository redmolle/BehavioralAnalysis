import React, { useEffect } from "react";
import { connect } from "react-redux";
import * as DashboardService from "../services/DashboardService";
import * as AccountService from "../services/AccountService";
import {
	TableContainer,
	Table,
	TableBody,
	TableRow,
	TableCell,
	withStyles,
	TableHead,
	FormControlLabel,
	TablePagination,
	Paper,
	TableSortLabel,
	Switch,
} from "@material-ui/core";
import { toast } from "react-toastify";
import GridContainer from "../components/GridContainer";
import GridItem from "../components/GridItem";
import LoginRedirect from "../components/LoginRedirect";

const styles = (theme) => ({
	root: {
		width: "100%",
	},
	paper: {
		width: "100%",
		marginBottom: theme.spacing(2),
	},
	table: {
		minWidth: 750,
		maxWidth: 750,
	},
	visuallyHidden: {
		border: 0,
		clip: "rect(0 0 0 0)",
		height: 1,
		margin: -1,
		overflow: "hidden",
		padding: 0,
		position: "absolute",
		top: 20,
		width: 1,
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

const headCells = [
	{
		id: "created",
		isMain: true,
		numeric: false,
		disablePadding: true,
        label: "Дата",
	},
	{ id: "type", numeric: false, disablePadding: false, label: "Тип" },
	{ id: "value", numeric: false, disablePadding: false, label: "Лог" },
];

const descendingComparator = (a, b, orderBy) => {
	if (b[orderBy] < a[orderBy]) {
		return -1;
	}
	if (b[orderBy] > a[orderBy]) {
		return 1;
	}
	return 0;
};

const getComparator = (order, orderBy) => {
	return order === "desc"
		? (a, b) => descendingComparator(a, b, orderBy)
		: (a, b) => -descendingComparator(a, b, orderBy);
};

const stableSort = (array, comparator) => {
	const stabilizedThis = array.map((el, index) => [el, index]);
	stabilizedThis.sort((a, b) => {
		const order = comparator(a[0], b[0]);
		if (order !== 0) return order;
		return a[1] - b[1];
	});
	return stabilizedThis.map((el) => el[0]);
};

const Dashboard = ({ classes, ...props }) => {
	const refresh = () => {
		const onSuccess = () => {
        };
        
        const onFail = (error) => {
            
            const onError = (error) => {
                toast.error("Произошла ошибка во время получения данных")
            }

            const onRefresh = () => {
                props.getAllLogs(props.accessToken, onSuccess, onError);
            }

            if (error &&
                error.response &&
                error.response.status &&
                error.response.status === 401 &&
                props.accessToken &&
                props.accessToken !== ""){
                    props.refresh(props.accessToken, props.refreshToken, onRefresh, onError);
                }
            else {
                onError(undefined);
            }
        }

        props.getAllLogs(props.accessToken, onSuccess, onFail);
	};

	useEffect(() => {
		setTimeout(() => {
            refresh();
		}, 1000);
    });
    
	const [order, setOrder] = React.useState("asc");
	const [orderBy, setOrderBy] = React.useState("calories");
	const [page, setPage] = React.useState(0);
	const [dense, setDense] = React.useState(false);
	const [rowsPerPage, setRowsPerPage] = React.useState(5);

	const handleRequestSort = (property) => (event) => {
		const isAsc = orderBy === property && order === "asc";
		setOrder(isAsc ? "desc" : "asc");
		setOrderBy(property);
	};

	const handleChangePage = (event, newPage) => {
		setPage(newPage);
	};

	const handleChangeRowsPerPage = (event) => {
		setRowsPerPage(parseInt(event.target.value, 10));
		setPage(0);
	};

	const handleChangeDense = (event) => {
		setDense(event.target.checked);
	};

	const emptyRows =
		rowsPerPage - Math.min(rowsPerPage, props.logList.length - page * rowsPerPage);

	return (
		<GridContainer>
			<GridItem>
				{!props.isLoggedIn ? (
					<LoginRedirect />
				) : (
					<div className={classes.root}>
							<TableContainer>
								<Table
									className={classes.table}
									aria-labelledby='tableTitle'
									size={dense ? "small" : "medium"}
									aria-label='enhanced table'
                                    >
									<TableHead>
										<TableRow>
											{headCells.map((headCell) => (
												<TableCell
													key={headCell.id}
													align={headCell.numeric ? "right" : "left"}
													padding={headCell.disablePadding ? "none" : "default"}
													sortDirection={
														orderBy === headCell.id ? order : false
													}>
													<TableSortLabel
														active={orderBy === headCell.id}
														direction={orderBy === headCell.id ? order : "asc"}
														onClick={handleRequestSort(headCell.id)}>
														{headCell.label}
														{orderBy === headCell.id ? (
															<span className={classes.visuallyHidden}>
																{order === "desc"
																	? "отсортировать по убыванию"
																	: "Отсортировать по возрастанию"}
															</span>
														) : null}
													</TableSortLabel>
												</TableCell>
											))}
										</TableRow>
									</TableHead>
									<TableBody>
										{stableSort(props.logList, getComparator(order, orderBy))
											.slice(
												page * rowsPerPage,
												page * rowsPerPage + rowsPerPage
											)
											.map((row, rindex) => {
												const labelId = `enhanced-table-checkbox-${rindex}`;

												return (
													<TableRow hover key={rindex}>
														{headCells.map((column, cindex) => {
															return (
																<TableCell key={`${rindex}.${cindex}`} className={classes.cell_short}>
																	{row[column.id]}
																</TableCell>
															);
														})}
													</TableRow>
												);
											})}
										{emptyRows > 0 && (
											<TableRow
												style={{ height: (dense ? 33 : 53) * emptyRows }}>
												<TableCell colSpan={6} />
											</TableRow>
										)}
									</TableBody>
								</Table>
							</TableContainer>
							<TablePagination
								rowsPerPageOptions={[5, 10, 25]}
								component='div'
								count={props.logList.length}
								rowsPerPage={rowsPerPage}
								page={page}
								onChangePage={handleChangePage}
								onChangeRowsPerPage={handleChangeRowsPerPage}
							/>
						<FormControlLabel
							control={<Switch checked={dense} onChange={handleChangeDense} />}
							label='Сократить'
						/>
					</div>
				)}
			</GridItem>
		</GridContainer>
	);
};

const mapStateToProps = (state) => ({
	logList: state.dashboard.list,
	isLoggedIn: state.account.isLoggedIn,
	accessToken: state.account.accessToken,
	refreshToken: state.account.refreshToken,
});

const mapActionToProps = {
    getAllLogs: DashboardService.getAllLogs,
    refresh: AccountService.refresh
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(Dashboard));
