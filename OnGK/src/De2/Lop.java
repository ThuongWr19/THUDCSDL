package De2;

import java.util.List;

public class Lop {
	private int maLop;
	private String tenLop;
	private List<SinhVien> listSinhVien;
	
	
	
	public List<SinhVien> getListSinhVien() {
		return listSinhVien;
	}
	public void setListSinhVien(List<SinhVien> listSinhVien) {
		this.listSinhVien = listSinhVien;
	}
	public Lop(int maLop, String tenLop) {
		super();
		this.maLop = maLop;
		this.tenLop = tenLop;
	}
	public Lop() {
		super();
	}
	public int getMaLop() {
		return maLop;
	}
	public void setMaLop(int maLop) {
		this.maLop = maLop;
	}
	public String getTenLop() {
		return tenLop;
	}
	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	@Override
	public String toString() {
		return this.getTenLop();
	}
	
	
}
