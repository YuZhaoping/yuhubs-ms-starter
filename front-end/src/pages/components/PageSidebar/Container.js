import React from 'react';


import Drawer from '@material-ui/core/Drawer';


import makeStyles from '@material-ui/core/styles/makeStyles';


import Layout from './Layout'


import sizes from 'Components/sizes';

const sidebarWidth = sizes.pageSidebarWidth;
const footerHeight = sizes.footerHeight;


const useStyles = makeStyles({
  drawer: {
    top: props => props.top,
    width: sidebarWidth,
    height: props => props.height,
    maxWidth: '100%'
  },
});


const Container = (props) => {
  const {
    onClose, open, refDimensions, sidebarComponent, children, ...rest
  } = props;


  let drawerTop = 0;

  const { innerHeight } = window;
  let drawerHeight = innerHeight;

  if (refDimensions) {
    const { top, height } = refDimensions;

    drawerTop = top;
    drawerHeight = height - footerHeight;
  }

  const classes = useStyles({ top: drawerTop, height: drawerHeight });


  return (
    <Drawer
      anchor='right'
      classes={{ paper: classes.drawer }}
      onClose={ onClose }
      open={ open }
      variant='permanent'
    >
      <Layout
        refDimensions={ refDimensions }
        onClose={ onClose }
        sidebarComponent={ sidebarComponent }
        { ...rest }
      >
        { children }
      </Layout>
    </Drawer>
  );
};


export default Container;
