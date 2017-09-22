package com.ad.model;

import java.sql.Timestamp;
import java.util.List;

public class AdService {
	private AdDAO_interface dao;
	
	
	public AdService(){
		dao = new AdJNDIDAO();
	}
	
	public AdVO addAd(Integer adminID, String adName, byte[] adImg, String adContent, String adLink,
			Timestamp adDate) {
		
		AdVO adVO = new AdVO();
		
		adVO.setAdminID(adminID);
		adVO.setAdName(adName);
		adVO.setAdImg(adImg);
		adVO.setAdContent(adContent);	
		adVO.setAdLink(adLink);
		adVO.setAdDate(adDate);
		dao.insert(adVO);
		
		return adVO;
	}
	
	public AdVO updateAd(Integer adminID, String adName, byte[] adImg, String adContent, String adLink,
			Timestamp adDate, Integer adID) {
		
		AdVO adVO = new AdVO();
		
		adVO.setAdminID(adminID);
		adVO.setAdName(adName);
		adVO.setAdImg(adImg);
		adVO.setAdContent(adContent);	
		adVO.setAdLink(adLink);
		adVO.setAdDate(adDate);
		adVO.setAdID(adID);
		dao.update(adVO);
		
		return adVO;
	}
	
	public void deleteAd(Integer adID){
		dao.delete(adID);
	}
	
	public AdVO getOneAd(Integer adID){
		return dao.findByPrimaryKey(adID);
	}
	
	public List<AdVO> getAll(){
		return dao.getAll();
	}
	
}
