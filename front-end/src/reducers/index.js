/**
 * App Reducers
 */
import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';


import appSettings from './appSettings';


const reducers = (history) => combineReducers({
  router: connectRouter(history),
  appSettings
});


export default reducers;
