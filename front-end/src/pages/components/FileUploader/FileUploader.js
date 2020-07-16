import React, { useState } from 'react';


import FileDropzone from './Dropzone';
import FileProfile from './FileProfile';


import makeStyles from '@material-ui/core/styles/makeStyles';
import styled from '@material-ui/core/styles/styled';

import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import MuiIconButton from '@material-ui/core/IconButton';

import UploadIcon from '@material-ui/icons/CloudUpload';
import CancelIcon from '@material-ui/icons/Cancel';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center'
  },

  title: {
    display: 'flex',
    alignItems: 'center',

    backgroundColor: theme.palette.primary.main,
    color: theme.palette.primary.contrastText,

    width: '100%'
  },

  titleText: {
    flexGrow: 1,

    paddingLeft: theme.spacing(2),

    fontSize: 16,
    fontWeight: theme.typography.fontWeightLight
  },

  content: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',

    width: '100%',
    flexGrow: 1,

    padding: theme.spacing(12, 20, 8, 20)
  },

  actions: {
    width: '100%',
    padding: theme.spacing(0, 24)
  },

  button: {
  }
}));


const IconButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2, 2),

  color: theme.palette.primary.contrastText,

  '& svg': {
    width: '20px',
    height: '20px'
  }
}));


const FileUploader = props => {
  const {
    acceptFileTypes,
    createUploadService,
    onClose,
    onUploadStart,
    onUploadEnd
  } = props;


  const onUploadSuccess = (response) => {
    onUploadEnd(true, response);
  };

  const onUploadFail = (error) => {
    onUploadEnd(false, error);
  };


  const [uploadService, setUploadService] = useState(null);

  const startUpload = () => {
    setUploadService(createUploadService({
      onSuccess: onUploadSuccess,
      onFail: onUploadFail
    }));
    onUploadStart();
  };


  const [currentFile, setCurrentFile] = useState(null);

  const handleAcceptedFiles = (acceptedFiles) => {
    const file = acceptedFiles.pop();
    if (file) {
      setUploadService(null);
      setCurrentFile(file);
    }
  };


  const classes = useStyles();

  return (
    <Paper className={ classes.root }>
      <div className={ classes.title }>
        <IconButton component="div" >
          <UploadIcon />
        </IconButton>
        <div className={ classes.titleText }>
          File upload
        </div>
        <IconButton
          disable={ uploadService ? "false" : "true" }
          onClick={ onClose }
        >
          <CancelIcon />
        </IconButton>
      </div>

      <div className={ classes.content }>
        <FileDropzone
          onAcceptedFiles={ handleAcceptedFiles }
          acceptFileTypes={ acceptFileTypes }
        />
        <FileProfile
          file={ currentFile }
          uploadService={ uploadService }
        />
        <div
          className={ classes.actions }
        >
          <Button
            fullWidth
            variant="contained"
            color="primary"
            className={ classes.button }
            disabled={ !!(uploadService) || !currentFile }
            onClick={ startUpload }
          >
            Upload
          </Button>
        </div>
      </div>
    </Paper>
  );
};


export default FileUploader;
