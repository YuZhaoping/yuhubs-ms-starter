import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';


import withStyles from '@material-ui/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import MuiIconButton from '@material-ui/core/IconButton';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import MoreVertIcon from '@material-ui/icons/MoreVert';


const menuItemHeight = 36;


const IconButton = styled(MuiIconButton)(({
  theme
}) => ({
  padding: theme.spacing(2, 2),
  color: theme.palette.common.white,

  '& svg': {
    fontSize: '16px',
    width: '16px',
    height: '16px'
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


const PageMenu = (props) => {
  const { menus } = props;


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
        <MoreVertIcon />
      </IconButton>

      <StyledMenu
        id="page-menu"
        anchorEl={ anchorMenu }
        open={ open }
        onClose={ closeMenu }
      >
        {menus.map((item, index) => (
          <StyledMenuItem
            key={ index }
            onClick={() => {
              closeMenu();
            }}
            component={ Link }
            to={ item.path }
          >
            <ListItemIcon>{ item.icon }</ListItemIcon>
            <ListItemText primary={ item.name } />
          </StyledMenuItem>
        ))}
      </StyledMenu>

    </React.Fragment>
  );
};


export default PageMenu;
