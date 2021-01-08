import React from "react";
import {
    Button,
    withStyles
} from "@material-ui/core";
import useRouting, { routes } from "../routes";
import GridContainer from "../components/GridContainer";
import GridItem from "../components/GridItem";


const styles = theme => ({
    root: {
        "& .MuiTableCell-head": {
            fontSize: "1.25rem"
        }
    },
});

const LoginRedirect = ({ classes, ...props}) => {

    const {
        changeRoute
    } = useRouting();

    const handleSubmit = (e) => {
        e.preventDefault();
        changeRoute(routes.account)
    }

    return (
                <form
				autoComplete='off'
				noValidate
				className={classes.root}
				onSubmit={handleSubmit}>
				<GridContainer>
					<GridItem>
						<Button
							variant='contained'
							color='primary'
							type='submit'
							className={classes.smMargin}>
							Авторизироваться
						</Button>
					</GridItem>
				</GridContainer>
			</form>
    )
}

export default (withStyles(styles)(LoginRedirect));