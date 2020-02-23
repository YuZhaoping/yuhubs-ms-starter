import { all } from 'redux-saga/effects';


import errorsSaga from './errors';
import authSaga from './auth';


export default function* rootSaga(getState) {
  yield all([
    errorsSaga(),
    authSaga()
  ]);
}
