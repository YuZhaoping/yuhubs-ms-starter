import { all } from 'redux-saga/effects';


import errorsSaga from './errors';


export default function* rootSaga(getState) {
  yield all([
    errorsSaga()
  ]);
}
