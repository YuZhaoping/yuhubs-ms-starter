import React from 'react';


import { AppContent, MainContent } from './appStyles';

import Footer from 'Components/Footer';


const AppLayout = (props) => {

  return (
    <React.Fragment>
      <AppContent>
        <MainContent>
        </MainContent>
        <Footer />
      </AppContent>
    </React.Fragment>
  );
};


export default AppLayout;
