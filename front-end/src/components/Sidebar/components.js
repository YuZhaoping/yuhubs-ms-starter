import React from 'react';
import { NavLink as RouterNavLink } from 'react-router-dom';


import styled from '@material-ui/core/styles/styled';

import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';

import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';


import { rgba, darken } from 'polished';


export const MenuList = styled(List)(({
  theme
}) => ({
  backgroundColor: theme.sidebar.background
}));


export const ItemsWrapper = styled('div')(({
  theme
}) => ({
  paddingTop: `${theme.spacing(2.5)}px`,
  paddingBottom: `${theme.spacing(2.5)}px`
}));


export const ItemHeader = styled(Typography)(({
  theme
}) => ({
  display: 'block',
  color: theme.palette.common.white,
  padding: theme.spacing(2, 4, 1, 4),
  opacity: 0.9
}));


const Item = styled(ListItem)(({
  theme
}) => ({
  display: 'flex',
  paddingLeft: `${theme.spacing(4)}px`,
  paddingTop: `${theme.spacing(3)}px`,
  paddingBottom: `${theme.spacing(3)}px`,

  '& svg': {
    color: theme.palette.common.white,
    fontSize: '20px',
    width: '20px',
    height: '20px'
  },

  '&:hover': {
    background: rgba(0, 0, 0, 0.08)
  },

  '&.active': {
    backgroundColor: darken(0.05, theme.sidebar.background),

    '& span': {
      color: theme.palette.grey["100"]
    }
  }
}));

const ItemText = styled(ListItemText)(({
  theme
}) => ({
  margin: 0,
  padding: 0,

  '& span': {
    color: theme.palette.common.white,
    fontSize: '14px',
    padding: '0 16px'
  }
}));


const IconLess = styled(ExpandLess)(({
  theme
}) => ({
  color: rgba(theme.palette.common.white, 0.5)
}));

const IconMore = styled(ExpandMore)(({
  theme
}) => ({
  color: rgba(theme.palette.common.white, 0.5)
}));


const createSubItem = level => styled(ListItem)(({
  theme
}) => ({
  display: 'flex',
  paddingLeft: `${theme.spacing(12 + (level-1)*6)}px`,
  paddingTop: `${theme.spacing(2)}px`,
  paddingBottom: `${theme.spacing(2)}px`,

  '& svg': {
    color: theme.palette.common.white,
    fontSize: '16px',
    width: '16px',
    height: '16px'
  },

  '&:hover': {
    background: rgba(0, 0, 0, 0.08)
  },

  '&.active': {
    backgroundColor: darken(0.06, theme.sidebar.background),

    '& span': {
      color: theme.palette.grey["100"]
    }
  }
}));

const SubItemText = styled(ListItemText)(({
  theme
}) => ({
  marginTop: 0,
  marginBottom: 0,

  '& span': {
    color: theme.palette.common.white,
    fontSize: '14px',
    padding: '0 12px'
  }
}));


const NavLink = React.forwardRef((props, ref) => (
  <RouterNavLink innerRef={ref} {...props} />
));


export const MenuItem = ({
  classes,
  name,
  icon,
  isOpen,
  isCollapsable,
  to,
  ...rest
}) => (
  to ?
  <Item
    component={ NavLink }
    to={ to }
  >
    { icon }
    <ItemText>{ name }</ItemText>
  </Item>
  :
  <Item {...rest}>
    { icon }
    <ItemText>{ name }</ItemText>
    { isCollapsable ? (
      isOpen ? (
        <IconMore />
      ) : (
        <IconLess />
      )
    ) : null }
  </Item>
);


export const MenuSubItem = ({
  name,
  icon,
  to,
  onClick,
  level
}) => {
  const SubItem = createSubItem(level);

  return (
  to ?
  <SubItem
    component={ NavLink }
    to={ to }
    onClick={ onClick }
  >
    { icon }
    <SubItemText>{ name }</SubItemText>
  </SubItem>
  :
  <SubItem>
    { icon }
    <SubItemText>{ name }</SubItemText>
  </SubItem>
)};
