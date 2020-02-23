/**
 * Redux Store
 */
import { createStore, applyMiddleware, compose } from 'redux';
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';

import createSagaMiddleware from 'redux-saga';


import reducers from '../reducers';
import rootSaga from '../sagas';

import { startAuthAction } from '../reducers/auth';


const history = createBrowserHistory();
const routeMiddleware = routerMiddleware(history);

const sagaMiddleware = createSagaMiddleware();

const middlewares = [sagaMiddleware, routeMiddleware];


export default function configureStore(initialState) {
  const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

  const store = createStore(
    reducers(history),
    initialState,
    composeEnhancer(
      applyMiddleware(...middlewares)
    )
  );

	sagaMiddleware.run(rootSaga);

  if (module.hot) {
    // Enable Webpack hot module replacement for reducers
    module.hot.accept('../reducers/index', () => {
      const nextRootReducer = require('../reducers/index');
      store.replaceReducer(nextRootReducer(history));
    });
  }

  startAuthAction(store);

  return store;
}


export { history };
