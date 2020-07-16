import async from 'Components/Async';

const Upload = async(() => import(/* webpackChunkName: "products-upload" */ './Upload'));

export default Upload;
