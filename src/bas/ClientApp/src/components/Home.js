import React from "react";
import { withStyles, Typography } from "@material-ui/core";

const styles = (theme) => ({
	paper: {
		margin: theme.spacing(2),
		padding: theme.spacing(2),
	},
});

const Home = ({ classes, ...props }) => {
	return (
		<Typography>
			Система поведенческого анализа мобильных устройств на ОС Android
		</Typography>
	);
};

export default withStyles(styles)(Home);
