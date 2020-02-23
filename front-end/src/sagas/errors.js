//import 'regenerator-runtime/runtime';

import { all, fork, put, takeEvery } from 'redux-saga/effects';


import {
  PUBLISH_ERROR,
  ADD_ERROR,
  REMOVE_ERROR
} from 'Constants/errors';

import {
  addError as addErrorAction,
  addErrorToList,
  removeErrorFromList,
  decreaseErrorCount,
  onAddError
} from 'Actions/errors';


function* doPublishError({ error, nextAction }) {
  if (nextAction) {
    yield put(nextAction(error));
  }
  yield put(addErrorAction(error));
}

function* asyncPublishError(action) {
  yield fork(doPublishError, action);
};

export function* publishError() {
  yield takeEvery(PUBLISH_ERROR, asyncPublishError);
}


function* doAddError({ error }) {
  yield put(addErrorToList(error));
  yield put(onAddError(error));
}

export function* addError() {
  yield takeEvery(ADD_ERROR, doAddError);
}


function* doRemoveError({ error }) {
  yield put(removeErrorFromList(error));
  yield put(decreaseErrorCount());
}

export function* removeError() {
  yield takeEvery(REMOVE_ERROR, doRemoveError);
}


/**
 * Errors Root Saga
 */
export default function* errorsSaga() {
  yield all([
    fork(publishError),
    fork(addError),
    fork(removeError)
  ]);
}
