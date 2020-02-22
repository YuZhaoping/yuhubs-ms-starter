import React from 'react';


import withStyles from '@material-ui/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import MuiIconButton from '@material-ui/core/IconButton';
import MuiMenu from '@material-ui/core/Menu';
import MuiMenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';


export const MenuButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2, 2),

  '& img': {
    fontSize: '24px',
    width: '24px',
    height: '24px'
  }
}));


export const Menu = withStyles({
  paper: {
    border: '1px solid #d3d4d5'
  }
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


export const MenuItem = React.forwardRef(function MenuItem(props, ref) {
  const {
    language,
    switchLanguage,
    handleClose,
    ...rest
  } = props;

  const { icon, name } = language;

  return (
    <MuiMenuItem
      onClick={() => {
        handleClose();
        switchLanguage(language);
      }}
      ref={ ref }
      {...rest}
    >
      <ListItemIcon>
        <i className={`flag flag-24 flag-${icon}`} />
      </ListItemIcon>
      <ListItemText primary={ name } />
    </MuiMenuItem>
  );
});
