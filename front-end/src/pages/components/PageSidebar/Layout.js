import React from 'react';


import PerfectScrollbar from 'react-perfect-scrollbar';

import MuiPaper from '@material-ui/core/Paper';


import ChevronRightIcon from '@material-ui/icons/ChevronRight';

import Fab from './FabWrapper';


import makeStyles from '@material-ui/core/styles/makeStyles';
import styled from '@material-ui/core/styles/styled';


const Scrollbar = styled(PerfectScrollbar)(({
  theme
}) => ({
  borderRight: 0,
  backgroundColor: theme.sidebar.background
}));


const Paper = styled(MuiPaper)(({
  theme
}) => ({
  background: theme.sidebar.background
}));


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    overflow: 'hidden',
    flexDirection: 'column'
  },

  content: {
    flexGrow: 1
  }
}));


const Layout = (props) => {
  const {
    refDimensions, onClose, sidebarComponent, children, ...rest
  } = props;


  const SidebarContent = sidebarComponent;


  const classes = useStyles();


  return (
    <div className={ classes.root } >

      <Fab
        refDimensions={ refDimensions }
        onSidebarToggle={ onClose }
        fabIcon={ ChevronRightIcon }
      />

      <div className={ classes.content } >
        <Scrollbar>
          <Paper>
          { SidebarContent ?
            <SidebarContent onClose={ onClose } { ... rest } /> :
            children
          }
          </Paper>
        </Scrollbar>
      </div>

    </div>
  );
};


export default Layout;
