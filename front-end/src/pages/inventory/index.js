import React from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';


import routes from './menus';


const Default = () => (
  <Switch>

    {routes.map((route, index) => (
      route.component &&
      <Route
        key={ index }
        path={ route.path }
        component={ route.component }
      />
    ))}

    <Redirect to={'/app/inventory/storages'} />
  </Switch>
);


export default Default;
