import React, { useState } from "react";
import {
	Button,
	Card,
	CardActions,
	CardContent,
	Typography,
	withStyles,
} from "@material-ui/core";

const styles = (theme) => ({
	popover: {
		pointerEvents: "none",
	},
	root: {
		minWidth: 275,
	},
	bullet: {
		display: "inline-block",
		margin: "0 2px",
		transform: "scale(0.8)",
	},
	title: {
		fontSize: 14,
	},
	pos: {
		marginBottom: 12,
	},
});

const CustomPopover = ({ classes, ...props }) => {
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
				<Card className={classes.root}>
					<CardActions>
						<Button size='small'
							color='primary'
							 onClick={handleClose}>
							Закрыть
						</Button>
					</CardActions>
					<CardContent>{props.children}</CardContent>
				</Card>
			)}
		</div>
	);
};

export default withStyles(styles)(CustomPopover);
