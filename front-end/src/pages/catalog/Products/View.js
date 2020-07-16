import React, { useState } from 'react';


import Divider from '@material-ui/core/Divider';


import Upload from './Upload';


const View = (props) => {
  const { selfRef } = props;


  const [uploadOpened, setUploadOpen] = useState(false);

  const toggleUpload = () => {
    setUploadOpen(!uploadOpened);
  };


  const onMenuItemClick = (index) => {
    switch (index) {
      case 0:
        toggleUpload();
        break;
      default: break;
    }
  };


  const Self = {
    onMenuItemClick
  };

  if (selfRef) {
    selfRef.current = Self;
  }


  return (
    <React.Fragment>
      <Divider />
      { uploadOpened &&
        <Upload
          open={ uploadOpened }
          onClose={ toggleUpload }
        />
      }
    </React.Fragment>
  );
};


const ViewWrapper = React.forwardRef((props, ref) => (
  <View selfRef={ref} {...props} />
));

export default ViewWrapper;
