
package modele;

import java.util.HashMap;

/**
 * @author juan_
 *
 */
public class Grantham {
	//private ArrayList<HashMap<String, HashMap<String, Integer>>> grantham = new ArrayList< HashMap<String,HashMap<String, Integer>>>();
	static HashMap<String, HashMap<String, Integer>> grantham = new HashMap<String, HashMap<String, Integer>>();
	
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
	
	
	public Grantham(){	
		s.put("R", 110);
		s.put("L", 145);
		s.put("P", 74);
		s.put("T", 58);
		s.put("A", 99);
		s.put("V", 124);
		s.put("G", 56);
		s.put("I", 142);
		s.put("F", 155);
		s.put("Y", 144);
		s.put("C", 112);
		s.put("H", 89);
		s.put("Q", 68);
		s.put("N", 46);
		s.put("K", 121);
		s.put("D", 65);
		s.put("E", 80);
		s.put("M", 135);
		s.put("W", 177);
				
		r.put("L", 102);
		r.put("P", 103);
		r.put("T", 71);
		r.put("A", 112);
		r.put("V", 96);
		r.put("G", 125);
		r.put("I", 97);
		r.put("F", 97);
		r.put("Y", 77);
		r.put("C", 180);
		r.put("H", 29);
		r.put("Q", 43);
		r.put("N", 86);
		r.put("K", 26);
		r.put("D", 96);
		r.put("E", 54);
		r.put("M", 91);
		r.put("W", 101);		
		
		grantham.put("R", r);
		
		l.put("P", 98);
		l.put("T", 92);
		l.put("A", 96);
		l.put("V", 32);
		l.put("G", 138);
		l.put("I", 5);
		l.put("F", 22);
		l.put("Y", 36);
		l.put("C", 198);
		l.put("H", 99);
		l.put("Q", 113);
		l.put("N", 153);
		l.put("K", 107);
		l.put("D", 172);
		l.put("E", 138);
		l.put("M", 15);
		l.put("W", 61);		
		
		grantham.put("L", l);
		
		p.put("T", 38);
		p.put("A", 27);
		p.put("V", 68);
		p.put("G", 42);
		p.put("I", 95);
		p.put("F", 114);
		p.put("Y", 110);
		p.put("C", 169);
		p.put("H", 77);
		p.put("Q", 76);
		p.put("N", 91);
		p.put("K", 103);
		p.put("D", 108);
		p.put("E", 93);
		p.put("M", 87);
		p.put("W", 147);	
		
		grantham.put("P", p);
		
		t.put("A", 58);
		t.put("V", 69);
		t.put("G", 59);
		t.put("I", 89);
		t.put("F", 103);
		t.put("Y", 92);
		t.put("C", 149);
		t.put("H", 47);
		t.put("Q", 42);
		t.put("N", 65);
		t.put("K", 78);
		t.put("D", 85);
		t.put("E", 65);
		t.put("M", 81);
		t.put("W", 128);	
		
		grantham.put("T", t);
		
		a.put("V", 64);
		a.put("G", 60);
		a.put("I", 94);
		a.put("F", 113);
		a.put("Y", 112);
		a.put("C", 195);
		a.put("H", 86);
		a.put("Q", 91);
		a.put("N", 111);
		a.put("K", 106);
		a.put("D", 126);
		a.put("E", 107);
		a.put("M", 84);
		a.put("W", 148);	
		
		grantham.put("A", a);
		
		v.put("G", 109);
		v.put("I", 29);
		v.put("F", 50);
		v.put("Y", 55);
		v.put("C", 192);
		v.put("H", 84);
		v.put("Q", 96);
		v.put("N", 133);
		v.put("K", 97);
		v.put("D", 152);
		v.put("E", 121);
		v.put("M", 21);
		v.put("W", 88);	
		
		grantham.put("V", v);
		
		g.put("I", 135);
		g.put("F", 153);
		g.put("Y", 147);
		g.put("C", 159);
		g.put("H", 98);
		g.put("Q", 87);
		g.put("N", 80);
		g.put("K", 127);
		g.put("D", 94);
		g.put("E", 98);
		g.put("M", 127);
		g.put("W", 184);	
		
		grantham.put("G", g);
		
		i.put("F", 21);
		i.put("Y", 33);
		i.put("C", 198);
		i.put("H", 94);
		i.put("Q", 109);
		i.put("N", 149);
		i.put("K", 102);
		i.put("D", 168);
		i.put("E", 134);
		i.put("M", 10);
		i.put("W", 61);	
		
		grantham.put("I", i);
		
		f.put("Y", 22);
		f.put("C", 205);
		f.put("H", 100);
		f.put("Q", 116);
		f.put("N", 158);
		f.put("K", 102);
		f.put("D", 177);
		f.put("E", 140);
		f.put("M", 28);
		f.put("W", 40);	
		
		grantham.put("F", f);
		
		y.put("C", 194);
		y.put("H", 83);
		y.put("Q", 99);
		y.put("N", 143);
		y.put("K", 85);
		y.put("D", 160);
		y.put("E", 122);
		y.put("M", 36);
		y.put("W", 37);	
		grantham.put("Y", y);
		

		c.put("H", 174);
		c.put("Q", 154);
		c.put("N", 139);
		c.put("K", 202);
		c.put("D", 154);
		c.put("E", 170);
		c.put("M", 196);
		c.put("W", 215);	
		grantham.put("C", c);
			
		
		h.put("Q", 24);
		h.put("N", 68);
		h.put("K", 32);
		h.put("D", 81);
		h.put("E", 40);
		h.put("M", 87);
		h.put("W", 115);	
		
		grantham.put("H", h);
		
		q.put("N", 46);
		q.put("K", 53);
		q.put("D", 61);
		q.put("E", 29);
		q.put("M", 101);
		q.put("W", 130);	
		
		grantham.put("Q", q);
		
		n.put("K", 94);
		n.put("D", 23);
		n.put("E", 42);
		n.put("M", 142);
		n.put("W", 174);	
		grantham.put("N", n);

		k.put("D", 101);
		k.put("E", 56);
		k.put("M", 95);
		k.put("W", 110);	
		
		grantham.put("K", k);
		
		d.put("E", 45);
		d.put("M", 160);
		d.put("W", 181);	
		grantham.put("D", d);
		
		e.put("M", 126);
		e.put("W", 152);	
		
		grantham.put("E", e);
		
		m.put("W", 67);
		grantham.put("M", m);
		
		grantham.put("S", s);
		
			}	

	public Integer getGranthamEq(String aa1, String aa2){	
		//A/P
		String op1=aa1.toUpperCase();
		String op2=aa2.toUpperCase();	
		//Initialize value and ver
		Integer value=-1000;
		boolean ver=true;
		
		while(ver){
			//looking for the AA in the hash and so we validate it
		    if(op1.equals("W")){
			    op1=aa2.toUpperCase();
			    op2=aa1.toUpperCase();
		    }
		    if (grantham.containsKey(op1)){
			    if(grantham.get(op1).containsKey(op2)){
				    value=grantham.get(op1).get(op2);
				    ver=false;
			    }
			    else{
				    op1=aa2.toUpperCase();
				    op2=aa1.toUpperCase();
			    }
		    }
		    else{
			    value=-1;
			    ver=false;
		    }
		}		
		return value;
		
	}
}
