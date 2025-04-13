package De1;

public class Sach {
	private String maSach;
	private String tenSach;
	private float Gia;
	private LoaiSach ls;
	
	public LoaiSach getLs() {
		return ls;
	}
	public void setLs(LoaiSach ls) {
		this.ls = ls;
	}
	public Sach(String maSach, String tenSach, float gia) {
		super();
		this.maSach = maSach;
		this.tenSach = tenSach;
		Gia = gia;
	}
	public Sach() {
		super();
	}
	public String getMaSach() {
		return maSach;
	}
	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public float getGia() {
		return Gia;
	}
	public void setGia(float gia) {
		Gia = gia;
	}
	
}
