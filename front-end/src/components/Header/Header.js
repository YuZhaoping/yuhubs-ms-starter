import React from 'react';


import {
  AppBar,
  Toolbar,
  SidebarButton,
  BrandBar,
  GrowSpace,
  PlaceHold
} from './components';

import AppsMenu from 'Components/AppsMenu';
import LanguageSwitcher from 'Components/LanguageSwitcher';


import MenuIcon from '@material-ui/icons/Menu';


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
