import React, { useEffect } from 'react';
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


import SignInForm from './forms/SignInForm';


import MessageBar from 'Components/MessageBar';
import Loading from 'Components/Loading';
import Copyright from 'Components/Copyright';


import {
  signInUser,
  signOutUser,
  cleanAuthError
} from 'Actions/auth';


import useStyles from './authStyles';


const pathOf = function(fromPath) {
  return fromPath ? fromPath : '/app';
};


const SignIn = (props) => {
  const { classes, auth, location } = props;

  const search = location.search;

  useEffect(() => {
    if ('?logout' === search) {
      const authUser = auth.user;
      delete auth.user;
      props.signOutUser(authUser);
    }
  }, [ search ]);


  const onSubmit = formData => {
    props.signInUser(formData);
  };


  if (auth.user) {
    return ( <Redirect to={ pathOf(auth.fromPath) }/> );
  }

  return (
    <Container component="main" maxWidth="xs">

      <div className={classes.paper}>

        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>

        <Typography component="h1" variant="h5">
          Sign in
        </Typography>

        { auth.error &&
          <MessageBar className={ classes.message }
            variant="error"
            onClose={ props.cleanAuthError }
            message={ auth.error.toString() }
          />
        }

        <SignInForm
          classes={ classes }
          auth={ auth }
          onSubmit= { onSubmit }
        />

        <Grid container>
          <Grid item xs>
            <Link
              component={ RouterLink }
              to="/auth/reset_password"
              variant="body2"
            >
              {"Forgot password?"}
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

      { auth.loading && <Loading /> }
    </Container>
  );
};


const mapStateToProps = ({ auth }) => {
  return { auth };
};

export default withStyles(useStyles)(connect(mapStateToProps, {
  signInUser,
  signOutUser,
  cleanAuthError
})(SignIn));
