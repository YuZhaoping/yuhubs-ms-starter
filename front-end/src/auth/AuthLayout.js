import React from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';

import withWidth, { isWidthUp } from '@material-ui/core/withWidth';


import Header from 'Components/Header';
import Footer from 'Components/Footer';

import {
  AppContent,
  MainContent
} from '../app/appStyles';


import async from 'Components/Async';

const SignInPage = async(() => import(/* webpackChunkName: "auth-signin" */ './SignIn'));


const AuthLayout = (props) => {
  const { width } = props;

  return (
    <AppContent>

      <Header />

      <MainContent p={isWidthUp("md", width) ? 8 : 6}>
        <Switch>
          <Route path="/auth/signin" component={ SignInPage } />

          <Redirect to="/app" />
        </Switch>
      </MainContent>

      <Footer />

    </AppContent>
  );
};


export default withWidth()(AuthLayout);
