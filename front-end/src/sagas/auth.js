//import 'regenerator-runtime/runtime';

import { all, call, fork, put, takeLatest, cancel, delay } from 'redux-saga/effects';


import {
  START_REFRESH_TOKEN_TASK,
  CANCEL_REFRESH_TOKEN_TASK,
  SIGNIN_USER,
  SIGNOUT_USER,
  SIGNUP_USER,
  CLEAN_AUTH_USER
} from 'Constants/auth';

import {
  startRefreshTokenTaskAction,
  cancelRefreshTokenTaskAction,
  signInUserSuccess,
  signInUserFailure,
  signOutUserSuccess,
  signOutUserFailure,
  signUpUserSuccess,
  signUpUserFailure
} from 'Actions/auth';


import {
  addError as addErrorAction
} from 'Actions/errors';


import auth from 'Services/auth';


/**
 * Refresh Token
 */

const enableRefreshTokenTask = false;

let refreshTokenTask = null;


function* doCancelRefreshTokenTask() {
  try {
    const task = refreshTokenTask;
    refreshTokenTask = null;

    if (task) {
      console.log('cancelRefreshTokenTask');

      yield cancel(task);
    }
  } catch (error) {
    // ignore
  }
};

export function* cancelRefreshTokenTask() {
  yield takeLatest(CANCEL_REFRESH_TOKEN_TASK, doCancelRefreshTokenTask);
}


const ONE_MINUTE = 60000;
const QUARTER_HOUR = 14 * 60 * 1000;

function getGap(exp) {
  if (!exp) {
    return 0;
  }

  const now = Date.now();

  if (now < exp) {
    let gap = exp - now;

    if (gap > ONE_MINUTE) {
      gap -= ONE_MINUTE;

      if (gap > QUARTER_HOUR) {
        gap -= QUARTER_HOUR;
      } else {
        gap >>>= 1;
      }

      return gap;
    }
  }

  return 0;
}

function* requestRefreshToken() {
  const authUser = yield call(auth.refreshToken);
  if (authUser) {
    yield put(signInUserSuccess(authUser));
  }
}

function* doRefreshTokenTask() {
  try {
    const authUser = auth.loadAuthUser();
    if (authUser) {

      const gap = getGap(authUser.expiration);
      if (gap > 0) {
        console.log('startRefreshTokenTask: ' + gap);

        yield delay(gap);
        yield call(requestRefreshToken);

        // next loop
        yield fork(putStartRefreshTokenAction);
      }
    }
  } catch (error) {
    yield put(addErrorAction(error));
  }
}

function* doStartRefreshTokenTask() {
  try {
    if (enableRefreshTokenTask) {

      yield call(doCancelRefreshTokenTask);

      refreshTokenTask = yield fork(doRefreshTokenTask);
    }
  } catch (error) {
    // ignore
  }
};

export function* startRefreshTokenTask() {
  yield takeLatest(START_REFRESH_TOKEN_TASK, doStartRefreshTokenTask);
}


function* putStartRefreshTokenAction() {
  yield put(startRefreshTokenTaskAction());
}

function* putCancelRefreshTokenAction() {
  yield put(cancelRefreshTokenTaskAction());
}


/**
 * Signin User
 */

function* doSignIn({ payload }) {
  const req = payload;

  try {
    const authUser = yield call(auth.signIn, req);
    yield fork(putStartRefreshTokenAction);
    yield put(signInUserSuccess(authUser));
  } catch (error) {
    yield put(signInUserFailure(error));
  }
}

export function* signInUser() {
  yield takeLatest(SIGNIN_USER, doSignIn);
}


/**
 * Signup User
 */

function* doSignUp({ payload }) {
  const req = payload;

  try {
    const authUser = yield call(auth.signUp, req);
    yield fork(putStartRefreshTokenAction);
    yield put(signUpUserSuccess(authUser));
  } catch (error) {
    yield put(signUpUserFailure(error, req.username));
  }
}

export function* signUpUser() {
  yield takeLatest(SIGNUP_USER, doSignUp);
}


/**
 * Signout User
 */

function* doSignOut({ payload }) {
  const req = payload;

  try {
    yield fork(putCancelRefreshTokenAction);
    yield call(auth.signOut, req);
    yield put(signOutUserSuccess());
  } catch (error) {
    yield put(signOutUserFailure(error));
  }
}

export function* signOutUser() {
  yield takeLatest(SIGNOUT_USER, doSignOut);
}


/**
 * Clean AuthUser
 */

function* doCleanAuthUser() {
  auth.cleanAuthUser();
};

export function* cleanAuthUser() {
  yield takeLatest(CLEAN_AUTH_USER, doCleanAuthUser);
}


/**
 * Auth Root Saga
 */
export default function* authSaga() {
  yield all([
    fork(startRefreshTokenTask),
    fork(cancelRefreshTokenTask),
    fork(signInUser),
    fork(signOutUser),
    fork(signUpUser),
    fork(cleanAuthUser)
  ]);
}
