$wnd.showcase.runAsyncCallback4("function gnb(a,b,c){this.b=a;this.d=b;this.c=c}\nfunction o7(a){var b,c;b=yJ(a.b.xd(Xyc),147);if(b==null){c=oJ(q2,Dqc,1,['Cars','Sports','Vacation Spots']);a.b.zd(Xyc,c);return c}else{return b}}\nfunction n7(a){var b,c;b=yJ(a.b.xd(Wyc),147);if(b==null){c=oJ(q2,Dqc,1,['compact','sedan','coupe','convertible','SUV','truck']);a.b.zd(Wyc,c);return c}else{return b}}\nfunction p7(a){var b,c;b=yJ(a.b.xd(Yyc),147);if(b==null){c=oJ(q2,Dqc,1,[Pyc,Qyc,Ryc,Syc,'Lacrosse','Polo',Tyc,'Softball',Uyc]);a.b.zd(Yyc,c);return c}else{return b}}\nfunction q7(a){var b,c;b=yJ(a.b.xd(Zyc),147);if(b==null){c=oJ(q2,Dqc,1,['Carribean','Grand Canyon','Paris','Italy','New York','Las Vegas']);a.b.zd(Zyc,c);return c}else{return b}}\nfunction cnb(a,b,c){var d,e;$s(LZb(b));e=null;switch(c){case 0:e=n7(a.b);break;case 1:e=p7(a.b);break;case 2:e=q7(a.b);}for(d=0;d<e.length;d++){NZb(b,e[d])}}\nfunction bnb(a){var b,c,d,e,f,g,i;d=new LYb;As(d.f,kxc,20);b=new TZb(false);f=o7(a.b);for(e=0;e<f.length;e++){NZb(b,f[e])}PZb(b,'cwListBox-dropBox');c=new Z7b;As(c.f,kxc,4);W7b(c,new kUb('<b>Select a category:<\\/b>'));W7b(c,b);IYb(d,c);g=new TZb(true);PZb(g,$yc);(PLb(),g.hb).style[ytc]='11em';Pt(g.hb,10);i=new Z7b;As(i.f,kxc,4);W7b(i,new kUb('<b>Select all that apply:<\\/b>'));W7b(i,g);IYb(d,i);Hj(b,new gnb(a,g,b),(Ty(),Ty(),Sy));cnb(a,g,0);PZb(g,$yc);return d}\nvar $yc='cwListBox-multiBox',Wyc='cwListBoxCars',Xyc='cwListBoxCategories',Yyc='cwListBoxSports',Zyc='cwListBoxVacations';v3(658,1,nrc,gnb);_.Mc=function hnb(a){cnb(this.b,this.d,LZb(this.c).selectedIndex);PZb(this.d,$yc)};v3(659,1,prc);_.xc=function lnb(){_5(this.c,bnb(this.b))};var sT=Bfc(_xc,'CwListBox$1',658);dsc(lo)(4);\n//# sourceURL=showcase-4.js\n")