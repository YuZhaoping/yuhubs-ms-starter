import React from 'react';


import PagesLayout from 'Pages/PagesLayout';

import tabs from './tabs';
import menus from './menus';


const title = 'Inventory';

const Layout = (props) => {
  const { children, ...rest } = props;

  return (
    <PagesLayout title={ title } menus={ menus } tabs={ tabs } { ...rest } >
      { children }
    </PagesLayout>
  );
};


export default Layout;
