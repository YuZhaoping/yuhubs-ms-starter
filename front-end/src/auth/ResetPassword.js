import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';


import withStyles from '@material-ui/styles/withStyles';

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Link from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';


import ResetPasswordForm from './forms/ResetPasswordForm';


import MessageBar from 'Components/MessageBar';
import Loading from 'Components/Loading';
import Copyright from 'Components/Copyright';

import useStyles from './authStyles';


import authService from 'Services/auth';

import {
  setAuthUsername
} from 'Actions/auth';


const isStatusOk = function(status) {
  return (status > 199 && status < 400);
};


const ResetPassword = (props) => {
  const { classes } = props;


  const [resp, setResp] = useState(() => ({
    loading: false,
    status: 0,
    error: null
  }));


  const setBusy = () => {
    setResp({ ...resp, loading: true });
  };


  const onSubmit = formData => {
    setBusy();

    authService.resetPassword(formData)
      .then(status => {
        props.setAuthUsername(formData.email);
        setResp({...resp, loading: false, status});
      })
      .catch(error => {
        setResp({...resp, loading: false, error});
      });
  };


  const cleanRespError = () => {
    setResp({ ...resp, error: null });
  };


  if (isStatusOk(resp.status)) {
    return ( <Redirect to="/auth/signin" /> );
  }

  return (
    <Container component="main" maxWidth="xs">

      <div className={classes.paper}>

        <Typography component="h1" variant="h5">
          Reset password
        </Typography>

        { resp.error &&
          <MessageBar className={ classes.message }
            variant="error"
            onClose={ cleanRespError }
            message={ resp.error.toString() }
          />
        }

        <ResetPasswordForm
          classes={ classes }
          onSubmit= { onSubmit }
        />

        <Grid container>
          <Grid item xs>
            <Link
              component={ RouterLink }
              to="/auth/signin"
              variant="body2"
            >
              {"Sign in"}
            </Link>
          </Grid>

          <Grid item>
            <Link
              component={ RouterLink }
              to="/auth/signup"
              variant="body2"
            >
              {"Don't have an account? Sign up"}
            </Link>
          </Grid>
        </Grid>

      </div>

      <Box mt={5}>
        <br/>
        <br/>
        <Copyright />
      </Box>

      { resp.loading && <Loading /> }
    </Container>
  );
};


const mapStateToProps = ({ }) => {
  return { };
};

export default withStyles(useStyles)(connect(mapStateToProps, {
  setAuthUsername
})(ResetPassword));
