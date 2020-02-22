import React from 'react';


/* Home */
import HomeIcon from '@material-ui/icons/Home';
import DashboardIcon from '@material-ui/icons/Dashboard';
import TabIcon from '@material-ui/icons/Tab';

/* Catalog */
import FolderSpecialIcon from '@material-ui/icons/FolderSpecial';
import CategoryIcon from '@material-ui/icons/Category';
import CreateNewFolderIcon from '@material-ui/icons/CreateNewFolder';
import FolderSharedIcon from '@material-ui/icons/FolderShared';
import BallotIcon from '@material-ui/icons/Ballot';

/* Inventory */
import StorageIcon from '@material-ui/icons/Storage';
import StoreIcon from '@material-ui/icons/Store';

/* Sale */
import MonetizationOnIcon from '@material-ui/icons/MonetizationOn';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';

/* Settings */
import AccountBoxIcon from '@material-ui/icons/AccountBox';

/* Supply */
import LocalShippingIcon from '@material-ui/icons/LocalShipping';
import BusinessIcon from '@material-ui/icons/Business';


import async from 'Components/Async';


/* Home */
import HomeDefault from 'Pages/home/index';
const HomeIndex = async(() => import(/* webpackChunkName: "home-index" */ 'Pages/home/Default'));
const HomeDashboard = async(() => import(/* webpackChunkName: "home-dashboard" */ 'Pages/home/Dashboard'));

/* Home */
import CatalogDefault from 'Pages/catalog/index';
const CatalogCategories = async(() => import(/* webpackChunkName: "catalog-categories" */ 'Pages/catalog/Categories'));
const CatalogProducts = async(() => import(/* webpackChunkName: "catalog-products" */ 'Pages/catalog/Products'));

/* Inventory */
import InventoryDefault from 'Pages/inventory/index';
const InventoryStorages = async(() => import(/* webpackChunkName: "inventory-storages" */ 'Pages/inventory/Storages'));

/* Sale */
import SaleDefault from 'Pages/sale/index';
const SaleAccounts = async(() => import(/* webpackChunkName: "sale-accounts" */ 'Pages/sale/Accounts'));

/* Supply */
import SupplyDefault from 'Pages/supply/index';
const SupplySuppliers = async(() => import(/* webpackChunkName: "supply-suppliers" */ 'Pages/supply/Suppliers'));

/* Settings */
const Profile = async(() => import(/* webpackChunkName: "user-profile" */ 'Pages/settings/Profile'));


import {
  APP_CATALOG,
  APP_INVENTORY,
  APP_SALE,
  APP_SUPPLY
} from 'Constants/appIds';


const homeRoutes = {
  name: "Home",
  path: "/app",
  icon: <HomeIcon />,
  component: HomeDefault,
  children: [
    {
      path: "/app/dashboard",
      name: "Dashboard",
      icon: <DashboardIcon />,
      component: HomeDashboard
    },
    {
      path: "/app/index",
      name: "Index",
      icon: <TabIcon />,
      component: HomeIndex
    }
  ]
};


const catalogRoutes = {
  header: "Apps",
  name: "Catalog",
  path: "/app/catalog",
  icon: <FolderSpecialIcon />,
  component: CatalogDefault,
  appId: APP_CATALOG,
  children: [
    {
      path: "/app/catalog/categories/:type",
      name: "Categories",
      icon: <CategoryIcon />,
      component: CatalogCategories,
      children: [
        {
          path: "/app/catalog/categories/global",
          name: "Global",
          icon: <CreateNewFolderIcon />
        },
        {
          path: "/app/catalog/categories/self",
          name: "Self",
          icon: <FolderSharedIcon />
        }
      ]
    },
    {
      path: "/app/catalog/products",
      name: "Products",
      icon: <BallotIcon />,
      component: CatalogProducts
    }
  ]
};

const inventoryRoutes = {
  name: "Inventory",
  path: "/app/inventory",
  icon: <StorageIcon />,
  component: InventoryDefault,
  appId: APP_INVENTORY,
  children: [
    {
      path: "/app/inventory/storages",
      name: "Storages",
      icon: <StoreIcon />,
      component: InventoryStorages
    }
  ]
};

const saleRoutes = {
  name: "Sale",
  path: "/app/sale",
  icon: <MonetizationOnIcon />,
  component: SaleDefault,
  appId: APP_SALE,
  children: [
    {
      path: "/app/sale/accounts",
      name: "Accounts",
      icon: <AccountBalanceIcon />,
      component: SaleAccounts
    }
  ]
};

const supplyRoutes = {
  name: "Supply",
  path: "/app/supply",
  icon: <LocalShippingIcon />,
  component: SupplyDefault,
  appId: APP_SUPPLY,
  children: [
    {
      path: "/app/supply/suppliers",
      name: "Suppliers",
      icon: <BusinessIcon />,
      component: SupplySuppliers
    }
  ]
};


const settingsRoutes = {
  header: "Settings",
  name: "Profile",
  path: "/app/auth/profile",
  icon: <AccountBoxIcon />,
  component: Profile,
  children: null
};


export const wholeRoutes = [
  homeRoutes,
  catalogRoutes,
  inventoryRoutes,
  saleRoutes,
  supplyRoutes,
  settingsRoutes
];


const reduceRoutesTree = function(result, route) {
  if (route.children) {
    result = route.children.reduce((result, child) => {
      return reduceRoutesTree(result, child);
    }, result);
  }

  if (route.component) {
    result.push({
      path: route.path,
      component: route.component
    });
  }

  return result;
}


const appLayoutRoutes = wholeRoutes.reduceRight(
(result, route) => {
  return reduceRoutesTree(result, route);
}, []);


export default appLayoutRoutes;
