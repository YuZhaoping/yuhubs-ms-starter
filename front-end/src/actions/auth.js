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
  CLEAN_AUTH_ERROR
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
