import React from 'react';


import styled from '@material-ui/core/styles/styled';

import Drawer from '@material-ui/core/Drawer';

import PerfectScrollbar from 'react-perfect-scrollbar';


import BrandBarBase from 'Components/BrandBar';


const brandBarHeight = 48;

const BrandBar = styled(BrandBarBase)(({
  theme
}) => ({
  padding: theme.spacing(3, 2, 2, 6),

  backgroundColor: theme.palette.primary.dark,
  color: theme.palette.common.white,

  height: `${brandBarHeight}px`
}));


const Scrollbar = styled(PerfectScrollbar)(({
  theme
}) => ({
  borderRight: 0,
  backgroundColor: theme.sidebar.background
}));


const Sidebar = (props) => {
  const { ...rest } = props;

  return (
    <Drawer {...rest} >
      <BrandBar />

      <Scrollbar>
      </Scrollbar>
    </Drawer>
  );
};


export default Sidebar;
