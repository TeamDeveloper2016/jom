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
@Table(name="tc_jom_incidentes")
public class TcJomIncidentesDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Column (name="consecutivo")
  private String consecutivo;
  @Column (name="descripcion")
  private String descripcion;
  @Column (name="longitud")
  private String longitud;
  @Column (name="latitud")
  private String latitud;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_incidente")
  private Long idIncidente;
  @Column (name="id_cliente")
  private Long idCliente;
  @Column (name="id_incidente_estatus")
  private Long idIncidenteEstatus;
  @Column (name="orden")
  private Long orden;
  @Column (name="id_tipo_servicio")
  private Long idTipoServicio;
  @Column (name="ejercicio")
  private Long ejercicio;
  @Column (name="capturo")
  private Timestamp capturo;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomIncidentesDto() {
    this(new Long(-1L));
  }

  public TcJomIncidentesDto(Long key) {
    this(null, null, null, null, new Long(-1L), null, null, null, null, null, new Timestamp(Calendar.getInstance().getTimeInMillis()));
    setKey(key);
  }

  public TcJomIncidentesDto(String consecutivo, String descripcion, String longitud, String latitud, Long idIncidente, Long idCliente, Long idIncidenteEstatus, Long orden, Long idTipoServicio, Long ejercicio, Timestamp capturo) {
    setConsecutivo(consecutivo);
    setDescripcion(descripcion);
    setLongitud(longitud);
    setLatitud(latitud);
    setIdIncidente(idIncidente);
    setIdCliente(idCliente);
    setIdIncidenteEstatus(idIncidenteEstatus);
    setOrden(orden);
    setIdTipoServicio(idTipoServicio);
    setEjercicio(ejercicio);
    setCapturo(capturo);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

  public String getConsecutivo() {
    return consecutivo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setLongitud(String longitud) {
    this.longitud = longitud;
  }

  public String getLongitud() {
    return longitud;
  }

  public void setLatitud(String latitud) {
    this.latitud = latitud;
  }

  public String getLatitud() {
    return latitud;
  }

  public void setIdIncidente(Long idIncidente) {
    this.idIncidente = idIncidente;
  }

  public Long getIdIncidente() {
    return idIncidente;
  }

  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public Long getIdCliente() {
    return idCliente;
  }

  public void setIdIncidenteEstatus(Long idIncidenteEstatus) {
    this.idIncidenteEstatus = idIncidenteEstatus;
  }

  public Long getIdIncidenteEstatus() {
    return idIncidenteEstatus;
  }

  public void setOrden(Long orden) {
    this.orden = orden;
  }

  public Long getOrden() {
    return orden;
  }

  public void setIdTipoServicio(Long idTipoServicio) {
    this.idTipoServicio = idTipoServicio;
  }

  public Long getIdTipoServicio() {
    return idTipoServicio;
  }

  public void setEjercicio(Long ejercicio) {
    this.ejercicio = ejercicio;
  }

  public Long getEjercicio() {
    return ejercicio;
  }

  public void setCapturo(Timestamp capturo) {
    this.capturo = capturo;
  }

  public Timestamp getCapturo() {
    return capturo;
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
  	return getIdIncidente();
  }

  @Override
  public void setKey(Long key) {
  	this.idIncidente = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getConsecutivo());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getDescripcion());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getLongitud());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getLatitud());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdIncidente());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdCliente());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdIncidenteEstatus());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getOrden());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdTipoServicio());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getEjercicio());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getCapturo());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getRegistro());
    regresar.append("]");
  	return regresar.toString();
  }

  @Override
  public Map toMap() {
    Map regresar = new HashMap();
		regresar.put("consecutivo", getConsecutivo());
		regresar.put("descripcion", getDescripcion());
		regresar.put("longitud", getLongitud());
		regresar.put("latitud", getLatitud());
		regresar.put("idIncidente", getIdIncidente());
		regresar.put("idCliente", getIdCliente());
		regresar.put("idIncidenteEstatus", getIdIncidenteEstatus());
		regresar.put("orden", getOrden());
		regresar.put("idTipoServicio", getIdTipoServicio());
		regresar.put("ejercicio", getEjercicio());
		regresar.put("capturo", getCapturo());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getConsecutivo(), getDescripcion(), getLongitud(), getLatitud(), getIdIncidente(), getIdCliente(), getIdIncidenteEstatus(), getOrden(), getIdTipoServicio(), getEjercicio(), getCapturo(), getRegistro()
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
    regresar.append("idIncidente~");
    regresar.append(getIdIncidente());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdIncidente());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomIncidentesDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdIncidente()!= null && getIdIncidente()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomIncidentesDto other = (TcJomIncidentesDto) obj;
    if (getIdIncidente() != other.idIncidente && (getIdIncidente() == null || !getIdIncidente().equals(other.idIncidente))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdIncidente() != null ? getIdIncidente().hashCode() : 0);
    return hash;
  }

}


