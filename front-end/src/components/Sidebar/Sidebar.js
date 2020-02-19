import React from 'react';


import Drawer from '@material-ui/core/Drawer';


import Scrollbar from "react-double-scrollbar";


const Sidebar = (props) => {
  const { ...rest } = props;

  return (
    <Drawer {...rest} >
      <Scrollbar>
      </Scrollbar>
    </Drawer>
  );
};


export default Sidebar;
