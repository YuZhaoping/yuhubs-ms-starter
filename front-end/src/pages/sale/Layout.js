import React from 'react';


import PagesLayout from 'Pages/PagesLayout';

import tabs from './tabs';

import PageSidebar from 'Pages/components/PageSidebar';


import makeStyles from '@material-ui/core/styles/makeStyles';

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    overflow: 'hidden',
    flexDirection: 'column'
  },

  sidebarAnchor: {
    height: 0,
    flexShrink: 0,
    flexGrow: 0
  },

  saleContent: {
    flexGrow: 1
  }
}));


const title = 'Sale';

const Layout = (props) => {
  const { sidebarComponent, children, ...rest } = props;

  const classes = useStyles();

  return (
    <PagesLayout title={ title } tabs={ tabs } { ...rest } >
      <div className={ classes.root } >
        <PageSidebar
          className={ classes.sidebarAnchor }
          sidebarComponent={ sidebarComponent }
          { ...rest }
        />
        <div className={ classes.saleContent } >
          { children }
        </div>
      </div>
    </PagesLayout>
  );
};


export default Layout;
