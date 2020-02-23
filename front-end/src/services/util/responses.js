import ApiError from 'Services/api/ApiError';
import { apiData, apiError } from 'Services/api/ApiEntity';


export function checkStatus(resp) {
  if (resp.ok ||
     (resp.status > 399 && resp.status < 600)) {
    return resp;
  }

  throw new ApiError({
    message: resp.statusText,
    statusCode: resp.status
  });
}

export function parseJSON(resp) {
  if (resp.status == 204) {// No Content
    return new Promise(function(resolve) {
      resolve({});
    });
  } else {
    return resp.json();
  }
}

export function parseApiData(jsonBody) {
  const error = apiError(jsonBody);
  if (error) {
    throw new ApiError(error);
  }
  return apiData(jsonBody);
}
