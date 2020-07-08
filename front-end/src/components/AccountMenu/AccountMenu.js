import React, { useState } from 'react';
import { withRouter, Link as RouterLink } from 'react-router-dom';
import { connect } from 'react-redux';


import { useIntl } from 'react-intl';


import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';


import {
  MenuToggle,
  AvatarWrapper,
  Username,
  ArrowDropDownWrapper,
  Menu,
  MenuItem
} from './components';

import { markAuthState } from 'Actions/auth';

import items from './menuItems';


const fromPath = function(location) {
  const { pathname, search, hash } = location;

  let path = pathname;

  if (search) path += search;
  if (hash) path += hash;

  return path;
};


const Link = React.forwardRef((props, ref) => (
  <RouterLink innerRef={ref} {...props} />
));


const AccountMenu = props => {
  const { username } = props;

  const intl = useIntl();


  const [anchorMenu, setAnchorMenu] = useState(null);

  const toggleMenu = (event) => {
    setAnchorMenu(event.currentTarget);
  };

  const closeMenu = () => {
    setAnchorMenu(null);
  };

  const open = Boolean(anchorMenu);


  const prepareLogout = () => {
    const { history } = props;
    if (history) {
      const { location } = history;
      props.markAuthState(fromPath(location), null);
    }
  };


  return (
    <React.Fragment>

      <MenuToggle onClick={ toggleMenu }>
        <AvatarWrapper>
          <AccountCircleIcon />
        </AvatarWrapper>
        <Username variant="subtitle2">
          { username }
        </Username>
        <ArrowDropDownWrapper>
          <ArrowDropDownIcon />
        </ArrowDropDownWrapper>
      </MenuToggle>

      <Menu
        id="account-menu"
        anchorEl={ anchorMenu }
        open={ open }
        onClose={ closeMenu }
      >
        {items.map((item, index) => (
          <MenuItem
            key={ index }
            onClick={() => {
              if (item.path.endsWith('?logout')) {
                prepareLogout();
              }

              closeMenu();
            }}
            component={ Link }
            to={ item.path }
          >
            <ListItemIcon>
              { item.icon }
            </ListItemIcon>
            <ListItemText
              primary={ intl.formatMessage({id: item.name}) }
            />
          </MenuItem>
        ))}
      </Menu>

    </React.Fragment>
  );
};


const mapStateToProps = ({ auth }) => {
  return { username: auth.username };
};

export default withRouter(connect(mapStateToProps, {
  markAuthState
})(AccountMenu));
