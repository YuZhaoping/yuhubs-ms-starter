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
  background: theme.header.background,
  color: theme.header.color
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
  padding: theme.spacing(3, 2, 2, 0)
}));


export const GrowSpace = styled('div')({
  flexGrow: 1,
  width: '100%'
});


export const PlaceHold = styled('div')({
  width: '48px'
});
