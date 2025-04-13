package De2;

public class SinhVien {
	private int MaSV;
	private String HoTen;
	private float DTB;
	
	private Lop lop;
	
	public Lop getLop() {
		return lop;
	}
	public void setLop(Lop lop) {
		this.lop = lop;
	}
	public SinhVien(int maSV, String hoTen, float dTB) {
		super();
		MaSV = maSV;
		HoTen = hoTen;
		DTB = dTB;
	}
	public SinhVien() {
		super();
	}
	public int getMaSV() {
		return MaSV;
	}
	public void setMaSV(int maSV) {
		MaSV = maSV;
	}
	public String getHoTen() {
		return HoTen;
	}
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	public float getDTB() {
		return DTB;
	}
	public void setDTB(float dTB) {
		DTB = dTB;
	}
	@Override
	public String toString() {
		return this.getHoTen();
	}
}
