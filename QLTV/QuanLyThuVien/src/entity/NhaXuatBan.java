package entity;

import javax.persistence.*;

@Entity
@Table(name = "NHA_XUAT_BAN")
public class NhaXuatBan {

    @Id
    @Column(name = "MaNXB", length = 20)
    private String maNXB;

    @Column(name = "TenNXB", nullable = false, length = 100)
    private String tenNXB;

    @Column(name = "DiaChi", length = 200)
    private String diaChi;

    @Column(name = "SDT", length = 15)
    private String sDT;

    public NhaXuatBan() {}

    public NhaXuatBan(String maNXB, String tenNXB, String diaChi, String sDT) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sDT = sDT;
    }

    public String getMaNXB()           { return maNXB; }
    public void   setMaNXB(String v)   { this.maNXB = v; }
    public String getTenNXB()          { return tenNXB; }
    public void   setTenNXB(String v)  { this.tenNXB = v; }
    public String getDiaChi()          { return diaChi; }
    public void   setDiaChi(String v)  { this.diaChi = v; }
    public String getSDT()             { return sDT; }
    public void   setSDT(String v)     { this.sDT = v; }
}
