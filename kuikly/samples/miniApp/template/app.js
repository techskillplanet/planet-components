if (typeof wx !== 'undefined' && typeof wx.getAppBaseInfo !== 'function') {
  wx.getAppBaseInfo = function() {
    return wx.getSystemInfoSync()
  }
}

if (typeof wx !== 'undefined' && typeof wx.createOffscreenCanvas !== 'function') {
  wx.createOffscreenCanvas = function() {
    return {
      width: 0,
      height: 0,
      getContext: function() {
        return {
          font: '',
          measureText: function(text) {
            return { width: String(text || '').length * 14 }
          }
        }
      }
    }
  }
}

var business = require('./business/nativevue2')
var render = require('./lib/miniprogramApp.js')

if (typeof globalThis !== 'undefined') {
  globalThis.com = business.com
  globalThis.callKotlinMethod = business.callKotlinMethod
}

global.com = business.com
global.callKotlinMethod = business.callKotlinMethod

global.getAssetJson = function(path) {
  var json = require('./assets/' + path.replace('.json', '.js'))
  return json
}

render.initApp()
