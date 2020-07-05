import React from 'react';


import sizes from 'Pages/components/sizes';

const titleBarHeight = sizes.pageTitleBarHeight;


import makeStyles from '@material-ui/core/styles/makeStyles';
import withStyles from '@material-ui/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import Typography from '@material-ui/core/Typography';


export const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    alignItems: 'center',
    height: `${titleBarHeight}px`
  }
}));


export const Title = withStyles(theme => ({
  root: {
    marginRight: theme.spacing(12)
  }
}))(Typography);


export const GrowSpace = styled('div')({
  flexGrow: 1
});


export const PlaceHold = styled('div')({
  width: '16px'
});
