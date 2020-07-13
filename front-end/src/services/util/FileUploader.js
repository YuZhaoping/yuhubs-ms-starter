import {
  initHeaders
} from '../http/requests';


const emptyFunc = function() {};

const FileUploader = function(opts) {
  this.status = 0;

  this.options = {
    onStart: emptyFunc,
    onProgress: emptyFunc,
    onSuccess: emptyFunc,
    onFail: emptyFunc
  };

  this.setOptions(opts);
};


FileUploader.prototype.setOptions = function(opts) {
  if (opts && this.status === 0) {
    this.options = Object.assign(this.options, opts);
  }
};


FileUploader.prototype.upload = function(file) {
  if (!file || this.status !== 0) {
    return;
  }

  const formData = new FormData();

  formData.append('file', file, window.encodeURI(file.name));


  const xhr = new XMLHttpRequest();

  xhr.open('POST', this.options.url, true);


  const headers = initHeaders();

  Object.keys(headers).forEach(key =>
    xhr.setRequestHeader(key, headers[key])
  );


  if (this.options.withCredentials) {
    xhr.withCredentials = this.options.withCredentials;
  }

  if (this.options.header) {
    Object.keys(this.options.header).forEach(key =>
      xhr.setRequestHeader(key, this.options.header[key])
    );
  }

  if (this.options.timeout) {
    xhr.timeout = this.options.timeout;

    xhr.addEventListener('timeout', (e) => {
      this.status = -1;
      this.options.onFail({type: 'TIMEOUT', message: 'timeout'});
    });
  }


  xhr.addEventListener('loadstart', (e) => {
    this.options.onStart(file);
  });

  xhr.upload.addEventListener('progress', (e) => {
    this.options.onProgress(e.loaded, e.total);
  });

  xhr.addEventListener('load', (e) => {
    this.status = -1;
    if (xhr.status >= 200 && xhr.status < 400) {
      if (xhr.responseType === '' || xhr.responseType === 'text') {
        this.options.onSuccess({type: 'text', data: xhr.responseText});
      } else if (xhr.responseType === 'json') {
        this.options.onSuccess({type: 'json', data: JSON.parse(xhr.response)});
      } else {
        this.options.onSuccess({type: xhr.responseType, data: xhr.response});
      }
    } else {
      this.options.onFail({type: xhr.status, message: xhr.statusText});
    }
  }, false);

  xhr.addEventListener('abort', (e) => {
    this.status = -1;
    this.options.onFail({type: 'ABORT', message: 'abort'});
  }, false);

  xhr.addEventListener('error', (e) => {
    this.status = -1;
    this.options.onFail({type: 'ERROR', message: 'error'});
  }, false);


  this.status = 1;

  xhr.send(formData);
};


FileUploader.prototype.isInitial = function() {
  return (this.status === 0);
};

FileUploader.prototype.isUploading = function() {
  return (this.status > 0);
};

FileUploader.prototype.isUploaded = function() {
  return (this.status < 0);
};


export default FileUploader;
