import {
  createGetInit,
  createPostJSONInit,
  createPutJSONInit,
  createPatchJSONInit,
  createDeleteInit
} from '../util/requests';

import {
  checkStatus,
  parseJSON,
  parseApiData
} from '../util/responses';


export const doGet = function(url) {
  return new Promise(function(resolve, reject) {

    const init = createGetInit();

    fetch(url, init)
      .then(checkStatus).then(parseJSON).then(parseApiData)
      .then(function(apiData) {
        resolve(apiData);
      })
      .catch(function(error) {
        reject(error);
      })
  });
};

export const doCreate = function(url, data) {
  return new Promise((resolve, reject) => {

    const init = createPostJSONInit(data);

    fetch(url, init)
      .then(checkStatus).then(parseJSON).then(parseApiData)
      .then(function(apiData) {
        resolve(apiData);
      })
      .catch(function(error) {
        reject(error);
      });
  });
};

export const doUpdate = function(url, data, isPatch) {
  return new Promise((resolve, reject) => {

    const init = isPatch
      ? createPatchJSONInit(data)
      : createPutJSONInit(data);

    fetch(url, init)
      .then(checkStatus).then(parseJSON).then(parseApiData)
      .then(function(apiData) {
        resolve(apiData);
      })
      .catch(function(error) {
        reject(error);
      });
  });
};

export const doDelete = function(url) {
  return new Promise((resolve, reject) => {

    const init = createDeleteInit();

    fetch(url, init)
      .then(checkStatus)
      .then(function() {
        resolve();
      })
      .catch(function(error) {
        reject(error);
      });
  });
};
