package batis;

import java.sql.*;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class webpage {
	
	@Autowired	//xml에 등록된 bean에 대한 id값을 가져올 때 사용함
	BasicDataSource datasource;	//xml에 id 이름을 로드하여 해당 SQL 정보를 class에 전달하게 됩니다.(의존성 주입)
	
	
	@SuppressWarnings("unused")
	@Inject	//xml에 대한 데이터를 가져올 때 사용하는 의존성 주입
	private SqlSessionFactory sqlsesstionfactory;
	
	
	@Resource	//@Autowired 확장형
	private SqlSessionTemplate sqlsession;
	
	//해당 페이지에 데이터 갯수가 출력 되도록 mybatis사용
	
	//selectList, selectOne
	
	@RequestMapping("/data_select.do")
	public String data_select(@ModelAttribute("abc")data_vo data_vo) throws Exception {
		SqlSession se = sqlsesstionfactory.openSession();
		data_vo dv = se.selectOne("reviewDB.selectcount");
		System.out.println(dv.getCnt());
		
		return null;
	}
	
	/*
	@ModelAttribute : parameter, method 형태를 구성하게 됩니다. vo, dto 형태로 구성
	해당 name값을 vo, dto에 동일하게 세팅을 하며, Database Field명과 동일 할 경우
	요청한 값을 모두 Db에 저장 시킬 수 있습니다.
	*/
	
	/*
	@ModelAttribute(별명 명칭) - 1개 이상 사용할때 구분을 위해 별명을 붙여준다
	*/
	@RequestMapping("data_insert.do")
	public String data_insert(@ModelAttribute("abc") data_vo data_vo) throws Exception {
		//System.out.println(data_vo.getRname());
		SqlSession se = sqlsesstionfactory.openSession();
		int a = se.insert("reviewDB.review_insert",data_vo);
		if(a>0) {
			System.out.println("정상적으로 리뷰가 등록 되었습니다.");
		}else {
			System.out.println("SQL 문법오류 발생");
		}
		return "/WEB-INF/jsp/login";
		
	}
	
	
	
	@RequestMapping("/login.do")
	public String logins() {	//BasicDataSource만 사용
		try {
			Connection con = datasource.getConnection();
			String sql = "select count(*) as cnt from air_reserve";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String result = rs.getString("cnt");
			System.out.println(result);
			rs.close();
			ps.close();
			con.close();
			
		}catch(Exception e) {
			System.out.println("Database Connect Error");
		}
		return "/WEB-INF/jsp/login";
	}
	
	
}
