import React, { useCallback } from 'react';
import { useDropzone } from 'react-dropzone';


import { FormattedMessage } from 'react-intl';


import makeStyles from '@material-ui/core/styles/makeStyles';
import clsx from 'clsx';


import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';


const useStyles = makeStyles(theme => ({
  root: {
    width: '100%'
  },

  dropZone: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    flexWrap: 'wrap',
    border: '1px dashed',
    outline: 'none',
    backgroundColor: theme.body.background,
    padding: theme.spacing(6),
    '&:hover': {
      cursor: 'pointer',
      opacity: 0.5
    }
  },

  dragActive: {
    opacity: 0.5
  },

  image: {
    width: 60
  },

  info: {
    marginTop: theme.spacing(2)
  }
}));


const Dropzone = props => {
  const { acceptFileTypes: accept, onAcceptedFiles } = props;


  const onDrop = useCallback(acceptedFiles => {
    if (onAcceptedFiles) {
      onAcceptedFiles(acceptedFiles);
    }
  }, []);


  const { getRootProps, getInputProps, isDragActive } = useDropzone({ accept, onDrop });


  const classes = useStyles();


  return (
    <div className={ classes.root }>
      <div
        className={ clsx({
          [classes.dropZone]: true,
          [classes.dragActive]: isDragActive
        }) }
        { ...getRootProps() }
      >
        <input { ...getInputProps() } />
        <div>
          <img
            className={ classes.image }
            src={ require("Assets/img/uploader/add_file_gvbb.svg") }
          />
        </div>
        <div>
          <Typography
            className={ classes.info }
            color="textSecondary"
            variant="body1"
          >
            <FormattedMessage
              id={ "file-dropzone.prompt" }
              values={{
                alink: chunks => <Link underline="always">{chunks}</Link>,
              }}
            />
          </Typography>
        </div>
      </div>
    </div>
  );
};


export default Dropzone;
