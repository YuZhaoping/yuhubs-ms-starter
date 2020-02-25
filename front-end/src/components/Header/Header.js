import React from 'react';


import styled from '@material-ui/core/styles/styled';

import MuiAppBar from '@material-ui/core/AppBar';
import MuiIconButton from '@material-ui/core/IconButton';

import MenuIcon from '@material-ui/icons/Menu';


import AppsMenu from 'Components/AppsMenu';
import LanguageSwitcher from 'Components/LanguageSwitcher';

import BrandBarBase from 'Components/BrandBar';


import sizes from 'Components/sizes';

const toolbarHeight = sizes.headerToolbarHeight;


const AppBar = styled(MuiAppBar)(({
  theme
}) => ({
  top: 0,
  background: theme.palette.common.white,
  color: theme.menubar.dark
}));


const Toolbar = styled('div')({
  position: 'relative',
  display: 'flex',
  alignItems: 'center',

  height: `${toolbarHeight}px`
});


const GrowSpace = styled('div')({
  flexGrow: 1,
  width: '100%'
});

const PlaceHold = styled('div')({
  width: '16px'
});


const SidebarButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2)
}));

const AuthedToolbar = ({ onDrawerToggle, authUser }) => (
  <Toolbar>
    <SidebarButton
      onClick={ onDrawerToggle }
      color="inherit"
      aria-label="Open drawer"
    >
      <MenuIcon />
    </SidebarButton>

    <GrowSpace />

    <AppsMenu />

    <LanguageSwitcher />

    <PlaceHold />
  </Toolbar>
);


const BrandBar = styled(BrandBarBase)(({
  theme
}) => ({
  borderRadius: '4px',
  margin: theme.spacing(0, 4),
  padding: theme.spacing(1, 2, 0, 2)
}));

const UnauthedToolbar = () => (
  <Toolbar>
    <BrandBar />

    <GrowSpace />

    <LanguageSwitcher />

    <PlaceHold />
  </Toolbar>
);


const Header = ({ onDrawerToggle, authUser }) => (
  <AppBar position="sticky" elevation={0}>

    { authUser
      ? AuthedToolbar({ onDrawerToggle, authUser })
      : UnauthedToolbar()
    }

  </AppBar>
);


export default Header;
