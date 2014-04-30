function dscrum(){
  var $intern_0 = '', $intern_36 = '" for "gwt:onLoadErrorFn"', $intern_34 = '" for "gwt:onPropertyErrorFn"', $intern_21 = '"><\/script>', $intern_10 = '#', $intern_102 = '.cache.html', $intern_12 = '/', $intern_24 = '//', $intern_77 = '030CEDEF68F778CDC2E8098288549217', $intern_81 = '456608876305DA3D954FDCD345EB404C', $intern_101 = ':', $intern_78 = ':1', $intern_88 = ':10', $intern_89 = ':11', $intern_90 = ':12', $intern_91 = ':13', $intern_92 = ':14', $intern_93 = ':15', $intern_94 = ':16', $intern_95 = ':17', $intern_96 = ':18', $intern_97 = ':19', $intern_79 = ':2', $intern_80 = ':3', $intern_83 = ':4', $intern_84 = ':5', $intern_85 = ':6', $intern_86 = ':7', $intern_98 = ':8', $intern_99 = ':9', $intern_28 = '::', $intern_111 = '<script defer="defer">dscrum.onInjectionDone(\'dscrum\')<\/script>', $intern_20 = '<script id="', $intern_31 = '=', $intern_11 = '?', $intern_82 = 'AB70C91F403F191007A8E3F9A15B93E3', $intern_87 = 'B55D06CD79A9056CD4AC81FF0BE42305', $intern_33 = 'Bad handler "', $intern_100 = 'D73858FDB242248B7C597730A0B6E44D', $intern_110 = 'DOMContentLoaded', $intern_22 = 'SCRIPT', $intern_19 = '__gwt_marker_dscrum', $intern_62 = 'adobeair', $intern_63 = 'air', $intern_23 = 'base', $intern_15 = 'baseUrl', $intern_4 = 'begin', $intern_3 = 'bootstrap', $intern_42 = 'chrome', $intern_14 = 'clear.cache.gif', $intern_30 = 'content', $intern_1 = 'dscrum', $intern_17 = 'dscrum.nocache.js', $intern_27 = 'dscrum::', $intern_9 = 'end', $intern_58 = 'gecko', $intern_60 = 'gecko1_8', $intern_61 = 'gecko1_9', $intern_5 = 'gwt.codesvr=', $intern_6 = 'gwt.hosted=', $intern_7 = 'gwt.hybrid', $intern_103 = 'gwt/clean/clean.css', $intern_35 = 'gwt:onLoadErrorFn', $intern_32 = 'gwt:onPropertyErrorFn', $intern_29 = 'gwt:property', $intern_41 = 'gxt.user.agent', $intern_108 = 'head', $intern_75 = 'hosted.html?dscrum', $intern_107 = 'href', $intern_45 = 'ie10', $intern_51 = 'ie6', $intern_49 = 'ie7', $intern_47 = 'ie8', $intern_46 = 'ie9', $intern_37 = 'iframe', $intern_13 = 'img', $intern_38 = "javascript:''", $intern_104 = 'link', $intern_71 = 'linux', $intern_74 = 'loadExternalRefs', $intern_70 = 'mac', $intern_69 = 'mac os x', $intern_68 = 'macintosh', $intern_25 = 'meta', $intern_40 = 'moduleRequested', $intern_8 = 'moduleStartup', $intern_44 = 'msie', $intern_50 = 'msie 6', $intern_48 = 'msie 7', $intern_26 = 'name', $intern_43 = 'opera', $intern_39 = 'position:absolute;width:0;height:0;border:none', $intern_105 = 'rel', $intern_109 = 'reset.css', $intern_59 = 'rv:1.8', $intern_52 = 'safari', $intern_54 = 'safari3', $intern_56 = 'safari4', $intern_57 = 'safari5', $intern_16 = 'script', $intern_76 = 'selectingPermutation', $intern_2 = 'startup', $intern_106 = 'stylesheet', $intern_18 = 'undefined', $intern_66 = 'unknown', $intern_64 = 'user.agent', $intern_67 = 'user.agent.os', $intern_53 = 'version/3', $intern_55 = 'version/4', $intern_65 = 'webkit', $intern_73 = 'win32', $intern_72 = 'windows';
  var $wnd = window, $doc = document, $stats = $wnd.__gwtStatsEvent?function(a){
    return $wnd.__gwtStatsEvent(a);
  }
  :null, $sessionId = $wnd.__gwtStatsSessionId?$wnd.__gwtStatsSessionId:null, scriptsDone, loadDone, bodyDone, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], softPermutationId = 0, onLoadErrorFunc, propertyErrorFunc;
  $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date).getTime(), type:$intern_4});
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    var result = false;
    try {
      var query = $wnd.location.search;
      return (query.indexOf($intern_5) != -1 || (query.indexOf($intern_6) != -1 || $wnd.external && $wnd.external.gwtOnLoad)) && query.indexOf($intern_7) == -1;
    }
     catch (e) {
    }
    isHostedMode = function(){
      return result;
    }
    ;
    return result;
  }

  function maybeStartModule(){
    if (scriptsDone && loadDone) {
      var iframe = $doc.getElementById($intern_1);
      var frameWnd = iframe.contentWindow;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name_0){
          return computePropValue(name_0);
        }
        ;
      }
      dscrum = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, $intern_1, base, softPermutationId);
      $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_8, millis:(new Date).getTime(), type:$intern_9});
    }
  }

  function computeScriptBase(){
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_10);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_11);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_12, Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):$intern_0;
    }

    function ensureAbsoluteUrl(url_0){
      if (url_0.match(/^\w+:\/\//)) {
      }
       else {
        var img = $doc.createElement($intern_13);
        img.src = url_0 + $intern_14;
        url_0 = getDirectoryOfFile(img.src);
      }
      return url_0;
    }

    function tryMetaTag(){
      var metaVal = __gwt_getMetaProperty($intern_15);
      if (metaVal != null) {
        return metaVal;
      }
      return $intern_0;
    }

    function tryNocacheJsTag(){
      var scriptTags = $doc.getElementsByTagName($intern_16);
      for (var i = 0; i < scriptTags.length; ++i) {
        if (scriptTags[i].src.indexOf($intern_17) != -1) {
          return getDirectoryOfFile(scriptTags[i].src);
        }
      }
      return $intern_0;
    }

    function tryMarkerScript(){
      var thisScript;
      if (typeof isBodyLoaded == $intern_18 || !isBodyLoaded()) {
        var markerId = $intern_19;
        var markerScript;
        $doc.write($intern_20 + markerId + $intern_21);
        markerScript = $doc.getElementById(markerId);
        thisScript = markerScript && markerScript.previousSibling;
        while (thisScript && thisScript.tagName != $intern_22) {
          thisScript = thisScript.previousSibling;
        }
        if (markerScript) {
          markerScript.parentNode.removeChild(markerScript);
        }
        if (thisScript && thisScript.src) {
          return getDirectoryOfFile(thisScript.src);
        }
      }
      return $intern_0;
    }

    function tryBaseTag(){
      var baseElements = $doc.getElementsByTagName($intern_23);
      if (baseElements.length > 0) {
        return baseElements[baseElements.length - 1].href;
      }
      return $intern_0;
    }

    function isLocationOk(){
      var loc = $doc.location;
      return loc.href == loc.protocol + $intern_24 + loc.host + loc.pathname + loc.search + loc.hash;
    }

    var tempBase = tryMetaTag();
    if (tempBase == $intern_0) {
      tempBase = tryNocacheJsTag();
    }
    if (tempBase == $intern_0) {
      tempBase = tryMarkerScript();
    }
    if (tempBase == $intern_0) {
      tempBase = tryBaseTag();
    }
    if (tempBase == $intern_0 && isLocationOk()) {
      tempBase = getDirectoryOfFile($doc.location.href);
    }
    tempBase = ensureAbsoluteUrl(tempBase);
    base = tempBase;
    return tempBase;
  }

  function processMetas(){
    var metas = document.getElementsByTagName($intern_25);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name_0 = meta.getAttribute($intern_26), content;
      if (name_0) {
        name_0 = name_0.replace($intern_27, $intern_0);
        if (name_0.indexOf($intern_28) >= 0) {
          continue;
        }
        if (name_0 == $intern_29) {
          content = meta.getAttribute($intern_30);
          if (content) {
            var value_0, eq = content.indexOf($intern_31);
            if (eq >= 0) {
              name_0 = content.substring(0, eq);
              value_0 = content.substring(eq + 1);
            }
             else {
              name_0 = content;
              value_0 = $intern_0;
            }
            metaProps[name_0] = value_0;
          }
        }
         else if (name_0 == $intern_32) {
          content = meta.getAttribute($intern_30);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_33 + content + $intern_34);
            }
          }
        }
         else if (name_0 == $intern_35) {
          content = meta.getAttribute($intern_30);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_33 + content + $intern_36);
            }
          }
        }
      }
    }
  }

  function __gwt_getMetaProperty(name_0){
    var value_0 = metaProps[name_0];
    return value_0 == null?null:value_0;
  }

  function unflattenKeylistIntoAnswers(propValArray, value_0){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value_0;
  }

  function computePropValue(propName){
    var value_0 = providers[propName](), allowedValuesMap = values[propName];
    if (value_0 in allowedValuesMap) {
      return value_0;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value_0);
    }
    throw null;
  }

  var frameInjected;
  function maybeInjectFrame(){
    if (!frameInjected) {
      frameInjected = true;
      var iframe = $doc.createElement($intern_37);
      iframe.src = $intern_38;
      iframe.id = $intern_1;
      iframe.style.cssText = $intern_39;
      iframe.tabIndex = -1;
      $doc.body.appendChild(iframe);
      $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_8, millis:(new Date).getTime(), type:$intern_40});
      iframe.contentWindow.location.replace(base + initialHtml);
    }
  }

  providers[$intern_41] = function(){
    var ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf($intern_42) != -1)
      return $intern_42;
    if (ua.indexOf($intern_43) != -1)
      return $intern_43;
    if (ua.indexOf($intern_44) != -1) {
      if ($doc.documentMode >= 10)
        return $intern_45;
      if ($doc.documentMode >= 9)
        return $intern_46;
      if ($doc.documentMode >= 8)
        return $intern_47;
      if (ua.indexOf($intern_48) != -1)
        return $intern_49;
      if (ua.indexOf($intern_50) != -1)
        return $intern_51;
      return $intern_45;
    }
    if (ua.indexOf($intern_52) != -1) {
      if (ua.indexOf($intern_53) != -1)
        return $intern_54;
      if (ua.indexOf($intern_55) != -1)
        return $intern_56;
      return $intern_57;
    }
    if (ua.indexOf($intern_58) != -1) {
      if (ua.indexOf($intern_59) != -1)
        return $intern_60;
      return $intern_61;
    }
    if (ua.indexOf($intern_62) != -1)
      return $intern_63;
    return null;
  }
  ;
  values[$intern_41] = {air:0, chrome:1, gecko1_8:2, gecko1_9:3, ie10:4, ie8:5, ie9:6, safari3:7, safari4:8, safari5:9};
  providers[$intern_64] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (function(){
      return ua.indexOf($intern_65) != -1;
    }
    ())
      return $intern_52;
    if (function(){
      return ua.indexOf($intern_44) != -1 && $doc.documentMode >= 10;
    }
    ())
      return $intern_45;
    if (function(){
      return ua.indexOf($intern_44) != -1 && $doc.documentMode >= 9;
    }
    ())
      return $intern_46;
    if (function(){
      return ua.indexOf($intern_44) != -1 && $doc.documentMode >= 8;
    }
    ())
      return $intern_47;
    if (function(){
      return ua.indexOf($intern_58) != -1;
    }
    ())
      return $intern_60;
    return $intern_66;
  }
  ;
  values[$intern_64] = {gecko1_8:0, ie10:1, ie8:2, ie9:3, safari:4};
  providers[$intern_67] = function(){
    var ua = $wnd.navigator.userAgent.toLowerCase();
    if (ua.indexOf($intern_68) != -1 || ua.indexOf($intern_69) != -1) {
      return $intern_70;
    }
    if (ua.indexOf($intern_71) != -1) {
      return $intern_71;
    }
    if (ua.indexOf($intern_72) != -1 || ua.indexOf($intern_73) != -1) {
      return $intern_72;
    }
    return $intern_66;
  }
  ;
  values[$intern_67] = {linux:0, mac:1, unknown:2, windows:3};
  dscrum.onScriptLoad = function(){
    if (frameInjected) {
      loadDone = true;
      maybeStartModule();
    }
  }
  ;
  dscrum.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_74, millis:(new Date).getTime(), type:$intern_9});
    maybeStartModule();
  }
  ;
  processMetas();
  computeScriptBase();
  var strongName;
  var initialHtml;
  if (isHostedMode()) {
    if ($wnd.external && ($wnd.external.initModule && $wnd.external.initModule($intern_1))) {
      $wnd.location.reload();
      return;
    }
    initialHtml = $intern_75;
    strongName = $intern_0;
  }
  $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date).getTime(), type:$intern_76});
  if (!isHostedMode()) {
    try {
      unflattenKeylistIntoAnswers([$intern_45, $intern_45, $intern_71], $intern_77);
      unflattenKeylistIntoAnswers([$intern_45, $intern_45, $intern_70], $intern_77 + $intern_78);
      unflattenKeylistIntoAnswers([$intern_45, $intern_45, $intern_66], $intern_77 + $intern_79);
      unflattenKeylistIntoAnswers([$intern_45, $intern_45, $intern_72], $intern_77 + $intern_80);
      unflattenKeylistIntoAnswers([$intern_46, $intern_46, $intern_71], $intern_81);
      unflattenKeylistIntoAnswers([$intern_46, $intern_46, $intern_70], $intern_81 + $intern_78);
      unflattenKeylistIntoAnswers([$intern_46, $intern_46, $intern_66], $intern_81 + $intern_79);
      unflattenKeylistIntoAnswers([$intern_46, $intern_46, $intern_72], $intern_81 + $intern_80);
      unflattenKeylistIntoAnswers([$intern_60, $intern_60, $intern_71], $intern_82);
      unflattenKeylistIntoAnswers([$intern_60, $intern_60, $intern_70], $intern_82 + $intern_78);
      unflattenKeylistIntoAnswers([$intern_60, $intern_60, $intern_66], $intern_82 + $intern_79);
      unflattenKeylistIntoAnswers([$intern_60, $intern_60, $intern_72], $intern_82 + $intern_80);
      unflattenKeylistIntoAnswers([$intern_61, $intern_60, $intern_71], $intern_82 + $intern_83);
      unflattenKeylistIntoAnswers([$intern_61, $intern_60, $intern_70], $intern_82 + $intern_84);
      unflattenKeylistIntoAnswers([$intern_61, $intern_60, $intern_66], $intern_82 + $intern_85);
      unflattenKeylistIntoAnswers([$intern_61, $intern_60, $intern_72], $intern_82 + $intern_86);
      unflattenKeylistIntoAnswers([$intern_63, $intern_52, $intern_71], $intern_87);
      unflattenKeylistIntoAnswers([$intern_63, $intern_52, $intern_70], $intern_87 + $intern_78);
      unflattenKeylistIntoAnswers([$intern_54, $intern_52, $intern_66], $intern_87 + $intern_88);
      unflattenKeylistIntoAnswers([$intern_54, $intern_52, $intern_72], $intern_87 + $intern_89);
      unflattenKeylistIntoAnswers([$intern_56, $intern_52, $intern_71], $intern_87 + $intern_90);
      unflattenKeylistIntoAnswers([$intern_56, $intern_52, $intern_70], $intern_87 + $intern_91);
      unflattenKeylistIntoAnswers([$intern_56, $intern_52, $intern_66], $intern_87 + $intern_92);
      unflattenKeylistIntoAnswers([$intern_56, $intern_52, $intern_72], $intern_87 + $intern_93);
      unflattenKeylistIntoAnswers([$intern_57, $intern_52, $intern_71], $intern_87 + $intern_94);
      unflattenKeylistIntoAnswers([$intern_57, $intern_52, $intern_70], $intern_87 + $intern_95);
      unflattenKeylistIntoAnswers([$intern_57, $intern_52, $intern_66], $intern_87 + $intern_96);
      unflattenKeylistIntoAnswers([$intern_57, $intern_52, $intern_72], $intern_87 + $intern_97);
      unflattenKeylistIntoAnswers([$intern_63, $intern_52, $intern_66], $intern_87 + $intern_79);
      unflattenKeylistIntoAnswers([$intern_63, $intern_52, $intern_72], $intern_87 + $intern_80);
      unflattenKeylistIntoAnswers([$intern_42, $intern_52, $intern_71], $intern_87 + $intern_83);
      unflattenKeylistIntoAnswers([$intern_42, $intern_52, $intern_70], $intern_87 + $intern_84);
      unflattenKeylistIntoAnswers([$intern_42, $intern_52, $intern_66], $intern_87 + $intern_85);
      unflattenKeylistIntoAnswers([$intern_42, $intern_52, $intern_72], $intern_87 + $intern_86);
      unflattenKeylistIntoAnswers([$intern_54, $intern_52, $intern_71], $intern_87 + $intern_98);
      unflattenKeylistIntoAnswers([$intern_54, $intern_52, $intern_70], $intern_87 + $intern_99);
      unflattenKeylistIntoAnswers([$intern_47, $intern_47, $intern_71], $intern_100);
      unflattenKeylistIntoAnswers([$intern_47, $intern_47, $intern_70], $intern_100 + $intern_78);
      unflattenKeylistIntoAnswers([$intern_47, $intern_47, $intern_66], $intern_100 + $intern_79);
      unflattenKeylistIntoAnswers([$intern_47, $intern_47, $intern_72], $intern_100 + $intern_80);
      strongName = answers[computePropValue($intern_41)][computePropValue($intern_64)][computePropValue($intern_67)];
      var idx = strongName.indexOf($intern_101);
      if (idx != -1) {
        softPermutationId = Number(strongName.substring(idx + 1));
        strongName = strongName.substring(0, idx);
      }
      initialHtml = strongName + $intern_102;
    }
     catch (e) {
      return;
    }
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      if (!__gwt_stylesLoaded[$intern_103]) {
        var l = $doc.createElement($intern_104);
        __gwt_stylesLoaded[$intern_103] = l;
        l.setAttribute($intern_105, $intern_106);
        l.setAttribute($intern_107, base + $intern_103);
        $doc.getElementsByTagName($intern_108)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_109]) {
        var l = $doc.createElement($intern_104);
        __gwt_stylesLoaded[$intern_109] = l;
        l.setAttribute($intern_105, $intern_106);
        l.setAttribute($intern_107, base + $intern_109);
        $doc.getElementsByTagName($intern_108)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_110, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_110, function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc.readyState)) {
      maybeInjectFrame();
      onBodyDone();
    }
  }
  , 50);
  $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date).getTime(), type:$intern_9});
  $stats && $stats({moduleName:$intern_1, sessionId:$sessionId, subSystem:$intern_2, evtGroup:$intern_74, millis:(new Date).getTime(), type:$intern_4});
  $doc.write($intern_111);
}

dscrum();
