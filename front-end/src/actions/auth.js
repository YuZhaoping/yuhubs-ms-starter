import {
  SIGNIN_USER,
  SIGNIN_USER_SUCCESS,
  SIGNIN_USER_FAILURE,
  SIGNOUT_USER,
  SIGNOUT_USER_SUCCESS,
  SIGNOUT_USER_FAILURE,
  SIGNUP_USER,
  SIGNUP_USER_SUCCESS,
  SIGNUP_USER_FAILURE,
  CLEAN_AUTH_ERROR,
  START_REFRESH_TOKEN_TASK,
  CANCEL_REFRESH_TOKEN_TASK,
  CLEAN_AUTH_USER,
  MARK_AUTH_STATE,
  SET_AUTH_USERNAME
} from 'Constants/auth';


export const signInUser = (req) => ({
  type: SIGNIN_USER,
  payload: req
});

export const signInUserSuccess = (authUser) => ({
  type: SIGNIN_USER_SUCCESS,
  payload: authUser
});

export const signInUserFailure = (error) => ({
  type: SIGNIN_USER_FAILURE,
  payload: error
});


export const signOutUser = (authUser) => ({
  type: SIGNOUT_USER,
  payload: authUser
});

export const signOutUserSuccess = () => ({
  type: SIGNOUT_USER_SUCCESS
});

export const signOutUserFailure = (error) => ({
  type: SIGNOUT_USER_FAILURE,
  payload: error
});


export const signUpUser = (req) => ({
  type: SIGNUP_USER,
  payload: req
});

export const signUpUserSuccess = (authUser) => ({
  type: SIGNUP_USER_SUCCESS,
  payload: authUser
});

export const signUpUserFailure = (error, username) => ({
  type: SIGNUP_USER_FAILURE,
  payload: error,
  username
});


export const cleanAuthError = () => ({
  type: CLEAN_AUTH_ERROR
});


export const startRefreshTokenTaskAction = () => ({
  type: START_REFRESH_TOKEN_TASK
});

export const cancelRefreshTokenTaskAction = () => ({
  type: CANCEL_REFRESH_TOKEN_TASK
});


export const cleanAuthUser = () => ({
  type: CLEAN_AUTH_USER
});


export const markAuthState = (fromPath, error) => ({
  type: MARK_AUTH_STATE,
  fromPath,
  error
});


export const setAuthUsername = (username) => ({
  type: SET_AUTH_USERNAME,
  username
});
