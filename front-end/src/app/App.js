import React from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { connect } from 'react-redux';


import 'Assets/Css';

import AppThemeProvider from './AppThemeProvider';

import { Root } from './appStyles';

import AuthorizedRoute from './AuthorizedRoute';

import AppLayout from './AppLayout';
import AuthLayout from '../auth/AuthLayout';

import ErrorHandler from './ErrorHandler';


import CssBaseline from '@material-ui/core/CssBaseline';


const App = (props) => {
  const { authUser } = props;

  return (
    <AppThemeProvider>

      <CssBaseline />

      <Root>

        <ErrorHandler />

        <Switch>
          <AuthorizedRoute
            path="/app"
            component={ AppLayout }
            authUser={ authUser }
          />

          <Route
            path="/auth"
            component={ AuthLayout }
          />

          <Redirect to="/app" />
        </Switch>

      </Root>

    </AppThemeProvider>
  );
};


const mapStateToProps = ({ auth }) => {
  return { authUser: auth.user };
};

export default connect(mapStateToProps, {
})(App);
