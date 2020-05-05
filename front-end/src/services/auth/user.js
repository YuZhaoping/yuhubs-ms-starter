
export const tokenAuthUser = function(claims) {
  const profile = claims.prof;

  let exp = claims.exp;
  if (exp) {
    exp *= 1000;
  }

  return {
    id: claims.sub,
    permissions: claims.auth,
    name: profile.name,
    groups: profile.groups,
    expiration: exp
  }
};

export const plainAuthUser = function(authUser, username) {
  if (authUser.name && authUser.name.length > 0) {
    return authUser;
  }

  return {
    ...authUser,
    name: username
  }
};
