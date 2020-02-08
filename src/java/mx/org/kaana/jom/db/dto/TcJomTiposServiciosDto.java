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
@Table(name="tc_jom_tipos_servicios")
public class TcJomTiposServiciosDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Column (name="descripcion")
  private String descripcion;
  @Column (name="nombre")
  private String nombre;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_tipo_servicio")
  private Long idTipoServicio;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomTiposServiciosDto() {
    this(new Long(-1L));
  }

  public TcJomTiposServiciosDto(Long key) {
    this(null, null, new Long(-1L));
    setKey(key);
  }

  public TcJomTiposServiciosDto(String descripcion, String nombre, Long idTipoServicio) {
    setDescripcion(descripcion);
    setNombre(nombre);
    setIdTipoServicio(idTipoServicio);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setIdTipoServicio(Long idTipoServicio) {
    this.idTipoServicio = idTipoServicio;
  }

  public Long getIdTipoServicio() {
    return idTipoServicio;
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
  	return getIdTipoServicio();
  }

  @Override
  public void setKey(Long key) {
  	this.idTipoServicio = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getDescripcion());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getNombre());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdTipoServicio());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getRegistro());
    regresar.append("]");
  	return regresar.toString();
  }

  @Override
  public Map toMap() {
    Map regresar = new HashMap();
		regresar.put("descripcion", getDescripcion());
		regresar.put("nombre", getNombre());
		regresar.put("idTipoServicio", getIdTipoServicio());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getDescripcion(), getNombre(), getIdTipoServicio(), getRegistro()
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
    regresar.append("idTipoServicio~");
    regresar.append(getIdTipoServicio());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdTipoServicio());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomTiposServiciosDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdTipoServicio()!= null && getIdTipoServicio()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomTiposServiciosDto other = (TcJomTiposServiciosDto) obj;
    if (getIdTipoServicio() != other.idTipoServicio && (getIdTipoServicio() == null || !getIdTipoServicio().equals(other.idTipoServicio))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdTipoServicio() != null ? getIdTipoServicio().hashCode() : 0);
    return hash;
  }

}


