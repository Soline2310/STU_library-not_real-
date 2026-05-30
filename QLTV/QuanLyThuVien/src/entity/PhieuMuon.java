package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PHIEU_MUON")
public class PhieuMuon {

    @Id
    @Column(name = "MaPhieu", length = 20)
    private String maPhieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDocGia")
    private DocGia docGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSach")
    private Sach sach;

    @Column(name = "NgayMuon")
    private LocalDate ngayMuon;

    @Column(name = "HanTra")
    private LocalDate hanTra;

    @Column(name = "TrangThai", length = 50)
    private String trangThai = "Đang mượn";

    public PhieuMuon() {}

    public PhieuMuon(String maPhieu, DocGia docGia, Sach sach,
                     LocalDate ngayMuon, LocalDate hanTra, String trangThai) {
        this.maPhieu = maPhieu;
        this.docGia = docGia;
        this.sach = sach;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.trangThai = trangThai;
    }

    public String    getMaPhieu()              { return maPhieu; }
    public void      setMaPhieu(String v)      { this.maPhieu = v; }
    public DocGia    getDocGia()               { return docGia; }
    public void      setDocGia(DocGia v)       { this.docGia = v; }
    public Sach      getSach()                 { return sach; }
    public void      setSach(Sach v)           { this.sach = v; }
    public LocalDate getNgayMuon()             { return ngayMuon; }
    public void      setNgayMuon(LocalDate v)  { this.ngayMuon = v; }
    public LocalDate getHanTra()               { return hanTra; }
    public void      setHanTra(LocalDate v)    { this.hanTra = v; }
    public String    getTrangThai()            { return trangThai; }
    public void      setTrangThai(String v)    { this.trangThai = v; }

    /** Convenience getters for table display */
    public String getMaDocGia() { return docGia != null ? docGia.getMaDocGia() : ""; }
    public String getMaSach()   { return sach   != null ? sach.getMaSach()     : ""; }
}
