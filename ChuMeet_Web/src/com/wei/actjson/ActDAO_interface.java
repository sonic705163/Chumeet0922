package com.wei.actjson;

import java.sql.Timestamp;
import java.util.*;


public interface ActDAO_interface {
    public int insert(ActVO actVO, byte[] actImg);
    public int update(ActVO actVO, byte[] actImg);
    public ActVO findByPrimaryKey(Integer actID);
    public List<ActVO> getAll();
    public List<ActVO> getActByCat(Integer POIID);
    public List<ActVO> getActByDate(Timestamp actDate);
    public List<ActVO> getActByWks(Timestamp actDate);
//    public List<ActVO> getActByDist(Integer Dist);
    public List<ActVO> getActByMemIDJoin(Integer memID);
    public List<ActVO> getActByMemIDCreate(Integer memID);
    public List<ActVO> getActByMemIDFriend(Integer memID);
    public List<ActVO> getActByMemIDTrack(Integer memID);
    public List<ActVO> getActByClub(Integer clubID);
    byte[] getImage(int id);
}
