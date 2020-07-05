import React from 'react';


import Fab from '@material-ui/core/Fab';

import makeStyles from '@material-ui/core/styles/makeStyles';


const useStyles = makeStyles(theme => ({
  fab: {
    position: 'fixed',

    margin: 0,

    top: props => props.top,
    left: 'auto',
    right: 0
  },
}));


const FabWrapper = (props) => {
  const {
    refDimensions, onSidebarToggle, fabIcon
  } = props;


  const FabIcon = fabIcon;


  let fabTop = 'auto';

  if (refDimensions) {
    const { top, height } = refDimensions;

    fabTop = top + height * 0.4;
  }

  const classes = useStyles({ top: fabTop });


  return (
    <Fab
      size='small'
      className={ classes.fab }
      onClick={ onSidebarToggle }
    >
      <FabIcon />
    </Fab>
  );
};


export default FabWrapper;
