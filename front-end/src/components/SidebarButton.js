import React from 'react';


import styled from '@material-ui/core/styles/styled';

import MuiIconButton from '@material-ui/core/IconButton';


import MenuIcon from '@material-ui/icons/Menu';


const IconButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2, 2)
}));


const SidebarButton = ({ onSidebarToggle, ...rest }) => (
  <IconButton
    onClick={ onSidebarToggle }
    color="inherit"
    aria-label="Toggle sidebar"
    { ...rest }
  >
    <MenuIcon />
  </IconButton>
);


export default SidebarButton;
