import createMuiTheme from '@material-ui/core/styles/createMuiTheme';

import grey from '@material-ui/core/colors/grey';


const variants = [
  {
    name: "Default",
    header: {
      background: grey[50],
      color: grey[500]
    },
    footer: {
      background: grey[50],
      color: grey[500]
    },
    sidebar: {
      background: grey[50],
      color: grey[700]
    },
    menubar: {
      background: grey[200],
      dark: grey[500]
    },
    body: {
      background: grey[100]
    }
  }
];


const theme = variant => {
  const muiTheme = createMuiTheme(
    {
      spacing: 4,
      props: {
        MuiButtonBase: {
          disableRipple: true
        }
      },
      shadows: Array(25).fill("none"),
      header: {
        ...variant.header
      },
      footer: {
        ...variant.footer
      },
      sidebar: {
        ...variant.sidebar
      },
      menubar: {
        ...variant.menubar
      },
      body: {
        ...variant.body
      }
    },
    variant.name
  );

  return muiTheme;
};


const themes = variants.map(variant => theme(variant));

export default themes;
