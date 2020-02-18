import React from 'react';


import CssBaseline from '@material-ui/core/CssBaseline';


import AppThemeProvider from './AppThemeProvider';


const App = () => {

  return (
    <AppThemeProvider>
      <CssBaseline />
    </AppThemeProvider>
  );
};


export default App;
