/**
 * App Reducers
 */
import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';


import auth from './auth';

import {
  errorsProfile,
  errorsList
} from './errors';

import customizings from './customizings';

import appSettings from './appSettings';


const reducers = (history) => combineReducers({
  router: connectRouter(history),
  auth,
  errorsProfile,
  errorsList,
  customizings,
  appSettings
});


export default reducers;
