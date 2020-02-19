import React from 'react';


import { AppContent, MainContent } from './appStyles';

import Header from 'Components/Header';
import Footer from 'Components/Footer';


const AppLayout = (props) => {

  return (
    <React.Fragment>
      <AppContent>
        <Header />
        <MainContent>
        </MainContent>
        <Footer />
      </AppContent>
    </React.Fragment>
  );
};


export default AppLayout;
