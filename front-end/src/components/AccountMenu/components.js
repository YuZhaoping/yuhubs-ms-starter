import React from 'react';


import withStyles from '@material-ui/core/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import MuiMenu from '@material-ui/core/Menu';
import MuiMenuItem from '@material-ui/core/MenuItem';
import Avatar from '@material-ui/core/Avatar';


import { darken } from 'polished';


export const MenuToggle = styled('div')(({
  theme
}) => ({
  display: 'flex',
  alignItems: 'center',

  margin: theme.spacing(0, 2),
  padding: theme.spacing(2, 2, 1, 2),

  borderStyle: 'solid',
  borderColor: theme.palette.primary.main,
  borderWidth: '1px',
  borderRadius: '2px',

  cursor: 'pointer',

  '&:hover': {
    backgroundColor: darken(0.025, theme.palette.grey["100"])
  }
}));


export const AvatarWrapper = styled(Avatar)(({
  theme
}) => ({
  backgroundColor: 'inherit',
  color: theme.palette.primary.main,
  marginLeft: theme.spacing(1),

  width: '22px',
  height: '22px'
}));


export const Username = styled('span')(({
  theme
}) => ({
  color: theme.palette.primary.main,
  marginLeft: theme.spacing(2),

  whiteSpace: 'nowrap',
  height: '24px'
}));


export const ArrowDropDownWrapper = styled('span')(({
  theme
}) => ({
  color: theme.palette.primary.main,
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
