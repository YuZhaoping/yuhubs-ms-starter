import React from 'react';


import styled from '@material-ui/core/styles/styled';

import MuiAppBar from '@material-ui/core/AppBar';


const AppBar = styled(MuiAppBar)(({
  theme
}) => ({
  top: 0,
  background: theme.palette.common.white,
  color: theme.menubar.dark
}));


const toolbarHeight = 48;

const Toolbar = styled('div')({
  position: 'relative',
  display: 'flex',
  alignItems: 'center',

  height: `${toolbarHeight}px`
});


const Header = () => (
  <AppBar position="sticky" elevation={0}>
    <Toolbar>
    </Toolbar>
  </AppBar>
);


export default Header;
