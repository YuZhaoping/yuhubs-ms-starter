import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';


import withStyles from '@material-ui/styles/withStyles';

import Container from '@material-ui/core/Container';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Link from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';

import LockOutlinedIcon from '@material-ui/icons/LockOutlined';


import SignUpForm from './forms/SignUpForm';


import MessageBar from 'Components/MessageBar';
import Loading from 'Components/Loading';
import Copyright from 'Components/Copyright';

import useStyles from './authStyles';


import authService from 'Services/auth';

import {
  signUpUserSuccess,
  signUpUserFailure
} from 'Actions/auth';


const hasUserId = function(authUser) {
  return (authUser.id || authUser.id === 0);
};


const SignUp = (props) => {
  const { classes } = props;


  const [auth, setAuth] = useState(() => ({
    loading: false,
    user: null,
    error: null
  }));


  const setBusy = () => {
    setAuth({ ...auth, loading: true });
  };


  const handleAuthUser = authUser => {
    return new Promise(function(resolve, reject) {

      if (hasUserId(authUser)) {
        props.signUpUserSuccess(authUser);
      } else {
        props.signUpUserFailure(null, authUser.name);
      }

      resolve(authUser);
    });
  };

  const handleError = (error, formData) => {
    return new Promise(function(resolve, reject) {

      const statusCode = error.statusCode;

      if (statusCode && statusCode === 401) {
        const username = formData.username;

        props.signUpUserFailure(error, username);

        resolve({name: username});
      } else {
        reject(error);
      }
    });
  };

  const onSubmit = formData => {
    setBusy();

    authService.signUp(formData)
      .then(authUser => {
        handleAuthUser(authUser).then(() => {
          setAuth({...auth, loading: false, user: authUser});
        });
      })
      .catch(error => {
        handleError(error, formData).then(authUser => {
          setAuth({...auth, loading: false, user: authUser});
        }, () => {
          setAuth({...auth, loading: false, error: error});
        });
      });
  };


  const setAuthError = error => {
    setAuth({ ...auth, error });
  };

  const cleanAuthError = () => {
    setAuth({ ...auth, error: null });
  };


  if (auth.user) {
    if (hasUserId(auth.user)) {
      return ( <Redirect to="/app" /> );
    } else {
      return ( <Redirect to="/auth/signin" /> );
    }
  }

  return (
    <Container component="main" maxWidth="xs">

      <div className={classes.paper}>

        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>

        <Typography component="h1" variant="h5">
          Sign up
        </Typography>

        { auth.error &&
          <MessageBar className={ classes.message }
            variant="error"
            onClose={ cleanAuthError }
            message={ auth.error.toString() }
          />
        }

        <SignUpForm
          classes={ classes }
          onSubmit= { onSubmit }
        />

        <Grid container justify="flex-end">
          <Grid item>
            <Link
              component={ RouterLink }
              to="/auth/signin"
              variant="body2"
            >
              {"Already have an account? Sign in"}
            </Link>
          </Grid>
        </Grid>

      </div>

      <Box mt={5}>
        <br/>
        <br/>
        <Copyright />
      </Box>

      { auth.loading && <Loading /> }
    </Container>
  );
};


const mapStateToProps = ({ }) => {
  return { };
};

export default withStyles(useStyles)(connect(mapStateToProps, {
  signUpUserSuccess,
  signUpUserFailure
})(SignUp));
