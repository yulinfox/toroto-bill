const autoprefixer = require("autoprefixer");
const pxtorem = require("postcss-pxtorem");
const path = require('path');
const themePath = path.resolve(__dirname,'src/assets/style/theme.less');

module.exports = {
  // 关闭eslint检查
  lintOnSave: false,
  // 配置css前缀,px转rem
  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          hack: `true; @import "${themePath}";`
        }
      },
      postcss: {
        plugins: [
          autoprefixer(),
          pxtorem({
            rootValue: 37.5,
            propList: ["*"]
          })
        ]
      }
    }
  },

  configureWebpack: {
    externals: {
      axios: "axios" // 配置使用CDN
    }
  },

  devServer: {
      // 代理
      proxy: {
          // 只要请求地址有'api'都会匹配上
          "/mobile": {
              target: "http://localhost:8764",
              ws: true,
              // 允许跨域
              changeOrigin: true,
              pathRewrite: {
                  "^/mobile": ""
              }
          }
      }
  }
};
