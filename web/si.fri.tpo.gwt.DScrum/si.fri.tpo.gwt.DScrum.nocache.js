function si_fri_tpo_gwt_DScrum(){var S='',Ab='" for "gwt:onLoadErrorFn"',yb='" for "gwt:onPropertyErrorFn"',lb='"><\/script>',ab='#',Ub='.cache.html',cb='/',ob='//',Tb=':',sb='::',ac='<script defer="defer">si_fri_tpo_gwt_DScrum.onInjectionDone(\'si.fri.tpo.gwt.DScrum\')<\/script>',kb='<script id="',vb='=',bb='?',xb='Bad handler "',_b='DOMContentLoaded',Sb="GWT module 'si.fri.tpo.gwt.DScrum' may need to be (re)compiled",mb='SCRIPT',jb='__gwt_marker_si.fri.tpo.gwt.DScrum',nb='base',fb='baseUrl',W='begin',V='bootstrap',eb='clear.cache.gif',ub='content',_='end',Mb='gecko',Nb='gecko1_8',X='gwt.codesvr=',Y='gwt.hosted=',Z='gwt.hybrid',Vb='gwt/clean/clean.css',zb='gwt:onLoadErrorFn',wb='gwt:onPropertyErrorFn',tb='gwt:property',$b='head',Qb='hosted.html?si_fri_tpo_gwt_DScrum',Zb='href',Jb='ie10',Lb='ie8',Kb='ie9',Bb='iframe',db='img',Cb="javascript:''",Wb='link',Pb='loadExternalRefs',pb='meta',Eb='moduleRequested',$='moduleStartup',Ib='msie',qb='name',Db='position:absolute;width:0;height:0;border:none',Xb='rel',Hb='safari',gb='script',Rb='selectingPermutation',T='si.fri.tpo.gwt.DScrum',hb='si.fri.tpo.gwt.DScrum.nocache.js',rb='si.fri.tpo.gwt.DScrum::',U='startup',Yb='stylesheet',ib='undefined',Ob='unknown',Fb='user.agent',Gb='webkit';var m=window,n=document,o=m.__gwtStatsEvent?function(a){return m.__gwtStatsEvent(a)}:null,p=m.__gwtStatsSessionId?m.__gwtStatsSessionId:null,q,r,s,t=S,u={},v=[],w=[],A=[],B=0,C,D;o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:V,millis:(new Date).getTime(),type:W});if(!m.__gwt_stylesLoaded){m.__gwt_stylesLoaded={}}if(!m.__gwt_scriptsLoaded){m.__gwt_scriptsLoaded={}}function F(){var b=false;try{var c=m.location.search;return (c.indexOf(X)!=-1||(c.indexOf(Y)!=-1||m.external&&m.external.gwtOnLoad))&&c.indexOf(Z)==-1}catch(a){}F=function(){return b};return b}
function G(){if(q&&r){var b=n.getElementById(T);var c=b.contentWindow;if(F()){c.__gwt_getProperty=function(a){return K(a)}}si_fri_tpo_gwt_DScrum=null;c.gwtOnLoad(C,T,t,B);o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:$,millis:(new Date).getTime(),type:_})}}
function H(){function e(a){var b=a.lastIndexOf(ab);if(b==-1){b=a.length}var c=a.indexOf(bb);if(c==-1){c=a.length}var d=a.lastIndexOf(cb,Math.min(c,b));return d>=0?a.substring(0,d+1):S}
function f(a){if(a.match(/^\w+:\/\//)){}else{var b=n.createElement(db);b.src=a+eb;a=e(b.src)}return a}
function g(){var a=J(fb);if(a!=null){return a}return S}
function h(){var a=n.getElementsByTagName(gb);for(var b=0;b<a.length;++b){if(a[b].src.indexOf(hb)!=-1){return e(a[b].src)}}return S}
function i(){var a;if(typeof isBodyLoaded==ib||!isBodyLoaded()){var b=jb;var c;n.write(kb+b+lb);c=n.getElementById(b);a=c&&c.previousSibling;while(a&&a.tagName!=mb){a=a.previousSibling}if(c){c.parentNode.removeChild(c)}if(a&&a.src){return e(a.src)}}return S}
function j(){var a=n.getElementsByTagName(nb);if(a.length>0){return a[a.length-1].href}return S}
function k(){var a=n.location;return a.href==a.protocol+ob+a.host+a.pathname+a.search+a.hash}
var l=g();if(l==S){l=h()}if(l==S){l=i()}if(l==S){l=j()}if(l==S&&k()){l=e(n.location.href)}l=f(l);t=l;return l}
function I(){var b=document.getElementsByTagName(pb);for(var c=0,d=b.length;c<d;++c){var e=b[c],f=e.getAttribute(qb),g;if(f){f=f.replace(rb,S);if(f.indexOf(sb)>=0){continue}if(f==tb){g=e.getAttribute(ub);if(g){var h,i=g.indexOf(vb);if(i>=0){f=g.substring(0,i);h=g.substring(i+1)}else{f=g;h=S}u[f]=h}}else if(f==wb){g=e.getAttribute(ub);if(g){try{D=eval(g)}catch(a){alert(xb+g+yb)}}}else if(f==zb){g=e.getAttribute(ub);if(g){try{C=eval(g)}catch(a){alert(xb+g+Ab)}}}}}}
function J(a){var b=u[a];return b==null?null:b}
function K(a){var b=w[a](),c=v[a];if(b in c){return b}var d=[];for(var e in c){d[c[e]]=e}if(D){D(a,d,b)}throw null}
var L;function M(){if(!L){L=true;var a=n.createElement(Bb);a.src=Cb;a.id=T;a.style.cssText=Db;a.tabIndex=-1;n.body.appendChild(a);o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:$,millis:(new Date).getTime(),type:Eb});a.contentWindow.location.replace(t+O)}}
w[Fb]=function(){var b=navigator.userAgent.toLowerCase();var c=function(a){return parseInt(a[1])*1000+parseInt(a[2])};if(function(){return b.indexOf(Gb)!=-1}())return Hb;if(function(){return b.indexOf(Ib)!=-1&&n.documentMode>=10}())return Jb;if(function(){return b.indexOf(Ib)!=-1&&n.documentMode>=9}())return Kb;if(function(){return b.indexOf(Ib)!=-1&&n.documentMode>=8}())return Lb;if(function(){return b.indexOf(Mb)!=-1}())return Nb;return Ob};v[Fb]={gecko1_8:0,ie10:1,ie8:2,ie9:3,safari:4};si_fri_tpo_gwt_DScrum.onScriptLoad=function(){if(L){r=true;G()}};si_fri_tpo_gwt_DScrum.onInjectionDone=function(){q=true;o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:Pb,millis:(new Date).getTime(),type:_});G()};I();H();var N;var O;if(F()){if(m.external&&(m.external.initModule&&m.external.initModule(T))){m.location.reload();return}O=Qb;N=S}o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:V,millis:(new Date).getTime(),type:Rb});if(!F()){try{alert(Sb);return;var P=N.indexOf(Tb);if(P!=-1){B=Number(N.substring(P+1));N=N.substring(0,P)}O=N+Ub}catch(a){return}}var Q;function R(){if(!s){s=true;if(!__gwt_stylesLoaded[Vb]){var a=n.createElement(Wb);__gwt_stylesLoaded[Vb]=a;a.setAttribute(Xb,Yb);a.setAttribute(Zb,t+Vb);n.getElementsByTagName($b)[0].appendChild(a)}G();if(n.removeEventListener){n.removeEventListener(_b,R,false)}if(Q){clearInterval(Q)}}}
if(n.addEventListener){n.addEventListener(_b,function(){M();R()},false)}var Q=setInterval(function(){if(/loaded|complete/.test(n.readyState)){M();R()}},50);o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:V,millis:(new Date).getTime(),type:_});o&&o({moduleName:T,sessionId:p,subSystem:U,evtGroup:Pb,millis:(new Date).getTime(),type:W});n.write(ac)}
si_fri_tpo_gwt_DScrum();