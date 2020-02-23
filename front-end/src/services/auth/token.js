import base64url from 'base64-url';


export const parseJwtToken = function(jwt) {
  const parts = jwt.split('.');

  if (parts.length !== 3) {
    // TODO
  }

  const claims = JSON.parse(base64url.decode(parts[1]));

  return claims;
};
