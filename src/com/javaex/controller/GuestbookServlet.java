package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;



@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("servlet 진입");
		String actionName = request.getParameter("a");

		if ("list".equals(actionName)) {
			System.out.println("list 진입");
			GuestbookDao dao = new GuestbookDao();
			
			List<GuestbookVo> list = dao.getList();
			request.setAttribute("list", list);
			System.out.println(list.toString());
		/*	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			rd.forward(request, response);*/
			
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
			
		} else if ("add".equals(actionName)) {
			System.out.println("add 진입");
			request.setCharacterEncoding("UTF-8");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			System.out.println(vo.toString());

			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);

			/*response.sendRedirect("gb?a=list");*/
			
			WebUtil.redirect(request, response, "/guestbook2/gb?a=list");
			
		} else if ("delete".equals(actionName)) {
			System.out.println("delete 진입");
			request.setCharacterEncoding("UTF-8");

			String password = request.getParameter("password");
						
			GuestbookDao dao = new GuestbookDao();
			int no = Integer.parseInt(request.getParameter("no").trim());
			
			GuestbookVo vo = dao.select(no);
			if(vo.getPassword().equals(password)) {
				dao.delete(no);
			}
			
			/*response.sendRedirect("gb?a=list");*/
			WebUtil.redirect(request, response, "/guestbook2/gb?a=list");
			
		} else if ("deleteform".equals(actionName)) {
			System.out.println("deleteform 진입");
			/*RequestDispatcher rd = request.getRequestDispatcher("deleteform.jsp");
			rd.forward(request, response);*/
			
			WebUtil.forward(request, response, "/WEB-INF/deleteform.jsp");
			
		} else {
			
			System.out.println("잘못된 a값 처리 입니다.");
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
