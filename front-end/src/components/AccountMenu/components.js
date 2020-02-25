import React from 'react';


import withStyles from '@material-ui/core/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import MuiMenu from '@material-ui/core/Menu';
import MuiMenuItem from '@material-ui/core/MenuItem';


import { darken } from 'polished';


export const MenuToggle = styled('div')(({
  theme
}) => ({
  display: 'inline-flex',
  verticalAlign: 'middle',

  margin: theme.spacing(0, 2, 0, 1),
  padding: theme.spacing(1, 2),
  borderRadius: '2px',
  cursor: 'pointer',

  '&:hover': {
    backgroundColor: darken(0.025, theme.palette.grey["100"])
  }
}));


export const Username = styled('span')({
  whiteSpace: 'nowrap',
  height: '24px'
});


export const ArrowDropDownWrapper = styled('span')(({
  theme
}) => ({
  marginLeft: theme.spacing(2),

  '& svg': {
    width: '22px',
    height: '22px'
  }
}));


export const Menu = withStyles({
  paper: {
    border: '1px solid #d3d4d5'
  },
})(props => (
  <MuiMenu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center'
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center'
    }}
    {...props}
  />
));


export const MenuItem = withStyles(theme => ({
  root: {
  }
}))(MuiMenuItem);
