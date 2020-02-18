process.env.NODE_ENV = 'development';

const merge = require('webpack-merge');
const common = require('./webpack.common.js');

const apiMocker = require('connect-api-mocker');


module.exports = function(env) {
  if (typeof(env) !== 'undefined' && env.report === true) {
    const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

    common.plugins.push(
      new BundleAnalyzerPlugin({
        analyzerMode: `disabled`,
        generateStatsFile: true
      })
    );
  }

  return merge(common, {
    mode: 'development',
    devtool: 'inline-source-map',

    devServer: {
      contentBase: './deploy/html',
      compress: true,
      writeToDisk:true,

      before: function(app) {
        app.use(apiMocker('/auth', 'mock/auth'));
      }
    }
  });
}
