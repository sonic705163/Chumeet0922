package com.wei.memjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@SuppressWarnings("serial")
@WebServlet("/SignupServletAndroid")
public class SignupServletAndroid extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String str = "";
		MemDAO memDAO = new MemDAO();
	
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		
		System.out.println(jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		
		String action = jsonObject.get("action").getAsString();
		
		
		
		if(action.equals("checkEmail")){
			
			String email = jsonObject.get("Email").getAsString();
			List<MemVO> memVOs = memDAO.getEmailCheck();
			
			for(MemVO mems : memVOs){
				
				String eMail = mems.getMemEmail();
				 
				if(eMail.equals(email)){
	        		  str = "repeat";
	        		  return;
				}else{
	        		  str = "usable";
	        		    
	        	} 
				 
			}
				
				 
		}
	        	  
		
	     writeText(response, gson.toJson(str));    
		
	}
		
		
		


			
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
//			MemDAO memDAO = new MemDAO();
//			
//			Gson gson = new Gson();
//			String memJson = gson.toJson(memDAO.findByMemEmail("member01@gmail"));
//			response.setContentType(CONTENT_TYPE);
//			PrintWriter out = response.getWriter();
//
//			out.println(memJson);
		
			doPost(request,response);
		
			
	}
			
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
		System.out.println(outText);
	}
	
}
