package com.wei.memjson;


import java.util.List;

public interface MemDAO_interface {
	//管理員用
    public int insert(MemVO memVO, byte[] image);
    public int update(MemVO memVO, byte[] image);
    public int delete(Integer memID);
    public MemVO findByPrimaryKey(Integer memID);
    //會員登入用
    public MemVO findByMemEmail(String memEmail);
    public MemVO findByMemPw(String memPw);
    //會員註冊用
//    public void registerMember(MemberVO memberVO);
//    public void updateInfo(MemberVO memberVO);
    
    
    public List<MemVO> getAll();
    //�f���}�ϲ�ԃ(���녢���͑BMap)(�؂� List)
//  public List<memberVO> getAll(Map<String, String[]> map); 
	byte[] getImage(int id);
	
}
