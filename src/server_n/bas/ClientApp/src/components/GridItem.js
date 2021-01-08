import React from "react";
import Grid from "@material-ui/core/Grid";

const GridItem = ({ classes, ...props }) => {
	return (
		<Grid item xs={12} {...props}>
			{props.children}
		</Grid>
	);
};

export default GridItem;
