/**
 * App Reducers
 */
import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';


import customizings from './customizings';

import appSettings from './appSettings';


const reducers = (history) => combineReducers({
  router: connectRouter(history),
  appSettings,

  customizings
});


export default reducers;
