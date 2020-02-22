import React from 'react';


import styled from '@material-ui/core/styles/styled';

import CircularProgress from '@material-ui/core/CircularProgress';


const Root = styled('div')({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  minHeight: '100%'
});


const Loading = () => (
  <Root>
    <CircularProgress m={2} color="secondary" />
  </Root>
);


export default Loading;
