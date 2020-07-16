import React, { useState, useEffect } from 'react';


import { formatFileSize } from './fileSize';


import LinearProgress from '@material-ui/core/LinearProgress';


import makeStyles from '@material-ui/core/styles/makeStyles';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexDirection: 'column'
  },

  progress: {
    width: '100%',
    margin: theme.spacing(2, 0)
  },

  statusDetail: {
    display: 'flex',
    justifyContent: 'space-between'
  },

  size: {
  },

  percentage: {
    color: theme.palette.primary.main
  }
}));


const formatSizeStatus = function(file, loadedSize) {
  if (file) {
    return formatFileSize(loadedSize, 1) + '/' + formatFileSize(file.size, 1);
  } else {
    return "-/-";
  }
};

const calculatePercentage = function(file, loadedSize) {
  if (file && file.size > 0) {
    return parseInt((loadedSize / file.size) * 100, 10);
  } else {
    return 0;
  }
};

const formatPercentageStatus = function(uploadService, percentage) {
  if (uploadService
      && uploadService.isUploading()
      && percentage < 100) {
    return "Uploading... " + percentage + '%';
  }
  return percentage + '%';
};


const StatusBar = props => {
  const { file, uploadService } = props;

  const [uploadedSize, setUploadedSize] = useState(0);

  useEffect(() => {
    setUploadedSize(0);
  }, [ file ]);


  const onUploadProgress = (loaded) => {
    setUploadedSize(loaded);
  };

  if (uploadService && uploadService.isInitial()) {
    uploadService.setOptions({
      onProgress: onUploadProgress
    });

    uploadService.startUpload(file);
  }

  const sizeStatus = formatSizeStatus(file, uploadedSize);

  const percentage = calculatePercentage(file, uploadedSize);
  const percentageStatus = formatPercentageStatus(uploadService, percentage);


  const classes = useStyles();

  return (
    <div className={ classes.root }>
      <div className={ classes.progress }>
        <LinearProgress variant="determinate" value={ percentage } />
      </div>
      <div className={ classes.statusDetail }>
        <div className={ classes.size }>
          { sizeStatus }
        </div>
        <div className={ classes.percentage }>
          { percentageStatus }
        </div>
      </div>
    </div>
  );
};


export default StatusBar;
