$wnd.showcase.runAsyncCallback18("function SH(){}\nfunction j0b(a,b){aD(a.a,b)}\nfunction $sb(a,b){this.b=a;this.a=b}\nfunction btb(a,b){this.b=a;this.a=b}\nfunction pI(a){return c4(aI,a)}\nfunction RH(){RH=oqc;QH=new SH}\nfunction Ssb(a,b){RTb(b,'Selected: '+a.og()+uuc+a.pg())}\nfunction w0b(){r0b();v0b.call(this,ts($doc,'password'),'gwt-PasswordTextBox')}\nfunction h9b(b){try{var c=b.document.selection.createRange();if(c.parentElement()!==b)return 0;return c.text.length}catch(a){return 0}}\nfunction g9b(b){try{var c=b.document.selection.createRange();if(c.parentElement()!==b)return -1;return -c.move(Rzc,-65535)}catch(a){return 0}}\nfunction Qsb(a,b){var c,d;c=new AYb;ls(c.e,_wc,4);xYb(c,a);if(b){d=new VTb('Selected: 0, 0');vj(a,new $sb(a,d),(vz(),vz(),uz));vj(a,new btb(a,d),(Sy(),Sy(),Ry));xYb(c,d)}return c}\nfunction j9b(b){try{var c=b.document.selection.createRange();if(c.parentElement()!==b)return 0;var d=c.text.length;var e=0;var f=c.duplicate();f.moveEnd(Rzc,-1);var g=f.text.length;while(g==d&&f.parentElement()==b&&c.compareEndPoints('StartToEnd',f)<=0){e+=2;f.moveEnd(Rzc,-1);g=f.text.length}return d+e}catch(a){return 0}}\nfunction i9b(b){try{var c=b.document.selection.createRange();if(c.parentElement()!==b)return -1;var d=c.duplicate();d.moveToElementText(b);d.setEndPoint('EndToStart',c);var e=d.text.length;var f=0;var g=d.duplicate();g.moveEnd(Rzc,-1);var i=g.text.length;while(i==e&&g.parentElement()==b){f+=2;g.moveEnd(Rzc,-1);i=g.text.length}return e+f}catch(a){return 0}}\nfunction Rsb(){var a,b,c,d,e,f;f=new T7b;ls(f.e,_wc,5);d=new u0b;u7b((YLb(),d.gb),Psc,'cwBasicText-textbox');j0b(d,(RH(),RH(),QH));b=new u0b;u7b(b.gb,Psc,'cwBasicText-textbox-disabled');ms(b.gb,Uyc,Qzc);_C(b.a);ks(b.gb,Lyc,true);Q7b(f,new $Tb('<b>Normal text box:<\\/b>'));Q7b(f,Qsb(d,true));Q7b(f,Qsb(b,false));c=new w0b;u7b(c.gb,Psc,'cwBasicText-password');a=new w0b;u7b(a.gb,Psc,'cwBasicText-password-disabled');ms(a.gb,Uyc,Qzc);_C(a.a);ks(a.gb,Lyc,true);Q7b(f,new $Tb('<br><br><b>Password text box:<\\/b>'));Q7b(f,Qsb(c,true));Q7b(f,Qsb(a,false));e=new H5b;u7b(e.gb,Psc,'cwBasicText-textarea');e.gb.rows=5;Q7b(f,new $Tb('<br><br><b>Text area:<\\/b>'));Q7b(f,Qsb(e,true));return f}\nvar Rzc='character',Qzc='read only';k3(365,366,{},SH);_.Jd=function TH(a){return pI((jI(),a))?(qF(),pF):(qF(),oF)};var QH;k3(750,1,grc);_.sc=function Ysb(){V5(this.a,Rsb())};k3(751,1,Qqc,$sb);_.Lc=function _sb(a){Ssb(this.b,this.a)};k3(752,1,drc,btb);_.Jc=function ctb(a){Ssb(this.b,this.a)};k3(1096,977,wqc);_.og=function m0b(){return g9b((YLb(),this.gb))};_.pg=function n0b(){return h9b((YLb(),this.gb))};k3(1093,1094,wqc,w0b);k3(1149,1095,wqc);_.og=function I5b(){return i9b((YLb(),this.gb))};_.pg=function J5b(){return j9b((YLb(),this.gb))};var vU=tfc(Vxc,'CwBasicText$2',751),wU=tfc(Vxc,'CwBasicText$3',752),sZ=tfc(Pxc,'PasswordTextBox',1093),RO=tfc(pyc,'AnyRtlDirectionEstimator',365);Wrc(Zn)(18);\n//# sourceURL=showcase-18.js\n")
