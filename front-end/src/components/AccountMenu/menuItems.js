import React from 'react';


import AccountBoxIcon from '@material-ui/icons/AccountBox';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';


const profileItem = {
  name: 'Profile',
  path: '/app/auth/profile',
  icon: <AccountBoxIcon />
};

const signOutItem = {
  name: 'Logout',
  path: '/auth/signin?logout',
  icon: <ExitToAppIcon />
};


const accountMenus = [
  profileItem,
  signOutItem
];


export default accountMenus;
