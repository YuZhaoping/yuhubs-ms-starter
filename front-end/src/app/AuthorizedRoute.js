import React from 'react';
import { Redirect, Route } from 'react-router-dom';


const AuthorizedRoute = ({ component: Component, authUser, ...rest }) => (
  <Route { ...rest }
    render = { props => authUser
      ? <Component {...props} authUser={ authUser } />
      : <Redirect to={{
          pathname: '/auth/signin',
          state: {from: props.location}
        }} />
    }
  />
);

export default AuthorizedRoute;
