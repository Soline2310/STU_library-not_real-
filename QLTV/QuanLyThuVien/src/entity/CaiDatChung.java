package entity;

import javax.persistence.*;

@Entity
@Table(name = "CAI_DAT_CHUNG")
public class CaiDatChung {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TenThuVien", length = 200)
    private String tenThuVien;

    @Column(name = "DiaChi", length = 200)
    private String diaChi;

    @Column(name = "NoiQuy", length = 500)
    private String noiQuy;

    @Column(name = "MaThue", length = 50)
    private String maThue;

    public CaiDatChung() {}

    public CaiDatChung(Integer id, String tenThuVien, String diaChi, String noiQuy, String maThue) {
        this.id = id;
        this.tenThuVien = tenThuVien;
        this.diaChi = diaChi;
        this.noiQuy = noiQuy;
        this.maThue = maThue;
    }

    public Integer getId()               { return id; }
    public void    setId(Integer v)      { this.id = v; }
    public String  getTenThuVien()       { return tenThuVien; }
    public void    setTenThuVien(String v){ this.tenThuVien = v; }
    public String  getDiaChi()           { return diaChi; }
    public void    setDiaChi(String v)   { this.diaChi = v; }
    public String  getNoiQuy()           { return noiQuy; }
    public void    setNoiQuy(String v)   { this.noiQuy = v; }
    public String  getMaThue()           { return maThue; }
    public void    setMaThue(String v)   { this.maThue = v; }
}
