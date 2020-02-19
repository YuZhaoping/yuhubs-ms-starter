import React from 'react';


import CssBaseline from '@material-ui/core/CssBaseline';


import AppThemeProvider from './AppThemeProvider';

import { Root, AppContent, MainContent } from './appStyles';


const App = () => {

  return (
    <AppThemeProvider>
      <CssBaseline />
      <Root>
        <AppContent>
          <MainContent>
          </MainContent>
        </AppContent>
      </Root>
    </AppThemeProvider>
  );
};


export default App;
