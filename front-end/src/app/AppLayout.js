import React, { useState } from 'react';
import { Route, Switch } from 'react-router-dom';


import styled from '@material-ui/core/styles/styled';


import sizes from 'Components/sizes';

const sidebarWidth = sizes.appSidebarWidth;


const SidebarWrapper = styled('div')(({
  theme
}) => ({
  [theme.breakpoints.up("md")]: {
    width: `${sidebarWidth}px`,
    flexShrink: 0
  }
}));


import { AppContent, MainContent } from './appStyles';

import Header from 'Components/Header';
import Footer from 'Components/Footer';
import Sidebar from 'Components/Sidebar';


import routes from 'Routes/index';


const AppLayout = (props) => {
  const { authUser } = props;


  const [sidebarOpen, setSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  }


  return (
    <React.Fragment>

      { sidebarOpen &&
      <SidebarWrapper>
        <Sidebar
          PaperProps={{ style: { width: sidebarWidth } }}
          variant="permanent"
          open={ sidebarOpen }
          onClose={ toggleSidebar }
        />
      </SidebarWrapper> }

      <AppContent>

        <Header
          sidebarOpen={ sidebarOpen }
          onSidebarToggle={ toggleSidebar }
          authUser={ authUser }
        />

        <MainContent>
          <Switch>
            {routes.map((route, index) => (
              <Route
                key={ index }
                path={ route.path }
                component={ route.component }
              />
            ))}
          </Switch>
        </MainContent>

        <Footer />

      </AppContent>

    </React.Fragment>
  );
};


export default AppLayout;
