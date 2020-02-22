import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';


import withStyles from '@material-ui/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import MuiIconButton from '@material-ui/core/IconButton';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import AppsIcon from '@material-ui/icons/Apps';


import { wholeRoutes } from 'Routes/index';

const apps = wholeRoutes.reduce((result, route) => {
  if (route.appId) {
    result.push({
      id: route.appId,
      name: route.name,
      path: route.path,
      icon: route.icon
    });
  }

  return result;
}, []);


import sizes from 'Components/sizes';

const menuItemHeight = sizes.appsMenuItemHeight;


const IconButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2, 2),

  '& svg': {
    fontSize: '24px',
    width: '24px',
    height: '24px'
  }
}));


const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5'
  }
})(props => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center'
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center'
    }}
    {...props}
  />
));


const StyledMenuItem = withStyles(theme => ({
  root: {
    height: `${menuItemHeight}px`,

    '&:focus': {
      backgroundColor: theme.palette.primary.main,

      '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
        color: theme.palette.common.white
      }
    }
  }
}))(MenuItem);


const Link = React.forwardRef((props, ref) => (
  <RouterLink innerRef={ref} {...props} />
));


const AppsMenu = (props) => {


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

      <IconButton onClick={ toggleMenu }>
        <AppsIcon />
      </IconButton>

      <StyledMenu
        id="apps-menu"
        anchorEl={ anchorMenu }
        open={ open }
        onClose={ closeMenu }
      >
        {apps.map((app, index) => (
          <StyledMenuItem
            key={ index }
            onClick={() => {
              closeMenu();
            }}
            component={ Link }
            to={ app.path }
          >
            <ListItemIcon>{ app.icon }</ListItemIcon>
            <ListItemText primary={ app.name } />
          </StyledMenuItem>
        ))}
      </StyledMenu>

    </React.Fragment>
  );
};


export default AppsMenu;
