import React from 'react';
import { Provider } from 'react-redux';

import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router-dom';


// app root component
import App from './app/App';

import configureStore, { history } from './store';


const store = configureStore();

const MainApp = () => (
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <Switch>
        <Route path="/" component={App} />
      </Switch>
    </ConnectedRouter>
  </Provider>
);


export default MainApp;
