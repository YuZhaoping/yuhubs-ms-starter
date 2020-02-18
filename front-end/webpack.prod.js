process.env.NODE_ENV = 'production';

const merge = require('webpack-merge');
const common = require('./webpack.common.js');

const UglifyJsPlugin = require('uglifyjs-webpack-plugin');


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
    mode: 'production',
    devtool: 'source-map',
    optimization: {
      minimizer: [
        new UglifyJsPlugin({
          uglifyOptions: {
            compress: {
              comparisons: false
            },
            output: {
              ascii_only: true
            }
          },
          exclude: /\.min\.js$/,
          extractComments: false,
          sourceMap: true,
          parallel: true,
          cache: true
        })
      ]
    }
  });
}
