package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PHIEU_NHAP")
public class PhieuNhap {

    @Id
    @Column(name = "MaPhieuNhap", length = 20)
    private String maPhieuNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSach")
    private Sach sach;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "NhaCungCap", length = 150)
    private String nhaCungCap;

    @Column(name = "NgayNhap")
    private LocalDate ngayNhap;

    @Column(name = "TinhTrang", length = 50)
    private String tinhTrang = "Đã nhận";

    public PhieuNhap() {}

    public PhieuNhap(String maPhieuNhap, Sach sach, Integer soLuong,
                     String nhaCungCap, LocalDate ngayNhap, String tinhTrang) {
        this.maPhieuNhap = maPhieuNhap;
        this.sach = sach;
        this.soLuong = soLuong;
        this.nhaCungCap = nhaCungCap;
        this.ngayNhap = ngayNhap;
        this.tinhTrang = tinhTrang;
    }

    public String    getMaPhieuNhap()              { return maPhieuNhap; }
    public void      setMaPhieuNhap(String v)      { this.maPhieuNhap = v; }
    public Sach      getSach()                     { return sach; }
    public void      setSach(Sach v)               { this.sach = v; }
    public Integer   getSoLuong()                  { return soLuong; }
    public void      setSoLuong(Integer v)         { this.soLuong = v; }
    public String    getNhaCungCap()               { return nhaCungCap; }
    public void      setNhaCungCap(String v)       { this.nhaCungCap = v; }
    public LocalDate getNgayNhap()                 { return ngayNhap; }
    public void      setNgayNhap(LocalDate v)      { this.ngayNhap = v; }
    public String    getTinhTrang()                { return tinhTrang; }
    public void      setTinhTrang(String v)        { this.tinhTrang = v; }

    /** Convenience getter for table display */
    public String getMaSach() { return sach != null ? sach.getMaSach() : ""; }
}
