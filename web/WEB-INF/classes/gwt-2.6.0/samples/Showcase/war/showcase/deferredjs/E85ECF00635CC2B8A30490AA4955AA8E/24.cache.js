$wnd.showcase.runAsyncCallback24("function q5b(a){this.b=a}\nfunction t5b(a){this.b=a}\nfunction w5b(a){this.b=a}\nfunction D5b(a,b){this.b=a;this.c=b}\nfunction _s(a,b){a.remove(b)}\nfunction fFc(a,b){ZEc(a,b);_s((drc(),a.hb),b)}\nfunction Wqc(){var a;if(!Tqc||Yqc()){a=new q3c;Xqc(a);Tqc=a}return Tqc}\nfunction Yqc(){var a=$doc.cookie;if(a!=Uqc){Uqc=a;return true}else{return false}}\nfunction Zqc(a){a=encodeURIComponent(a);$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction l5b(a,b){var c,d,e,f;$s(aFc(a.d));f=0;e=jP(Wqc());for(d=t0c(e);d.b.Fe();){c=hnb(z0c(d),1);cFc(a.d,c);WXc(c,b)&&(f=aFc(a.d).options.length-1)}rp((kp(),jp),new D5b(a,f))}\nfunction m5b(a){var b,c,d,e;if(aFc(a.d).options.length<1){MHc(a.b,m8c);MHc(a.c,m8c);return}d=aFc(a.d).selectedIndex;b=bFc(a.d,d);c=(e=Wqc(),hnb(e.ue(b),1));MHc(a.b,b);MHc(a.c,c)}\nfunction Xqc(b){var c=$doc.cookie;if(c&&c!=m8c){var d=c.split(E9c);for(var e=0;e<d.length;++e){var f,g;var i=d[e].indexOf(R9c);if(i==-1){f=d[e];g=m8c}else{f=d[e].substring(0,i);g=d[e].substring(i+1)}if(Vqc){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.we(f,g)}}}\nfunction k5b(a){var b,c,d;c=new LCc(3,3);a.d=new hFc;b=new dvc('Delete');Aj((drc(),b.hb),Ofd,true);$Bc(c,0,0,'<b><b>Existing Cookies:<\\/b><\\/b>');bCc(c,0,1,a.d);bCc(c,0,2,b);a.b=new WHc;$Bc(c,1,0,'<b><b>Name:<\\/b><\\/b>');bCc(c,1,1,a.b);a.c=new WHc;d=new dvc('Set Cookie');Aj(d.hb,Ofd,true);$Bc(c,2,0,'<b><b>Value:<\\/b><\\/b>');bCc(c,2,1,a.c);bCc(c,2,2,d);Hj(d,new q5b(a),(bz(),bz(),az));Hj(a.d,new t5b(a),(Ty(),Ty(),Sy));Hj(b,new w5b(a),az);l5b(a,null);return c}\nMKb(793,1,D6c,q5b);_.Nc=function r5b(a){var b,c,d;c=ps($i(this.b.b),Ted);d=ps($i(this.b.c),Ted);b=new zmb(iKb(mKb((new xmb).q.getTime()),M6c));if(c.length<1){$rc('You must specify a cookie name');return}$qc(c,d,b);l5b(this.b,c)};MKb(794,1,E6c,t5b);_.Mc=function u5b(a){m5b(this.b)};MKb(795,1,D6c,w5b);_.Nc=function x5b(a){var b,c;c=aFc(this.b.d).selectedIndex;if(c>-1&&c<aFc(this.b.d).options.length){b=bFc(this.b.d,c);Zqc(b);fFc(this.b.d,c);m5b(this.b)}};MKb(796,1,G6c);_.xc=function B5b(){qNb(this.c,k5b(this.b))};MKb(797,1,{},D5b);_.zc=function E5b(){this.c<aFc(this.b.d).options.length&&gFc(this.b.d,this.c);m5b(this.b)};_.c=0;var Tqc=null,Uqc,Vqc=true;var fzb=SWc(Wdd,'CwCookies$1',793),gzb=SWc(Wdd,'CwCookies$2',794),hzb=SWc(Wdd,'CwCookies$3',795),jzb=SWc(Wdd,'CwCookies$5',797);t7c(lo)(24);\n//# sourceURL=showcase-24.js\n")