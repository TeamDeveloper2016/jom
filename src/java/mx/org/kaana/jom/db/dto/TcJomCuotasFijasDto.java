package mx.org.kaana.jom.db.dto;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Lob;
import javax.persistence.Table;
import mx.org.kaana.libs.Constantes;
import mx.org.kaana.libs.reflection.Methods;
import mx.org.kaana.kajool.db.comun.dto.IBaseDto;

/**
 *@company KAANA
 *@project KAJOOL (Control system polls)
 *@date 10/10/2016
 *@time 11:58:22 PM
 *@author Team Developer 2016 <team.developer@kaana.org.mx>
 */

@Entity
@Table(name="tc_jom_cuotas_fijas")
public class TcJomCuotasFijasDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_cuota_fija")
  private Long idCuotaFija;
  @Column (name="observaciones")
  private String observaciones;
  @Column (name="id_articulo")
  private Long idArticulo;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomCuotasFijasDto() {
    this(new Long(-1L));
  }

  public TcJomCuotasFijasDto(Long key) {
    this(new Long(-1L), null, null);
    setKey(key);
  }

  public TcJomCuotasFijasDto(Long idCuotaFija, String observaciones, Long idArticulo) {
    setIdCuotaFija(idCuotaFija);
    setObservaciones(observaciones);
    setIdArticulo(idArticulo);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setIdCuotaFija(Long idCuotaFija) {
    this.idCuotaFija = idCuotaFija;
  }

  public Long getIdCuotaFija() {
    return idCuotaFija;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setIdArticulo(Long idArticulo) {
    this.idArticulo = idArticulo;
  }

  public Long getIdArticulo() {
    return idArticulo;
  }

  public void setRegistro(Timestamp registro) {
    this.registro = registro;
  }

  public Timestamp getRegistro() {
    return registro;
  }

  @Transient
  @Override
  public Long getKey() {
  	return getIdCuotaFija();
  }

  @Override
  public void setKey(Long key) {
  	this.idCuotaFija = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getIdCuotaFija());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getObservaciones());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdArticulo());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getRegistro());
    regresar.append("]");
  	return regresar.toString();
  }

  @Override
  public Map toMap() {
    Map regresar = new HashMap();
		regresar.put("idCuotaFija", getIdCuotaFija());
		regresar.put("observaciones", getObservaciones());
		regresar.put("idArticulo", getIdArticulo());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getIdCuotaFija(), getObservaciones(), getIdArticulo(), getRegistro()
    };
    return regresar;
  }

  @Override
  public Object toValue(String name) {
    return Methods.getValue(this, name);
  }

  @Override
  public String toAllKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("|");
    regresar.append("idCuotaFija~");
    regresar.append(getIdCuotaFija());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdCuotaFija());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomCuotasFijasDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdCuotaFija()!= null && getIdCuotaFija()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomCuotasFijasDto other = (TcJomCuotasFijasDto) obj;
    if (getIdCuotaFija() != other.idCuotaFija && (getIdCuotaFija() == null || !getIdCuotaFija().equals(other.idCuotaFija))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdCuotaFija() != null ? getIdCuotaFija().hashCode() : 0);
    return hash;
  }

}


