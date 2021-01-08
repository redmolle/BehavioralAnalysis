import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import * as DashboardService from "../store/Dash";
import * as AccountService from "../store/Account";
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
	Typography,
	Grid,
	Button,
	Card,
	CardActions,
	CardContent,
} from "@material-ui/core";
import { toast } from "react-toastify";
import ReactJson from "react-json-view";
import useRouting, { routes } from "../routes";

const styles = (theme) => ({
	root: {
		width: "100%",
	},
	paper: {
		margin: theme.spacing(2),
		padding: theme.spacing(2),
		width: "100%",
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
		minWidth: 1,
	},
	cell_short: {
		fontSize: "15px",
		width: 100,
		verticalAlign: "top",
	},
	smMargin: {
		margin: theme.spacing(1),
	},
});

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

const Log = (props) => {
	const { label } = props;

	const [anchorEl, setAnchorEl] = useState(null);

	const handleClick = (event) => {
		if (anchorEl) {
			handleClose();
		}
		setAnchorEl(event.currentTarget);
	};

	const handleClose = () => {
		setAnchorEl(null);
	};

	const open = Boolean(anchorEl);

	return (
		<div>
			<Typography
				aria-owns={open ? "mouse-over-popover" : undefined}
				aria-haspopup='true'
				onClick={handleClick}>
				{label}
			</Typography>

			{open && (
				<Card>
					<CardActions>
						<Button size='small' color='primary' onClick={handleClose}>
							Закрыть
						</Button>
					</CardActions>
					<CardContent>{props.children}</CardContent>
				</Card>
			)}
		</div>
	);
};

const headCells = [
	{ key: "created", label: "Дата" },
	{ key: "deviceId", label: "Устройство" },
	{ key: "type", label: "Тип" },
	{ key: "value", label: "Лог" },
];

const Dashboard = ({ classes, ...props }) => {
	const refresh = () => {
		if (!props.accessToken) {
			return;
		}

		const onSuccess = () => {};

		const onFail = (error) => {
			const onError = (error) => {
				toast.error("Произошла ошибка во время получения данных");
			};

			const onRefresh = () => {
				props.getAllLogs(props.accessToken, onSuccess, onError);
			};

			if (
				error &&
				error.response &&
				error.response.status &&
				error.response.status === 401 &&
				props.accessToken &&
				props.accessToken !== ""
			) {
				props.refresh(
					props.accessToken,
					props.refreshToken,
					onRefresh,
					onError
				);
			} else {
				onError(undefined);
			}
		};

		props.getAllLogs(props.accessToken, onSuccess, onFail);
	};

	useEffect(() => {
		setTimeout(() => {
			refresh();
		}, 1000);
	});

	const [order, setOrder] = useState("asc");
	const [orderBy, setOrderBy] = useState("calories");
	const [page, setPage] = useState(0);
	const [dense, setDense] = useState(false);
	const [rowsPerPage, setRowsPerPage] = useState(5);

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
		rowsPerPage -
		Math.min(rowsPerPage, props.logList.length - page * rowsPerPage);

	const { changeRoute } = useRouting();

	const handleSubmit = (e) => {
		e.preventDefault();
		changeRoute(routes.account);
	};

	return (
		<Grid
			container
			direction='column'
			justify='center'
			alignItems='center'
			spacing={3}>
			<Grid item xs={12}>
				<Paper
					style={{ height: 500, width: "100%" }}
					className={classes.paper}
					elevation={3}>
					<TableContainer>
						<Table
							className={classes.table}
							aria-labelledby='tableTitle'
							size={dense ? "small" : "medium"}
							aria-label='enhanced table'>
							<TableHead stickyHeader>
								<TableRow>
									{headCells.map((head, index) => {
										return (
											<TableCell
												key={index}
												sortDirection={orderBy === head.key ? order : false}>
												<TableSortLabel
													active={orderBy === head.key}
													direction={orderBy === head.key ? order : "asc"}
													onClick={handleRequestSort(head.key)}>
													{head.label}
													{orderBy === head.key ? (
														<span className={classes.visuallyHidden}>
															{order === "desc"
																? "отсортировать по убыванию"
																: "Отсортировать по возрастанию"}
														</span>
													) : null}
												</TableSortLabel>
											</TableCell>
										);
									})}
								</TableRow>
							</TableHead>
							<TableBody>
							{stableSort(props.logList, getComparator(order, orderBy))
								.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
								.map((row, rindex) => {
									return (
										<TableRow hover key={rindex}>
											<TableCell>{row.created}</TableCell>
											<TableCell>{row.deviceId}</TableCell>
											<TableCell>{row.type}</TableCell>
											<TableCell>
												<Log label={row.value.substring(0, 20)}>
													<ReactJson src={JSON.parse(row.value)} />
												</Log>
											</TableCell>
										</TableRow>
									);
								})}
							{emptyRows > 0 && (
								<TableRow style={{ height: (dense ? 33 : 53) * emptyRows }}>
									<TableCell colSpan={6} />
								</TableRow>
							)}
						</TableBody>
						</Table>
					</TableContainer>
					<TablePagination
						rowsPerPageOptions={[5, 25, 50, 100]}
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
				</Paper>
			</Grid>
			{!props.isLogged && (
				<Grid item xs={12}>
					<form
						autoComplete='off'
						noValidate
						className={classes.root}
						onSubmit={handleSubmit}>
						<Button
							variant='contained'
							color='primary'
							type='submit'
							className={classes.smMargin}>
							Авторизироваться
						</Button>
					</form>
				</Grid>
			)}
		</Grid>
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
	refresh: AccountService.refresh,
};

export default connect(
	mapStateToProps,
	mapActionToProps
)(withStyles(styles)(Dashboard));
