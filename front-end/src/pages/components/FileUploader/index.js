import async from 'Components/Async';

const FileUploader = async(() => import(/* webpackChunkName: "file-uploader" */ './FileUploader'));

export default FileUploader;
