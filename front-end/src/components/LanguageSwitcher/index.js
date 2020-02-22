import React, { useState } from 'react';
import { connect } from 'react-redux';


import {
  MenuButton,
  Menu,
  MenuItem
} from './components';

import languages from './data';


import {
  switchLanguage
} from 'Actions/customizings';


const LanguageSwitcher = (props) => {
  const { currentLocale } = props;


  const [anchorMenu, setAnchorMenu] = useState(null);

  const toggleMenu = (event) => {
    setAnchorMenu(event.currentTarget);
  }

  const closeMenu = () => {
    setAnchorMenu(null);
  }

  const open = Boolean(anchorMenu);


  return (
    <React.Fragment>

      <MenuButton
        onClick={ toggleMenu }
      >
        <i className={`flag flag-24 flag-${currentLocale.icon}`} />
      </MenuButton>

      <Menu
        id="lang-menu"
        anchorEl={ anchorMenu }
        open={ open }
        onClose={ closeMenu }
      >
        {languages.map((language, index) =>
          <MenuItem
            key={ index }
            language={ language }
            switchLanguage={ props.switchLanguage }
            handleClose={ closeMenu }
          />
        )}
      </Menu>

    </React.Fragment>
  );
};


const mapStateToProps = ({ customizings }) => {
  const { currentLocale } = customizings;
  return { currentLocale };
};

export default connect(mapStateToProps, {
  switchLanguage
})(LanguageSwitcher);
