import React from 'react';


import SnackbarContent from '@material-ui/core/SnackbarContent';
import IconButton from '@material-ui/core/IconButton';

import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import InfoIcon from '@material-ui/icons/Info';
import WarningIcon from '@material-ui/icons/Warning';
import ErrorIcon from '@material-ui/icons/Error';

import CloseIcon from '@material-ui/icons/Close';


const variantIcon = {
  success: CheckCircleIcon,
  info: InfoIcon,
  warning: WarningIcon,
  error: ErrorIcon
};


import makeStyles from '@material-ui/core/styles/makeStyles';

import clsx from 'clsx';

import amber from '@material-ui/core/colors/amber';
import green from '@material-ui/core/colors/green';


const useStyles = makeStyles(theme => ({
  common: {
    backgroundColor: 'inherit'
  },
  success: {
    color: green[600]
  },
  error: {
    color: theme.palette.error.dark
  },
  info: {
    color: theme.palette.primary.main
  },
  warning: {
    color: amber[700]
  },
  icon: {
    fontSize: 20
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing(1)
  },
  message: {
    display: 'flex',
    alignItems: 'center'
  }
}));


const MessageBar = (props) => {
  const { className, message, onClose, variant, ...rest } = props;

  const classes = useStyles();

  const Icon = variantIcon[variant];


  return (
    <SnackbarContent
      className={clsx(classes.common, classes[variant], className)}

      aria-describedby="message-bar"

      message= {
        <span id="message-bar" className={classes.message}>
          <Icon className={clsx(classes.icon, classes.iconVariant)} />
          { message }
        </span>
      }

      action={[
        <IconButton
          key="close"
          aria-label="close"
          color="inherit"
          onClick={ onClose }
        >
          <CloseIcon className={classes.icon} />
        </IconButton>,
      ]}

      {...rest}
    />
  );
};


export default MessageBar;
