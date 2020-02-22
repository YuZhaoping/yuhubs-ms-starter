import React from 'react';


import PagesLayout from 'Pages/PagesLayout';

import tabs from './tabs';


const title = 'Supply';

const Layout = (props) => {
  const { children, ...rest } = props;

  return (
    <PagesLayout title={ title } tabs={ tabs } { ...rest } >
      { children }
    </PagesLayout>
  );
};


export default Layout;
