'use strict';

const path = require('path');

// plugins
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const ChunkRenamePlugin = require("webpack-chunk-rename-plugin");
const webpack = require('webpack');

const dev = process.env.NODE_ENV !== 'production';


const cleanOptions = {
  cleanOnceBeforeBuildPatterns: ['**/*'],
  verbose: false,
  dry: false
};


module.exports = {
  entry: {
    polyfills: './src/polyfills/index.js',
    main: './src/index.js'
  },
  output: {
    filename: dev ? 'js/[name].bundle.js' : 'js/[name].[chunkhash].bundle.js',
    chunkFilename: dev ? 'js/[name].chunk.js' : 'js/[name].[chunkhash].chunk.js',
    path: path.resolve(__dirname, 'deploy/html/public'),
    publicPath: '/public/',
    libraryTarget: 'umd'
  },
  optimization: {
    runtimeChunk: 'single',
    splitChunks: {
      chunks: 'all',
      cacheGroups: {
        materialui: {
          test: /[\\/]node_modules[\\/]@material-ui[\\/]/,
          name: 'material-ui'
        },
        redux: {
          test: /[\\/]node_modules[\\/](.*redux.*|.*react-router.*|history)[\\/]/,
          name: 'redux'
        },
        polyfills: {
          test: /[\\/]node_modules[\\/]whatwg-fetch[\\/]/,
          name: 'polyfills'
        },
        vendor: {
          test: /[\\/]node_modules[\\/]/,
          priority: -10,
          name: 'vendors'
        }
      }
    }
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        loader: "babel-loader",
        options: {
          presets: ['@babel/preset-env', '@babel/preset-react']
        }
      },
      {
        test: /\.css$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader'
        ]
      },
      {
        test: /\.(png|jpg|gif|svg)$/i,
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8192,
              name: dev ? 'img/[name].[ext]' : 'img/[name].[hash].[ext]',
              publicPath: '../'
            }
          }
        ]
      },
      {
        test: /\.(woff|woff2|eot|ttf)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 10240,
              name: 'fonts/[name].[ext]',
              publicPath: '../'
            }
          }
        ]
      },
      {
        test: /\.scss$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'sass-loader'
        ]
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin(cleanOptions),
    new ChunkRenamePlugin({
      initialChunksWithEntry: true,
      polyfills: 'js/polyfills.bundle.js'
    }),
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: dev ? './public/dev/index.html' : './public/index.html',
      favicon: './public/favicon.png',
      inject: false,
      chunks: ['redux', 'vendors', 'material-ui', 'main', 'runtime'],
      chunksSortMode: 'manual'
    }),
    new MiniCssExtractPlugin({
      filename: dev ? 'css/[name].css' : 'css/[name].[contenthash].css'
    }),
    new webpack.HashedModuleIdsPlugin(),
    new CopyPlugin([
      { from: 'public/vendors', to: 'vendors/' },
      { from: 'public/loader.css' }
    ])
  ],
  resolve: {
    extensions: ['.js', '.jsx', '.scss'],
    alias: {
      NodeModules: path.resolve(__dirname, 'node_modules/'),
      Actions: path.resolve(__dirname, 'src/actions/'),
      Assets: path.resolve(__dirname, 'src/assets/'),
      Components: path.resolve(__dirname, 'src/components/'),
      Constants: path.resolve(__dirname, 'src/constants/'),
      Routes: path.resolve(__dirname, 'src/routes/'),
      Pages: path.resolve(__dirname, 'src/pages/'),
      Services: path.resolve(__dirname, 'src/services/')
    }
  },
  externals: {
    'react': {
      root: 'React',
      commonjs: 'react',
      commonjs2: 'react',
      amd: 'React'
    },
    'react-dom': {
      root: 'ReactDOM',
      commonjs: 'react-dom',
      commonjs2: 'react-dom',
      amd: 'ReactDOM'
    }
  }
};
