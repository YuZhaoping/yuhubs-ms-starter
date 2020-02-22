import React, { useState } from 'react';
import { Route, Switch } from 'react-router-dom';


import styled from '@material-ui/core/styles/styled';


import routes from 'Routes/index';

import { AppContent, MainContent } from './appStyles';

import Header from 'Components/Header';
import Footer from 'Components/Footer';
import Sidebar from 'Components/Sidebar';


const drawerWidth = 252;

const Drawer = styled('div')(({
  theme
}) => ({
  [theme.breakpoints.up("md")]: {
    width: `${drawerWidth}px`,
    flexShrink: 0
  }
}));


const AppLayout = (props) => {

  const [sidebarOpen, setSidebarOpen] = useState(true);

  const handleDrawerToggle = () => {
    setSidebarOpen(!sidebarOpen);
  }


  return (
    <React.Fragment>
      { sidebarOpen &&
      <Drawer>
        <Sidebar
          PaperProps={{ style: { width: drawerWidth } }}
          variant="permanent"
          open={ sidebarOpen }
          onClose={ handleDrawerToggle }
        />
      </Drawer> }

      <AppContent>
        <Header
          onDrawerToggle={ handleDrawerToggle }
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
