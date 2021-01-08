import React from "react";
import Grid from "@material-ui/core/Grid";

const GridContainer = ({ classes, ...props }) => {
	return (
		<Grid
			container
			direction='column'
			justify='center'
			alignItems='center'
			spacing={3}
			{...props}>
			{props.children}
		</Grid>
	);
};

export default GridContainer;
