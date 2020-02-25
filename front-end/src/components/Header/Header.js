import React from 'react';


import MenuIcon from '@material-ui/icons/Menu';


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
import AccountMenu from 'Components/AccountMenu';


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

    <AccountMenu />

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
