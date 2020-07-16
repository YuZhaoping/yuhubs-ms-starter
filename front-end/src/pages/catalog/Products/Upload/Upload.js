import React from 'react';


import FileUploader from 'Pages/components/FileUploader';
import fileTypes from './fileTypes';

import UploadService from 'Services/catalog/products/Upload';


import Modal from '@material-ui/core/Modal';


import makeStyles from '@material-ui/core/styles/makeStyles';


import sizes from 'Pages/components/sizes';

const dialogWidth = sizes.uploadDialogWidth;


const useStyles = makeStyles(theme => ({
  root: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    outline: 'none',
    boxShadow: theme.shadows[20],
    width: dialogWidth,
    maxHeight: '100%',
    overflowY: 'auto',
    maxWidth: '100%'
  }
}));


const Upload = props => {
  const { open, onClose } = props;

  const createUploadService = (opts) => {
    return new UploadService(opts);
  };

  const onUploadStart = () => {
  };

  const onUploadEnd = (isSuccess, response) => {
  };


  const classes = useStyles();


  if (!open) {
    return null;
  }

  return (
    <Modal
      disableBackdropClick
      disableEscapeKeyDown
      open={ open }
    >
      <div className={ classes.root } >
        <FileUploader
          acceptFileTypes={ fileTypes.join() }
          createUploadService={ createUploadService }
          onClose={ onClose }
          onUploadStart={ onUploadStart }
          onUploadEnd={ onUploadEnd }
        />
      </div>
    </Modal>
  );
};


export default Upload;
