import React from 'react';


import { getFileIcon } from './fileIcons';
import StatusBar from './StatusBar';


import makeStyles from '@material-ui/core/styles/makeStyles';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    width: '100%',
    margin: theme.spacing(8, 0)
  },

  fileIcon: {
    width: 60
  },

  fileDetail: {
    flexGrow: 1,
    display: 'flex',
    flexDirection: 'column',
    marginLeft: theme.spacing(2)
  },

  fileName: {
  }
}));


const FileProfile = props => {
  const { file, uploadService } = props;

  const fileName = file ? file.name : ".";
  const fileIcon = getFileIcon(file);


  const classes = useStyles();

  return (
    <div className={ classes.root }>
      <div>
        <img
          className={ classes.fileIcon }
          src={ require(`Assets/img/uploader/${fileIcon}`) }
        />
      </div>
      <div className={ classes.fileDetail }>
        <div className={ classes.fileName }>
          { fileName }
        </div>
        <StatusBar
          file={ file }
          uploadService={ uploadService }
        />
      </div>
    </div>
  );
};


export default FileProfile;
