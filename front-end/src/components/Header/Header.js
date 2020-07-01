import React from 'react';


import MenuIcon from '@material-ui/icons/Menu';


import {
  AppBar,
  Toolbar,
  BrandBar,
  GrowSpace,
  PlaceHold
} from './components';

import AppsMenu from 'Components/AppsMenu';
import SidebarButton from 'Components/SidebarButton';
import LanguageSwitcher from 'Components/LanguageSwitcher';
import AccountMenu from 'Components/AccountMenu';


const AuthedToolbar = ({ onDrawerToggle, authUser }) => (
  <Toolbar>

    <SidebarButton
      onDrawerToggle={ onDrawerToggle }
    />

    <GrowSpace />

    <AppsMenu />

    <PlaceHold />

    <LanguageSwitcher />

    <AccountMenu />

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
