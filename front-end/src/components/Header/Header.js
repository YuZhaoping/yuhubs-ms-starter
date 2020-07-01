import React from 'react';


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


const AuthedToolbar = ({ sidebarOpen, onDrawerToggle, authUser }) => (
  <Toolbar>

    { !sidebarOpen &&
      <SidebarButton
        onDrawerToggle={ onDrawerToggle }
      />
    }

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


const Header = ({ sidebarOpen, onDrawerToggle, authUser }) => (
  <AppBar position="sticky" elevation={0}>

    { authUser
      ? AuthedToolbar({ sidebarOpen, onDrawerToggle, authUser })
      : UnauthedToolbar()
    }

  </AppBar>
);


export default Header;
