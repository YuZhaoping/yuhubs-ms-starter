import React from 'react';


import StylesProvider from '@material-ui/styles/StylesProvider';
import MuiThemeProvider from '@material-ui/styles/ThemeProvider';


import appThemes from './themes';


const AppThemeProvider = (props) => {
  const { children } = props;

  const applyTheme = appThemes[0];


  return (
    <StylesProvider injectFirst>
      <MuiThemeProvider theme={ applyTheme }>
        { children }
      </MuiThemeProvider>
    </StylesProvider>
  );
};


export default AppThemeProvider;
