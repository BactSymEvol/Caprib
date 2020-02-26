package modele;

import java.util.HashMap;

public class EXdistance {
	
	private HashMap<String, HashMap<String, Integer>> ex = new HashMap<String,HashMap<String, Integer>>();

	
	private HashMap<String,Integer> s = new HashMap<String,Integer>();
	private HashMap<String,Integer> r = new HashMap<String,Integer>();
	private HashMap<String,Integer> l = new HashMap<String,Integer>();
	private HashMap<String,Integer> p = new HashMap<String,Integer>();
	private HashMap<String,Integer> t = new HashMap<String,Integer>();
	private HashMap<String,Integer> a = new HashMap<String,Integer>();
	private HashMap<String,Integer> v = new HashMap<String,Integer>();
	private HashMap<String,Integer> g = new HashMap<String,Integer>();
	private HashMap<String,Integer> i = new HashMap<String,Integer>();
	private HashMap<String,Integer> f = new HashMap<String,Integer>();
	private HashMap<String,Integer> y = new HashMap<String,Integer>();
	private HashMap<String,Integer> c = new HashMap<String,Integer>();
	private HashMap<String,Integer> h = new HashMap<String,Integer>();
	private HashMap<String,Integer> q = new HashMap<String,Integer>();
	private HashMap<String,Integer> n = new HashMap<String,Integer>();
	private HashMap<String,Integer> k = new HashMap<String,Integer>();
	private HashMap<String,Integer> d = new HashMap<String,Integer>();
	private HashMap<String,Integer> e = new HashMap<String,Integer>();
	private HashMap<String,Integer> m = new HashMap<String,Integer>();
	private HashMap<String,Integer> w = new HashMap<String,Integer>();
	private HashMap<String,Integer> EXdest = new HashMap<String,Integer>();
	
