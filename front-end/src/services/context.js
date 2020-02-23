
export const isRememberMe = function() {
  const rememberMe = localStorage.getItem('remember_me');
  if (rememberMe) {
    return true;
  }
  return false;
};

export const setRememberMe = function(checked) {
  if (checked) {
    localStorage.setItem('remember_me', 'true');
  } else {
    localStorage.removeItem('remember_me');
  }
};


const storage = function() {
  return isRememberMe() ? localStorage : sessionStorage;
};


export const clear = function() {
  storage().clear();
};


export const getUserId = function() {
  return storage().getItem('user_id');
};

export const setUserId = function(userId) {
  storage().setItem('user_id', userId);
};

export const removeUserId = function() {
  storage().removeItem('user_id');
};


export const getUserJwt = function() {
  return storage().getItem('user_jwt');
};

export const setUserJwt = function(jwt) {
  storage().setItem('user_jwt', jwt);
};

export const removeUserJwt = function() {
  storage().removeItem('user_jwt');
};


const context = {
  isRememberMe,
  setRememberMe,
  getUserId,
  setUserId,
  removeUserId,
  getUserJwt,
  setUserJwt,
  removeUserJwt,
  clear
};


export default context;
