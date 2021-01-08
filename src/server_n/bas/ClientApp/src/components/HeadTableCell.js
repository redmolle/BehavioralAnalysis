import React from "react";
import {
	TableCell,
	withStyles,
	TableSortLabel,
} from "@material-ui/core";

const styles = (theme) => ({
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
	}
});

const HeadTableCell = ({ classes, ...props }) => {
	const { name, label, order, orderBy, handleRequestSort } = props;

	return (
		<TableCell key={name} sortDirection={orderBy === name ? order : false}>
			<TableSortLabel
				active={orderBy === name}
				direction={orderBy === name ? order : "asc"}
				onClick={handleRequestSort(name)}>
				{props.children}
				{orderBy === name ? (
					<span className={classes.visuallyHidden}>
						{order === "desc"
							? "отсортировать по убыванию"
							: "Отсортировать по возрастанию"}
					</span>
				) : null}
			</TableSortLabel>
		</TableCell>
	);
};

export default withStyles(styles)(HeadTableCell);
