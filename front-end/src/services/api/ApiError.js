
function inheritPrototype(subType, superType) {
  const prototype = Object.create(superType.prototype);
  prototype.constructor = subType;
  subType.prototype = prototype;
}


const ApiError = function(error) {
  Error.call(this);

  this.name = 'ApiError';

  this.statusCode = error.statusCode;

  this.message = error.message;

  if (error.code) {
    this.code = error.code;
  } else {
    this.code = error.statusCode;
  }

  if (error.errors) {
    this.errors = error.errors;
  }

  if (error.body) {
    this.body = error.body;
  }

};


inheritPrototype(ApiError, Error);


ApiError.prototype.toString = function() {
  let str = String(this.statusCode);

  if (this.code && this.code !== this.statusCode) {
    str += '(' + String(this.code) + ')';
  }

  str += ' - ' + String(this.message);

  return str;
};


export default ApiError;
