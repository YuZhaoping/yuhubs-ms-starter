import React from 'react';


import sizes from 'Components/sizes';

const toolbarHeight = sizes.headerToolbarHeight;


import styled from '@material-ui/core/styles/styled';

import MuiAppBar from '@material-ui/core/AppBar';


import BrandBarBase from 'Components/BrandBar';


export const AppBar = styled(MuiAppBar)(({
  theme
}) => ({
  top: 0,
  background: theme.palette.common.white,
  color: theme.menubar.dark
}));


export const Toolbar = styled('div')({
  position: 'relative',
  display: 'flex',
  alignItems: 'center',

  height: `${toolbarHeight}px`
});


export const BrandBar = styled(BrandBarBase)(({
  theme
}) => ({
  borderRadius: '4px',
  margin: theme.spacing(0, 4),
  padding: theme.spacing(1, 2, 0, 2)
}));


export const GrowSpace = styled('div')({
  flexGrow: 1,
  width: '100%'
});


export const PlaceHold = styled('div')({
  width: '16px'
});
