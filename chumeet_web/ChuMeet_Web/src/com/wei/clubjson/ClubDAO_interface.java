package com.wei.clubjson;

import java.util.List;

public interface ClubDAO_interface {
	public int insert(ClubVO clubVO, byte[] clubPhoto);
	public int update(ClubVO clubVO, byte[] clubPhoto);
	public int delete(Integer clubID);
	public ClubVO findByPrimaryKey(Integer clubID);
	public List<ClubVO> getAll();
	byte[] getImage(int id);
	
}
