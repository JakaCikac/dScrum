$wnd.showcase.runAsyncCallback24("function eDb(a){this.b=a}\nfunction hDb(a){this.b=a}\nfunction kDb(a){this.b=a}\nfunction rDb(a,b){this.b=a;this.c=b}\nfunction _s(a,b){a.remove(b)}\nfunction Uac(a,b){Mac(a,b);_s((TYb(),a.hb),b)}\nfunction KYb(){var a;if(!HYb||MYb()){a=new eBc;LYb(a);HYb=a}return HYb}\nfunction MYb(){var a=$doc.cookie;if(a!=IYb){IYb=a;return true}else{return false}}\nfunction NYb(a){a=encodeURIComponent(a);$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction _Cb(a,b){var c,d,e,f;$s(Pac(a.d));f=0;e=HJ(KYb());for(d=hyc(e);d.b.Fe();){c=dW(nyc(d),1);Rac(a.d,c);Ktc(c,b)&&(f=Pac(a.d).options.length-1)}rp((kp(),jp),new rDb(a,f))}\nfunction aDb(a){var b,c,d,e;if(Pac(a.d).options.length<1){zdc(a.b,aGc);zdc(a.c,aGc);return}d=Pac(a.d).selectedIndex;b=Qac(a.d,d);c=(e=KYb(),dW(e.ue(b),1));zdc(a.b,b);zdc(a.c,c)}\nfunction LYb(b){var c=$doc.cookie;if(c&&c!=aGc){var d=c.split(sHc);for(var e=0;e<d.length;++e){var f,g;var i=d[e].indexOf(FHc);if(i==-1){f=d[e];g=aGc}else{f=d[e].substring(0,i);g=d[e].substring(i+1)}if(JYb){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.we(f,g)}}}\nfunction $Cb(a){var b,c,d;c=new y8b(3,3);a.d=new Wac;b=new S0b('Supprimer');Aj((TYb(),b.hb),hNc,true);N7b(c,0,0,'<b><b>Cookies existants:<\\/b><\\/b>');Q7b(c,0,1,a.d);Q7b(c,0,2,b);a.b=new Jdc;N7b(c,1,0,'<b><b>Nom:<\\/b><\\/b>');Q7b(c,1,1,a.b);a.c=new Jdc;d=new S0b('Sauvegarder Cookie');Aj(d.hb,hNc,true);N7b(c,2,0,'<b><b>Valeur:<\\/b><\\/b>');Q7b(c,2,1,a.c);Q7b(c,2,2,d);Hj(d,new eDb(a),(bz(),bz(),az));Hj(a.d,new hDb(a),(Ty(),Ty(),Sy));Hj(b,new kDb(a),az);_Cb(a,null);return c}\nzgb(731,1,rEc,eDb);_.Nc=function fDb(a){var b,c,d;c=ps($i(this.b.b),gMc);d=ps($i(this.b.c),gMc);b=new vV(Xfb(_fb((new tV).q.getTime()),AEc));if(c.length<1){NZb('Vous devez indiquer un nom de cookie');return}OYb(c,d,b);_Cb(this.b,c)};zgb(732,1,sEc,hDb);_.Mc=function iDb(a){aDb(this.b)};zgb(733,1,rEc,kDb);_.Nc=function lDb(a){var b,c;c=Pac(this.b.d).selectedIndex;if(c>-1&&c<Pac(this.b.d).options.length){b=Qac(this.b.d,c);NYb(b);Uac(this.b.d,c);aDb(this.b)}};zgb(734,1,uEc);_.xc=function pDb(){djb(this.c,$Cb(this.b))};zgb(735,1,{},rDb);_.zc=function sDb(){this.c<Pac(this.b.d).options.length&&Vac(this.b.d,this.c);aDb(this.b)};_.c=0;var HYb=null,IYb,JYb=true;var U4=Gsc(nLc,'CwCookies$1',731),V4=Gsc(nLc,'CwCookies$2',732),W4=Gsc(nLc,'CwCookies$3',733),Y4=Gsc(nLc,'CwCookies$5',735);hFc(lo)(24);\n//# sourceURL=showcase-24.js\n")
