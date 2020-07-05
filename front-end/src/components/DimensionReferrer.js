import React, { useState, useEffect } from 'react';


const DimensionReferrer = props => {
  const { component: Component, anchorRef, children, ...rest } = props;

  const [refDimensions, setRefDimensions] =
    useState({ height: 0, left: 0, top: 0, width: 0 });


  const { innerHeight } = window;

  useEffect(() => {
    if (anchorRef.current) {
      const { offsetLeft, offsetTop, offsetWidth } = anchorRef.current;

      setRefDimensions({
        height: innerHeight - offsetTop,
        left: offsetLeft,
        top: offsetTop,
        width: offsetWidth
      });
    }
  }, [anchorRef.current, innerHeight]);


  return (
    <Component refDimensions={ refDimensions } { ...rest } >
      { children }
    </Component>
  );
};

export default DimensionReferrer;
