import FileUploader from 'Services/util/FileUploader';

import baseUrl from '../baseUrl';


function inheritPrototype(subType, superType) {
  const prototype = Object.create(superType.prototype);
  prototype.constructor = subType;
  subType.prototype = prototype;
}


const Upload = function(opts) {
  FileUploader.call(this, {
    ...opts,
    url: baseUrl + '/upload/products'
  });
};

inheritPrototype(Upload, FileUploader);


export default Upload;
