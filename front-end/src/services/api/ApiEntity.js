
export const apiData = function(entity) {
  if (entity.apiVersion) {
    return entity.data;
  }
  return entity;
};

export const apiError = function(entity) {
  if (entity.apiVersion) {
    return entity.error;
  }
  return null;
};
