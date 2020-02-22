import React, { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom';


import styled from '@material-ui/core/styles/styled';

import Drawer from '@material-ui/core/Drawer';
import Collapse from '@material-ui/core/Collapse';

import PerfectScrollbar from 'react-perfect-scrollbar';


import BrandBarBase from 'Components/BrandBar';

import { wholeRoutes as items } from 'Routes/index';

import {
  MenuList,
  ItemsWrapper,
  ItemHeader,
  MenuItem,
  MenuSubItem
} from './components';


const brandBarHeight = 48;


const BrandBar = styled(BrandBarBase)(({
  theme
}) => ({
  padding: theme.spacing(3, 2, 2, 6),

  backgroundColor: theme.palette.primary.dark,
  color: theme.palette.common.white,

  height: `${brandBarHeight}px`
}));


const Scrollbar = styled(PerfectScrollbar)(({
  theme
}) => ({
  borderRight: 0,
  backgroundColor: theme.sidebar.background
}));


const Sidebar = (props) => {
  const { classes, staticContext, ...rest } = props;


  const [itemsState, setItemsState] = useState({});

  useEffect(() => {
    const pathName = props.location.pathname;

    let state = itemsState;

    items.forEach((item, index) => {
      state = { ...state, [index]: pathName.startsWith(item.path) || item.open };
    });

    setItemsState(state);
  }, []);

  const toggleCategory = (index) => {
    const state = { ...itemsState, [index]: !itemsState[index] };
    setItemsState(state);
  };


  return (
    <Drawer {...rest} >
      <BrandBar />

      <Scrollbar>
        <MenuList disablePadding>
          <ItemsWrapper>
            {items.map((item, index) => (
              <React.Fragment key={ index }>

                {item.header ? (
                  <ItemHeader variant="caption">
                    { item.header }
                  </ItemHeader>
                ) : null}

                {item.children ? (
                  <React.Fragment key={ index }>

                    <MenuItem
                      isCollapsable={ true }
                      isOpen={ !itemsState[index] }
                      onClick={ () => toggleCategory(index) }
                      name={ item.name }
                      icon={ item.icon }
                      button={ true }
                    />

                    <Collapse
                      in={ itemsState[index] }
                      timeout="auto"
                      unmountOnExit
                    >
                      {item.children.map((sub, index) => (
                        <React.Fragment key={ index }>

                          <MenuSubItem
                            name={ sub.name }
                            icon={ sub.icon }
                            to={ sub.children ? null : sub.path }
                            level={1}
                          />

                          {sub.children && sub.children.map((child, index) => (
                            <MenuSubItem
                              key={ index }
                              name={ child.name }
                              icon={ child.icon }
                              to={ child.path }
                              level={2}
                            />
                          ))}

                        </React.Fragment>
                      ))}
                    </Collapse>

                  </React.Fragment>
                ) : (
                  <MenuItem
                    isCollapsable={ false }
                    name={ item.name }
                    icon={ item.icon }
                    to={ item.path }
                  />
                )}

              </React.Fragment>
            ))}
          </ItemsWrapper>
        </MenuList>
      </Scrollbar>

    </Drawer>
  );
};


export default withRouter(Sidebar);
