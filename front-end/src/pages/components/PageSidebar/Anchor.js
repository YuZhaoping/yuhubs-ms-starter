import React, { useState, useRef } from 'react';


import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';


import Container from './Container';
import Fab from './FabWrapper';

import DimensionReferrer from 'Components/DimensionReferrer';


const Anchor = (props) => {
  const {
    sidebarComponent, className, children, ...rest
  } = props;


  const anchorRef = useRef();


  const [sidebarOpened, setSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpened);
  };


  return (
    <div className={ className } ref={ anchorRef } >
      { sidebarOpened ?
        <DimensionReferrer
          anchorRef={ anchorRef }
          component={ Container }
          onClose={ toggleSidebar } open={ sidebarOpened }
          sidebarComponent={ sidebarComponent }
          { ...rest }
        >
          { children }
        </DimensionReferrer> :
        <DimensionReferrer
          anchorRef={ anchorRef }
          component={ Fab }
          onSidebarToggle={ toggleSidebar }
          fabIcon={ ChevronLeftIcon }
        />
      }
    </div>
  );
};


export default Anchor;
