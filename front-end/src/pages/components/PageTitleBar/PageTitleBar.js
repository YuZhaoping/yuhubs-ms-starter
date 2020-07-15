import React from 'react';


import { FormattedMessage } from 'react-intl';


import {
  useStyles,
  Title,
  GrowSpace,
  PlaceHold
} from './components';

import PageMenu from 'Pages/components/PageMenu';
import PageTabs from 'Pages/components/PageTabs';


const PageTitleBar = (props) => {
  const { title, tabs, menus, pageRef, ...rest } = props;

  const classes = useStyles();

  return (
    <div className={ classes.root } >
      { title &&
        <Title variant="h6">
          <FormattedMessage id={ title }/>{ ' /' }
        </Title> }
      <GrowSpace />
      { tabs && <PageTabs tabs={ tabs } { ...rest } /> }
      { menus
        ? <PageMenu menus={ menus } pageRef={ pageRef } />
        : <PlaceHold />
      }
    </div>
  );
};


export default PageTitleBar;
