import React from 'react';


import CssBaseline from '@material-ui/core/CssBaseline';


import AppThemeProvider from './AppThemeProvider';

import { Root } from './appStyles';
import AppLayout from './AppLayout';


const App = () => {

  return (
    <AppThemeProvider>
      <CssBaseline />
      <Root>
        <AppLayout />
      </Root>
    </AppThemeProvider>
  );
};


export default App;
