package entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOC_GIA")
public class DocGia {

    @Id
    @Column(name = "MaDocGia", length = 20)
    private String maDocGia;

    @Column(name = "TenDocGia", nullable = false, length = 100)
    private String tenDocGia;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "DiaChi", length = 200)
    private String diaChi;

    public DocGia() {}

    public DocGia(String maDocGia, String tenDocGia, LocalDate ngaySinh,
                  String sdt, String email, String diaChi) {
        this.maDocGia = maDocGia;
        this.tenDocGia = tenDocGia;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaDocGia()         { return maDocGia; }
    public void setMaDocGia(String v)   { this.maDocGia = v; }
    public String getTenDocGia()        { return tenDocGia; }
    public void setTenDocGia(String v)  { this.tenDocGia = v; }
    public LocalDate getNgaySinh()      { return ngaySinh; }
    public void setNgaySinh(LocalDate v){ this.ngaySinh = v; }
    public String getSdt()              { return sdt; }
    public void setSdt(String v)        { this.sdt = v; }
    public String getEmail()            { return email; }
    public void setEmail(String v)      { this.email = v; }
    public String getDiaChi()           { return diaChi; }
    public void setDiaChi(String v)     { this.diaChi = v; }

    @Override
    public String toString() { return maDocGia + " - " + tenDocGia; }
}
