'use strict';

module.exports = function (api) {
  api.cache(true);

  const presets = [
    ["@babel/preset-env", {
      "modules": false,
      "useBuiltIns": "usage"
    }],
    "@babel/preset-react"
  ];

  const plugins = [
    ["@babel/plugin-transform-runtime", {
      "regenerator": false,
      "useESModules": true
    }],
    ["@babel/plugin-syntax-dynamic-import"],
    ["@babel/plugin-proposal-class-properties"]
  ];

  return {
    presets,
    plugins
  };
}
