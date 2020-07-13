import context from 'Services/context';


const baseInit = {
  credentials: 'include'
};


export function initHeaders() {
  const jwt = context.getUserJwt();
  return jwt ? {
    'Accept': 'application/json',
    'Authorization': 'Bearer ' + jwt
  } : {
    'Accept': 'application/json'
  };
}


export function createGetInit() {
  const headers = initHeaders();

  return {
    ...baseInit,
    headers: headers,
    method: 'GET'
  };
}


function createJSONInit(data, method) {
  const headers = initHeaders();

  return {
    ...baseInit,
    body: JSON.stringify(data),
    headers: {
      ...headers,
      'Content-Type': 'application/json'
    },
    method: method
  };
}

export function createPostJSONInit(data) {
  return createJSONInit(data, 'POST');
}

export function createPutJSONInit(data) {
  return createJSONInit(data, 'PUT');
}

export function createPatchJSONInit(data) {
  return createJSONInit(data, 'PATCH');
}


export function createDeleteInit() {
  const headers = initHeaders();

  return {
    ...baseInit,
    headers: headers,
    method: 'DELETE'
  };
}
