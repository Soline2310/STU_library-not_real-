package entity;

import javax.persistence.*;

@Entity
@Table(name = "THU_THU")
public class ThuThu {

    @Id
    @Column(name = "MaThuThu", length = 20)
    private String maThuThu;

    @Column(name = "TenThuThu", nullable = false, length = 100)
    private String tenThuThu;

    @Column(name = "CaTruc", length = 50)
    private String caTruc;

    @Column(name = "SDT", length = 15)
    private String sDT;

    @Column(name = "TaiKhoan", length = 50)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 50)
    private String matKhau;

    @Column(name = "VaiTro", length = 50)
    private String vaiTro = "Thu thu";

    public ThuThu() {}

    public ThuThu(String maThuThu, String tenThuThu, String caTruc, String sDT,
                  String taiKhoan, String matKhau, String vaiTro) {
        this.maThuThu = maThuThu;
        this.tenThuThu = tenThuThu;
        this.caTruc = caTruc;
        this.sDT = sDT;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getMaThuThu()            { return maThuThu; }
    public void   setMaThuThu(String v)    { this.maThuThu = v; }
    public String getTenThuThu()           { return tenThuThu; }
    public void   setTenThuThu(String v)   { this.tenThuThu = v; }
    public String getCaTruc()              { return caTruc; }
    public void   setCaTruc(String v)      { this.caTruc = v; }
    public String getSDT()                 { return sDT; }
    public void   setSDT(String v)         { this.sDT = v; }
    public String getTaiKhoan()            { return taiKhoan; }
    public void   setTaiKhoan(String v)    { this.taiKhoan = v; }
    public String getMatKhau()             { return matKhau; }
    public void   setMatKhau(String v)     { this.matKhau = v; }
    public String getVaiTro()              { return vaiTro; }
    public void   setVaiTro(String v)      { this.vaiTro = v; }
}
