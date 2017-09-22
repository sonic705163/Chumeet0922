package com.wei.actjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



@SuppressWarnings("serial")
@WebServlet("/ActServletAndroid")
public class ActServletAndroid extends HttpServlet {

    
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ActDAO actDAO = new ActDAO();
		
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
		

		if(action.equals("getAll")){
			List<ActVO> spots = actDAO.getAll();
			writeText(response, gson.toJson(spots));
		}else if(action.equals("getImage")){
			
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = actDAO.getImage(id);
			if (image != null) {
				image = com.wei.actjson.ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			OutputStream os = response.getOutputStream();
			os.write(image);
		}else if(action.equals("Insert") || action.equals("Update")) {
			String actJson = jsonObject.get("act").getAsString();
			ActVO actVO = gson.fromJson(actJson, ActVO.class);
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("Insert")) {
				count = actDAO.insert(actVO, image);
			} else if (action.equals("Update")) {
				count = actDAO.update(actVO, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("findById")) {
			int id = jsonObject.get("id").getAsInt();
			ActVO actVO = actDAO.findByPrimaryKey(id);
			writeText(response, gson.toJson(actVO));
		} else {
			writeText(response, "");
		}
	}
		
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			ActDAO actDAO = new ActDAO();
			
			
			Gson gson = new Gson();
			String actJson = gson.toJson(actDAO.getAll());
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			out.println(actJson);
		
			
	}
			
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		 System.out.println("outText: " + outText);
		out.print(outText);
		System.out.println(outText);
	}

}
