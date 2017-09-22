package com.wei.memjson;

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
@WebServlet("/MemServletAndroid")
public class MemServletAndroid extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	

		
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
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
		

		if(action.equals("getAll")){
			List<MemVO> mems = memDAO.getAll();
			writeText(response, gson.toJson(mems));
		}else if(action.equals("getImage")){
			
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = memDAO.getImage(id);
			if (image != null) {
				image = com.wei.actjson.ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			OutputStream os = response.getOutputStream();
			os.write(image);
		}else if(action.equals("Insert") || action.equals("Update")) {
			String memJson = jsonObject.get("mem").getAsString();
			MemVO memVO = gson.fromJson(memJson, MemVO.class);
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("Insert")) {
				count = memDAO.insert(memVO, image);
			} else if (action.equals("Update")) {
				count = memDAO.update(memVO, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("Delete")) {
			String memJson = jsonObject.get("mem").getAsString();
			MemVO memVO = gson.fromJson(memJson, MemVO.class);
			int count = memDAO.delete(memVO.getMemID());
			writeText(response, String.valueOf(count));
		} else if (action.equals("findById")) {
			int id = jsonObject.get("id").getAsInt();
			MemVO memVO = memDAO.findByPrimaryKey(id);
			writeText(response, gson.toJson(memVO));
		} else {
			writeText(response, "");
		}
	}
		
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			MemDAO memDAO = new MemDAO();
			
			Gson gson = new Gson();
			String memJson = gson.toJson(memDAO.getAll());
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			out.println(memJson);
		
			
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