	public EXdistance(){
		//C	.	258	121	201	334	288	109	109	270	383	258	306	252	169	109	347	 89	349	349	139	280
		c.put("S", 258);
		c.put("T", 121);
		c.put("P", 201);
		c.put("A", 334);
		c.put("G", 288);
		c.put("N", 109);
		c.put("D", 109);
		c.put("E", 270);
		c.put("Q", 383);	
		c.put("H", 258);
		c.put("R", 306);
		c.put("K", 252);
		c.put("M", 169);
		c.put("I", 109);
		c.put("L", 347);
		c.put("V", 89);
		c.put("F", 349);
		c.put("Y", 349);
		c.put("W", 139);
		c.put("EXsrc", 280);
			
		//S	373	.	481	249	490	418	390	314	343	352	353	363	275	321	270	295	358	334	294	160	351
		s.put("C", 373);
		s.put("T", 481);
		s.put("P", 249);
		s.put("A", 490);
		s.put("G", 418);
		s.put("N", 390);
		s.put("D", 314);
		s.put("E", 343);
		s.put("Q", 352);	
		s.put("H", 353);
		s.put("R", 363);
		s.put("K", 275);
		s.put("M", 321);
		s.put("I", 270);
		s.put("L", 295);
		s.put("V", 358);
		s.put("F", 334);
		s.put("Y", 294);
		s.put("W", 160);
		s.put("EXsrc", 351);
		//T	325	408	.	164	402	332	240	190	212	308	246	299	256	152	198	271	362	273	260	 66	287		
		t.put("C", 325);
		t.put("S", 408);
		t.put("P", 164);
		t.put("A", 402);
		t.put("G", 332);
		t.put("N", 240);
		t.put("D", 190);
		t.put("E", 212);
		t.put("Q", 308);	
		t.put("H", 246);
		t.put("R", 299);
		t.put("K", 256);
		t.put("M", 152);
		t.put("I", 198);
		t.put("L", 271);
		t.put("V", 362);
		t.put("F", 273);
		t.put("Y", 260);
		t.put("W", 66);
		t.put("EXsrc", 287);		
		//P	345	392	286	.	454	404	352	254	346	384	369	254	231	257	204	258	421	339	298	305	335
		p.put("C", 345);
		p.put("S", 392);
		p.put("T", 286);
		p.put("A", 454);
		p.put("G", 404);
		p.put("N", 352);
		p.put("D", 254);
		p.put("E", 346);
		p.put("Q", 384);
		p.put("H", 369);
		p.put("R", 254);
		p.put("K", 231);
		p.put("M", 257);
		p.put("I", 204);
		p.put("L", 258);
		p.put("V", 421);
		p.put("F", 339);
		p.put("Y", 298);
		p.put("W", 305);
		p.put("EXsrc", 335);
		
		//A	393	384	312	243	.	387	430	193	275	320	301	295	225	549	245	313	319	305	286	165	312
		a.put("C", 393);
		a.put("S", 384);
		a.put("T", 312);
		a.put("P", 243);
		a.put("G", 387);
		a.put("N", 430);
		a.put("D", 193);
		a.put("E", 275);
		a.put("Q", 320);
		a.put("H", 301);
		a.put("R", 295);
		a.put("K", 225);
		a.put("M", 549);
		a.put("I", 245);
		a.put("L", 313);
		a.put("V", 319);
		a.put("F", 305);
		a.put("Y", 286);
		a.put("W", 165);
		a.put("EXsrc", 312);		
		
		//G	267	304	187	140	369	.	210	188	206	272	235	178	219	197	110	193	208	168	188	173	228
		g.put("C", 267);
		g.put("S", 304);
		g.put("T", 187);
		g.put("P", 140);
		g.put("A", 369);
		g.put("N", 210);
		g.put("D", 188);
		g.put("E", 206);
		g.put("Q", 272);
		g.put("H", 235);
		g.put("R", 178);
		g.put("K", 219);
		g.put("M", 197);
		g.put("I", 110);
		g.put("L", 193);
		g.put("V", 208);
		g.put("F", 168);
		g.put("Y", 188);
		g.put("W", 173);
		g.put("EXsrc", 228);
				
		//N	234	355	329	275	400	391	.	208	257	298	248	252	183	236	184	233	233	210	251	120	272
		n.put("C", 234);
		n.put("S", 355);
		n.put("T", 329);
		n.put("P", 275);
		n.put("A", 400);
		n.put("G", 391);
		n.put("D", 208);
		n.put("E", 257);
		n.put("Q", 298);
		n.put("H", 248);
		n.put("R", 252);
		n.put("K", 183);
		n.put("M", 236);
		n.put("I", 184);
		n.put("L", 233);
		n.put("V", 233);
		n.put("F", 233);
		n.put("Y", 210);
		n.put("W", 251);
		n.put("EXsrc", 120);
		
		//D	285	275	245	220	293	264	201	344	263	298	252	208	245	299	236	175	233	227	103	258
		d.put("C", 285);
		d.put("S", 275);
		d.put("T", 245);
		d.put("P", 220);
		d.put("A", 293);
		d.put("G", 264);
		d.put("N", 201);
		d.put("E", 344);
		d.put("Q", 263);
		d.put("H", 298);
		d.put("R", 252);
		d.put("K", 208);
		d.put("M", 245);
		d.put("I", 299);
		d.put("L", 236);
		d.put("V", 175);
		d.put("F", 233);
		d.put("Y", 227);
		d.put("W", 103);
		d.put("EXsrc", 258);		
		
		//E	332	355	292	216	520	407	258	533	.	341	380	279	323	219	450	321	351	342	348	145	363
		e.put("C", 332);
		e.put("S", 355);
		e.put("T", 292);
		e.put("P", 216);
		e.put("A", 520);
		e.put("G", 407);
		e.put("N", 258);
		e.put("D", 533);
		e.put("Q", 341);
		e.put("H", 380);
		e.put("R", 279);
		e.put("K", 323);
		e.put("M", 219);
		e.put("I", 450);
		e.put("L", 321);
		e.put("V", 351);
		e.put("F", 342);
		e.put("Y", 348);
		e.put("W", 145);
		e.put("EXsrc", 363);
		
		//Q	383	443	361	212	499	406	338	 68	439	.396 366 354 504 467 391 603 383 361 159 386		
		q.put("C", 383);
		q.put("S", 443);
		q.put("T", 361);
		q.put("P", 212);
		q.put("A", 499);
		q.put("G", 406);
		q.put("N", 338);
		q.put("D", 68);
		q.put("E", 439);
		q.put("H", 396);
		q.put("R", 366);
		q.put("K", 354);
		q.put("M", 504);
		q.put("I", 467);
		q.put("L", 391);
		q.put("V", 603);
		q.put("F", 383);
		q.put("Y", 361);
		q.put("W", 159);
		q.put("EXsrc", 386);	
		
		//H	331	365	205	220	462	370	225	141	319	301	275	332	315	205	364	255	328	260	 72	303		
		h.put("C", 331);
		h.put("S", 365);
		h.put("T", 205);
		h.put("P", 220);
		h.put("A", 462);
		h.put("G", 370);
		h.put("N", 225);
		h.put("D", 141);
		h.put("E", 319);
		h.put("Q", 301);
		h.put("R", 275);
		h.put("K", 332);
		h.put("M", 315);
		h.put("I", 205);
		h.put("L", 364);
		h.put("V", 255);
		h.put("F", 328);
		h.put("Y", 260);
		h.put("W", 72);
		h.put("EXsrc", 303);
		
		//R	225	270	199	145	459	251	 67	124	250	288	263	.	306	 68	139	242	189	213	272	 63	259
		r.put("C", 225);
		r.put("S", 270);
		r.put("T", 199);
		r.put("P", 145);
		r.put("A", 459);
		r.put("G", 251);
		r.put("N", 67);
		r.put("D", 124);
		r.put("E", 250);
		r.put("Q", 288);
		r.put("H", 263);
		r.put("K", 306);
		r.put("M", 68);
		r.put("I", 139);
		r.put("L", 242);
		r.put("V", 189);
		r.put("F", 213);
		r.put("Y", 272);
		r.put("W", 63);
		r.put("EXsrc", 259);		
			
		//K	331	376	476	252	600	492	457	465	272	441	362	440	.	414	491	301	487	360	343	218	409
		k.put("C", 331);
		k.put("S", 376);
		k.put("T", 476);
		k.put("P", 252);
		k.put("A", 600);
		k.put("G", 492);
		k.put("N", 457);
		k.put("D", 465);
		k.put("E", 272);
		k.put("Q", 441);
		k.put("H", 362);
		k.put("R", 440);
		k.put("M", 414);
		k.put("I", 491);
		k.put("L", 301);
		k.put("V", 487);
		k.put("F", 360);
		k.put("Y", 343);
		k.put("W", 218);
		k.put("EXsrc", 409);
		
		//M	347	353	261	 85	357	218	544	392	287	394	278	112	135	.	612	513	354	330	308	633	307
		m.put("C", 347);
		m.put("S", 353);
		m.put("T", 261);
		m.put("P", 85);
		m.put("A", 357);
		m.put("G", 218);
		m.put("N", 544);
		m.put("D", 392);
		m.put("E", 287);
		m.put("Q", 394);
		m.put("H", 278);
		m.put("R", 112);
		m.put("K", 135);
		m.put("I", 612);
		m.put("L", 513);
		m.put("V", 354);
		m.put("F", 330);
		m.put("Y", 308);
		m.put("W", 633);
		m.put("EXsrc", 307);
		
		//I	362	196	193	145	326	160	172	 27	197	191	221	124	121	279	.	417	494	331	323	 73	252
		i.put("C", 362);
		i.put("S", 196);
		i.put("T", 193);
		i.put("P", 145);
		i.put("A", 326);
		i.put("G", 160);
		i.put("N", 172);
		i.put("D", 27);
		i.put("E", 197);
		i.put("Q", 191);
		i.put("H", 221);
		i.put("R", 124);
		i.put("K", 121);
		i.put("M", 279);
		i.put("L", 417);
		i.put("V", 494);
		i.put("F", 331);
		i.put("Y", 323);
		i.put("W", 73);
		i.put("EXsrc", 252);
		
		//L	366	212	165	146	343	201	162	112	199	250	288	185	171	367	301	.	275	336	295	152	248
		l.put("C", 366);
		l.put("S", 212);
		l.put("T", 165);
		l.put("P", 146);
		l.put("A", 343);
		l.put("G", 201);
		l.put("N", 162);
		l.put("D", 112);
		l.put("E", 199);
		l.put("Q", 250);
		l.put("H", 288);
		l.put("R", 185);
		l.put("K", 171);
		l.put("M", 367);
		l.put("I", 301);
		l.put("V", 275);
		l.put("F", 336);
		l.put("Y", 295);
		l.put("W", 152);
		l.put("EXsrc", 248);
		
		//V	382	326	398	201	389	269	108	228	192	280	253	190	197	562	537	333	.	207	209	286	277
		v.put("C", 382);
		v.put("S", 326);
		v.put("T", 398);
		v.put("P", 201);
		v.put("A", 389);
		v.put("G", 269);
		v.put("N", 108);
		v.put("D", 228);
		v.put("E", 192);
		v.put("Q", 280);
		v.put("H", 253);
		v.put("R", 190);
		v.put("K", 197);
		v.put("M", 562);
		v.put("I", 537);
		v.put("L", 333);
		v.put("F", 207);
		v.put("Y", 209);
		v.put("W", 286);
		v.put("EXsrc", 277);
		
		//F	176	152	257	112	236	 94	136	 90	 62	216	237	122	 85	255	181	296	291	.	332	232	193	
		f.put("C", 176);
		f.put("S", 152);
		f.put("T", 257);
		f.put("P", 112);
		f.put("A", 236);
		f.put("G", 94);
		f.put("N", 136);
		f.put("D", 90);
		f.put("E", 62);
		f.put("Q", 216);
		f.put("H", 237);
		f.put("R", 122);
		f.put("K", 85);
		f.put("M", 255);
		f.put("I", 181);
		f.put("L", 296);
		f.put("V", 291);
		f.put("Y", 332);
		f.put("W", 232);
		f.put("EXsrc", 193);
				
		//Y	142	173	.	194	402	357	129	 87	176	369	197	340	171	392	.	362	.	360	.	303	258		
		y.put("C", 142);
		y.put("S", 173);
		y.put("T", -1000);
		y.put("P", 194);
		y.put("A", 402);
		y.put("G", 357);
		y.put("N", 129);
		y.put("D", 87);
		y.put("E", 176);
		y.put("Q", 369);
		y.put("H", 197);
		y.put("R", 340);
		y.put("K", 171);
		y.put("M", 392);
		y.put("I", -1000);
		y.put("L", 362);
		y.put("V", -1000);
		y.put("F", 360);
		y.put("W", 303);
		y.put("EXsrc", 258);	
		
		//W	137	 92	 17	 66	 63	162	.	.	 65	 61	239	103	 54	110	.	177	110	364	281	.	142
		w.put("C", 137);
		w.put("S", 92);
		w.put("T", 17);
		w.put("P", 66);
		w.put("A", 63);
		w.put("G", 162);
		w.put("N", -1000);
		w.put("D", -1000);
		w.put("E", 65);
		w.put("Q", 61);
		w.put("H", 239);
		w.put("R", 103);
		w.put("K", 54);
		w.put("M", 110);
		w.put("I", -1000);
		w.put("L", 177);
		w.put("V", 110);
		w.put("F", 364);
		w.put("Y", 281);
		w.put("EXsrc", 142);
		
		//EXdest	315	311	293	192	411	321	258	225	262	305	290	255	225	314	293	307	305	294	279	172	291
		EXdest.put("C", 315);		
		EXdest.put("S", 311);
		EXdest.put("T", 293);
		EXdest.put("P", 192);
		EXdest.put("A", 411);
		EXdest.put("G", 321);
		EXdest.put("N", 258);
		EXdest.put("D", 225);
		EXdest.put("E", 262);
		EXdest.put("Q", 305);
		EXdest.put("H", 290);
		EXdest.put("R", 255);
		EXdest.put("K", 225);
		EXdest.put("M", 314);
		EXdest.put("I", 293);
		EXdest.put("L", 307);
		EXdest.put("V", 305);
		EXdest.put("F", 294);
		EXdest.put("Y", 279);
		EXdest.put("W", 172);
		
		ex.put("EXdest", EXdest);
		ex.put("C", c);	
		ex.put("W", w);
		ex.put("Y", y);
		ex.put("V", v);
		ex.put("F", f);
		ex.put("L", l);
		ex.put("I", i);
		ex.put("R", r);	
		ex.put("M", m);
		ex.put("K", k);
		ex.put("H", h);
		ex.put("Q", q);
		ex.put("E", e);
		ex.put("S",s);
		ex.put("T", t);
		ex.put("P", p);
		ex.put("A", a);
		ex.put("G", g);
		ex.put("N", n);
		ex.put("D", d);
			
	
				
	}	

	public Integer getEX(String aa1, String aa2){	
		//A/P
		String op1 = "EXdest";
		String op2 = "EXsrc";
		if(!aa1.equals("EXdest")){
			op1=aa1.toUpperCase();
		}
		if(!aa2.equals("EXsrc")){
			op2=aa2.toUpperCase();
		}
		
		Integer value=-1000;
		boolean ver=true;
		while(ver){
			if (ex.containsKey(op1)){
				if(ex.get(op1).containsKey(op2)){
					value=ex.get(op1).get(op2);
					ver=false;
				}else{
					value=-1000;
					ver=false;
				}
			}else{
				value=-1000;
				ver=false;
			}
		}
		return value;
		
	}

}
