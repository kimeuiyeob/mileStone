package com.milestone.app.community;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.milestone.app.Execute;
import com.milestone.app.Result;
import com.milestone.app.individualcommunity.dao.IndividualCommunityDAO;
import com.milestone.app.nurseryschoolcommunity.dao.NurserySchoolCommunityDAO;

public class CommunityListOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("커뮤니티 컨트롤러 들어옴");

		HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
		NurserySchoolCommunityDAO nurserySchoolCommunityDAO = new NurserySchoolCommunityDAO();
		IndividualCommunityDAO individualCommunityDAO = new IndividualCommunityDAO();
		Result result = new Result();
		String tempSchool = req.getParameter("pageSchool");
		String tempIndi = req.getParameter("pageIndi");

		int pageSchool = tempSchool == null ? 1 : Integer.parseInt(tempSchool);
		int pageIndi = tempIndi == null ? 1 : Integer.parseInt(tempIndi);
		
		int totalSchool = nurserySchoolCommunityDAO.managerSchoolCommunitySelectCount();
		int totalIndi = individualCommunityDAO.managerIndiCommunitySelectCount();
		
//			한 페이지에 출력되는 게시글의 개수
		int rowCount = 6;
//			한 페이지에서 나오는 페이지 버튼의 개수
		int pageCount = 5;
		
		int startRowSchool = (pageSchool - 1) * rowCount;
		int startRowIndi = (pageIndi - 1) * rowCount;

		int endPageSchool = (int) (Math.ceil(pageSchool / (double) pageCount) * pageCount);
		int endPageIndi = (int) (Math.ceil(pageIndi / (double) pageCount) * pageCount);
		
		int startPageSchool = endPageSchool - (pageCount - 1);
		int startPageIndi = endPageIndi - (pageCount - 1);
		
		int realEndPageSchool = (int) Math.ceil(totalSchool / (double) pageCount);
		int realEndPageIndi = (int) Math.ceil(totalIndi / (double) pageCount);

		boolean prevSchool = startPageSchool > 1;
		boolean prevIndi = startPageIndi > 1;
		
		endPageSchool = endPageSchool > realEndPageSchool ? realEndPageSchool : endPageSchool;
		endPageIndi = endPageIndi > realEndPageIndi ? realEndPageIndi : endPageIndi;
		
		boolean nextSchool = endPageSchool != realEndPageSchool;
		boolean nextIndi = endPageIndi != realEndPageIndi;

		pageMap.put("startRowSchool", startRowSchool);
		pageMap.put("startRowIndi", startRowIndi);
		
		pageMap.put("rowCount", rowCount);

		req.setAttribute("individual", individualCommunityDAO.managerIndiCommunityList(pageMap));
		req.setAttribute("nursery", nurserySchoolCommunityDAO.managerSchoolCommunityList(pageMap));
		
		req.setAttribute("totalSchool", totalSchool);
		req.setAttribute("totalIndi", totalSchool);
		
		req.setAttribute("pageSchool", pageSchool);
		req.setAttribute("pageIndi", pageIndi);
		
		req.setAttribute("startPageSchool", startPageSchool);
		req.setAttribute("startPageIndi", startPageIndi);
		
		req.setAttribute("endPageSchool", endPageSchool);
		req.setAttribute("endPageIndi", endPageIndi);
		
		req.setAttribute("prevSchool", prevSchool);
		req.setAttribute("prevIndi", prevIndi);
		
		req.setAttribute("nextSchool", nextSchool);
		req.setAttribute("nextIndi", nextIndi);

		System.out.println(individualCommunityDAO.managerIndiCommunityList(pageMap));
		System.out.println(nurserySchoolCommunityDAO.managerSchoolCommunityList(pageMap));

		result.setPath("/app/manager/milestoneManagerCommunity.jsp");

		return result;
	}

}
