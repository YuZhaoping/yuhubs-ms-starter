import React from 'react';


import styled from '@material-ui/core/styles/styled';

import Paper from '@material-ui/core/Paper';


export const Root = styled('div')({
  display: 'flex',
  minHeight: '100vh'
});


export const AppContent = styled('div')({
  flex: 1,
  display: 'flex',
  flexDirection: 'column',
  overflow: 'hidden'
});


export const MainContent = styled(Paper)(({
  theme
}) => ({
  flex: 1,
  background: theme.body.background,
  overflow: 'hidden'
}));
