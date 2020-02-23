import {
  createGetInit,
  createPostJSONInit,
  createPutJSONInit
} from 'Services/util/requests';

import {
  checkStatus,
  parseJSON,
  parseApiData
} from 'Services/util/responses';


import {
  parseJwtToken
} from './token';

import {
  tokenAuthUser,
  plainAuthUser
} from './user';


import context from 'Services/context';


const authBaseUrl = '/auth/api';

const signInUrl = authBaseUrl + '/login';
const signOutUrl = authBaseUrl + '/logout';
const signUpUrl = authBaseUrl + '/users';

const resetPasswordUrl = authBaseUrl + '/reset_password';

const refreshTokenUrl = authBaseUrl + '/refresh_token';


const X_SET_AUTHORIZATION_BEARER = 'X-Set-Authorization-Bearer';


export const loadAuthUser = function() {
  const jwt = context.getUserJwt();
  if (jwt) {
    const authUser = tokenAuthUser(parseJwtToken(jwt));
    return authUser;
  }
  return null;
};

export const cleanAuthUser = function() {
  context.removeUserJwt();
  context.removeUserId();
};


const hasUserId = function(authUser) {
  return (authUser.id || authUser.id === 0);
};

const doSign = function(url, req) {
  return new Promise((resolve, reject) => {

    const init = createPostJSONInit(req);

    fetch(url, init)
      .then(checkStatus)
      .then(function(resp) {

        const jwt = resp.headers.get(X_SET_AUTHORIZATION_BEARER);
        if (jwt) {
          const authUser = tokenAuthUser(parseJwtToken(jwt));

          context.setUserId(authUser.id);
          context.setUserJwt(jwt);

          resolve(authUser);
          return;
        }

        parseJSON(resp).then(parseApiData)
          .then(function(apiData) {
            const authUser = plainAuthUser(apiData, req.username);

            if (hasUserId(authUser)) {
              context.setUserId(authUser.id);
            }

            resolve(authUser);
          })
          .catch(function(error) {
            reject(error);
          });

      })
      .catch(function(error) {
        reject(error);
      });
  });
};

export const signIn = function(req) {
  return doSign(signInUrl, req);
};

export const signUp = function(req) {
  return doSign(signUpUrl, req);
};


export const signOut = function(req) {
  return new Promise(function(resolve, reject) {

    if (!context.getUserJwt()) {
      resolve({});
      return;
    }

    const init = createGetInit();

    fetch(signOutUrl, init)
      .then(function(resp) {
        cleanAuthUser();
        resolve({});
      })
      .catch(function(error) {
        cleanAuthUser();
        reject(error);
      })
  });
};


export const resetPassword = function(req) {
  return new Promise(function(resolve, reject) {

    const init = createPutJSONInit(req);

    fetch(resetPasswordUrl, init)
      .then(function(resp) {
        resolve(resp.status);
      })
      .catch(function(error) {
        reject(error);
      })
  });
};


export const refreshToken = function() {
  return new Promise(function(resolve, reject) {

    const init = createGetInit();

    fetch(refreshTokenUrl, init)
      .then(checkStatus)
      .then(function(resp) {

        const jwt = resp.headers.get(X_SET_AUTHORIZATION_BEARER);
        if (jwt) {
          const authUser = tokenAuthUser(parseJwtToken(jwt));

          context.setUserJwt(jwt);

          resolve(authUser);
          return;
        }

        parseJSON(resp).then(parseApiData)
          .then(function() {
            resolve(null);
          })
          .catch(function(error) {
            reject(error);
          });

      })
      .catch(function(error) {
        reject(error);
      });
  });
};


const auth = {
  loadAuthUser,
  cleanAuthUser,
  signIn,
  signOut,
  signUp,
  resetPassword,
  refreshToken
};

export default auth;
