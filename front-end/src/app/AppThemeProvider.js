import React from 'react';
import { connect } from 'react-redux';


import StylesProvider from '@material-ui/styles/StylesProvider';
import MuiThemeProvider from '@material-ui/styles/ThemeProvider';

import { IntlProvider } from 'react-intl';


import themes from './themes';
import locales from '../i18n';


const AppThemeProvider = (props) => {
  const { currentLocale, children } = props;

  const theme = themes[0];

  const localeData = locales[currentLocale.localeKey];


  return (
    <StylesProvider injectFirst>
      <MuiThemeProvider theme={ theme }>
        <IntlProvider locale={ localeData.locale } messages={ localeData.messages }>
          { children }
        </IntlProvider>
      </MuiThemeProvider>
    </StylesProvider>
  );
};


const mapStateToProps = ({ customizings }) => {
  const { currentLocale } = customizings;
  return { currentLocale };
};

export default connect(mapStateToProps, {
})(AppThemeProvider);
