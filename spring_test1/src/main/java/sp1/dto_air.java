package sp1;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class dto_air {
	//필드값 모두 사용
	String pidx, pid, pnm, passport,ptel,rcode,rair,person,money,pdata;
	
	public ArrayList<String> listdata(){
		ArrayList<String> al = new ArrayList<String>();
		al.add(getPidx());
		al.add(getPid());
		al.add(getPnm());
		al.add(getPassport());
		al.add(getPtel());
		al.add(getRcode());
		al.add(getRair());
		al.add(getPerson());
		al.add(getMoney());
		al.add(getPdata());
		
		return al;
	}
}
