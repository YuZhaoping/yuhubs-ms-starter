import React from 'react';


import { AppContent, MainContent } from './appStyles';


const AppLayout = (props) => {

  return (
    <React.Fragment>
      <AppContent>
        <MainContent>
        </MainContent>
      </AppContent>
    </React.Fragment>
  );
};


export default AppLayout;
