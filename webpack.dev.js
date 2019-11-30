const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
    mode: 'development',
    devtool: 'source-map',
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8000,
        allowedHosts: [
            '*'
        ]
    },
    output: {
        filename: '[name].bundle.js',
        chunkFilename: '[name].chunk.js'
    }
});