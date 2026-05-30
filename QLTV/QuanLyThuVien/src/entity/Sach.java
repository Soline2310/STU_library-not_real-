package entity;

import javax.persistence.*;

@Entity
@Table(name = "SACH")
public class Sach {

    @Id
    @Column(name = "MaSach", length = 20)
    private String maSach;

    @Column(name = "TenSach", nullable = false, length = 200)
    private String tenSach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTacGia")
    private TacGia tacGia;

    @Column(name = "TheLoai", length = 100)
    private String theLoai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNXB")
    private NhaXuatBan nhaXuatBan;

    @Column(name = "SoLuong")
    private Integer soLuong = 0;

    public Sach() {}

    public Sach(String maSach, String tenSach, TacGia tacGia, String theLoai,
                NhaXuatBan nhaXuatBan, Integer soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.nhaXuatBan = nhaXuatBan;
        this.soLuong = soLuong;
    }

    public String getMaSach()                  { return maSach; }
    public void   setMaSach(String v)          { this.maSach = v; }
    public String getTenSach()                 { return tenSach; }
    public void   setTenSach(String v)         { this.tenSach = v; }
    public TacGia getTacGia()                  { return tacGia; }
    public void   setTacGia(TacGia v)          { this.tacGia = v; }
    public String getTheLoai()                 { return theLoai; }
    public void   setTheLoai(String v)         { this.theLoai = v; }
    public NhaXuatBan getNhaXuatBan()          { return nhaXuatBan; }
    public void   setNhaXuatBan(NhaXuatBan v)  { this.nhaXuatBan = v; }
    public Integer getSoLuong()                { return soLuong; }
    public void   setSoLuong(Integer v)        { this.soLuong = v; }

    /** Convenience: returns author name or empty string if null */
    public String getTenTacGia() {
        return tacGia != null ? tacGia.getTenTacGia() : "";
    }

    /** Convenience: returns publisher name or empty string if null */
    public String getTenNXB() {
        return nhaXuatBan != null ? nhaXuatBan.getTenNXB() : "";
    }
}
