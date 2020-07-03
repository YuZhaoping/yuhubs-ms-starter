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


const AuthedToolbar = ({ sidebarOpen, onSidebarToggle, authUser }) => (
  <Toolbar>

    { !sidebarOpen &&
      <SidebarButton
        onSidebarToggle={ onSidebarToggle }
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


const Header = ({ sidebarOpen, onSidebarToggle, authUser }) => (
  <AppBar position="sticky" elevation={0}>

    { authUser
      ? AuthedToolbar({ sidebarOpen, onSidebarToggle, authUser })
      : UnauthedToolbar()
    }

  </AppBar>
);


export default Header;
