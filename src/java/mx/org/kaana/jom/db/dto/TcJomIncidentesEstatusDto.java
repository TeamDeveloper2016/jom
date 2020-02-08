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
@Table(name="tc_jom_incidentes_estatus")
public class TcJomIncidentesEstatusDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Column (name="descripcion")
  private String descripcion;
  @Column (name="id_justificacion")
  private Long idJustificacion;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_incidente_estatus")
  private Long idIncidenteEstatus;
  @Column (name="estatus_asociados")
  private String estatusAsociados;
  @Column (name="nombres")
  private String nombres;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomIncidentesEstatusDto() {
    this(new Long(-1L));
  }

  public TcJomIncidentesEstatusDto(Long key) {
    this(null, null, new Long(-1L), null, null);
    setKey(key);
  }

  public TcJomIncidentesEstatusDto(String descripcion, Long idJustificacion, Long idIncidenteEstatus, String estatusAsociados, String nombres) {
    setDescripcion(descripcion);
    setIdJustificacion(idJustificacion);
    setIdIncidenteEstatus(idIncidenteEstatus);
    setEstatusAsociados(estatusAsociados);
    setNombres(nombres);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setIdJustificacion(Long idJustificacion) {
    this.idJustificacion = idJustificacion;
  }

  public Long getIdJustificacion() {
    return idJustificacion;
  }

  public void setIdIncidenteEstatus(Long idIncidenteEstatus) {
    this.idIncidenteEstatus = idIncidenteEstatus;
  }

  public Long getIdIncidenteEstatus() {
    return idIncidenteEstatus;
  }

  public void setEstatusAsociados(String estatusAsociados) {
    this.estatusAsociados = estatusAsociados;
  }

  public String getEstatusAsociados() {
    return estatusAsociados;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getNombres() {
    return nombres;
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
  	return getIdIncidenteEstatus();
  }

  @Override
  public void setKey(Long key) {
  	this.idIncidenteEstatus = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getDescripcion());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdJustificacion());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdIncidenteEstatus());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getEstatusAsociados());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getNombres());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getRegistro());
    regresar.append("]");
  	return regresar.toString();
  }

  @Override
  public Map toMap() {
    Map regresar = new HashMap();
		regresar.put("descripcion", getDescripcion());
		regresar.put("idJustificacion", getIdJustificacion());
		regresar.put("idIncidenteEstatus", getIdIncidenteEstatus());
		regresar.put("estatusAsociados", getEstatusAsociados());
		regresar.put("nombres", getNombres());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getDescripcion(), getIdJustificacion(), getIdIncidenteEstatus(), getEstatusAsociados(), getNombres(), getRegistro()
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
    regresar.append("idIncidenteEstatus~");
    regresar.append(getIdIncidenteEstatus());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdIncidenteEstatus());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomIncidentesEstatusDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdIncidenteEstatus()!= null && getIdIncidenteEstatus()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomIncidentesEstatusDto other = (TcJomIncidentesEstatusDto) obj;
    if (getIdIncidenteEstatus() != other.idIncidenteEstatus && (getIdIncidenteEstatus() == null || !getIdIncidenteEstatus().equals(other.idIncidenteEstatus))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdIncidenteEstatus() != null ? getIdIncidenteEstatus().hashCode() : 0);
    return hash;
  }

}


