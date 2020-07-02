import React from 'react';


import TabIcon from '@material-ui/icons/Tab';


const extraPage1 = {
  name: 'Extra-Page1',
  path: '/app/inventory/extpage1',
  icon: <TabIcon />,
  component: null
};

const extraPage2 = {
  name: 'Extra-Page2',
  path: '/app/inventory/extpage2',
  icon: <TabIcon />,
  component: null
};


export default [
  extraPage1,
  extraPage2
];
