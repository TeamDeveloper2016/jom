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
@Table(name="tc_jom_apartados_bitacora")
public class TcJomApartadosBitacoraDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_apartado_bitacora")
  private Long idApartadoBitacora;
  @Column (name="justificacion")
  private String justificacion;
  @Column (name="id_usuario")
  private Long idUsuario;
  @Column (name="id_apartado_estatus")
  private Long idApartadoEstatus;
  @Column (name="id_apartado")
  private Long idApartado;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomApartadosBitacoraDto() {
    this(new Long(-1L));
  }

  public TcJomApartadosBitacoraDto(Long key) {
    this(new Long(-1L), null, null, null, null);
    setKey(key);
  }

  public TcJomApartadosBitacoraDto(Long idApartadoBitacora, String justificacion, Long idUsuario, Long idApartadoEstatus, Long idApartado) {
    setIdApartadoBitacora(idApartadoBitacora);
    setJustificacion(justificacion);
    setIdUsuario(idUsuario);
    setIdApartadoEstatus(idApartadoEstatus);
    setIdApartado(idApartado);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setIdApartadoBitacora(Long idApartadoBitacora) {
    this.idApartadoBitacora = idApartadoBitacora;
  }

  public Long getIdApartadoBitacora() {
    return idApartadoBitacora;
  }

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setIdApartadoEstatus(Long idApartadoEstatus) {
    this.idApartadoEstatus = idApartadoEstatus;
  }

  public Long getIdApartadoEstatus() {
    return idApartadoEstatus;
  }

  public void setIdApartado(Long idApartado) {
    this.idApartado = idApartado;
  }

  public Long getIdApartado() {
    return idApartado;
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
  	return getIdApartadoBitacora();
  }

  @Override
  public void setKey(Long key) {
  	this.idApartadoBitacora = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getIdApartadoBitacora());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getJustificacion());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdUsuario());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdApartadoEstatus());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdApartado());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getRegistro());
    regresar.append("]");
  	return regresar.toString();
  }

  @Override
  public Map toMap() {
    Map regresar = new HashMap();
		regresar.put("idApartadoBitacora", getIdApartadoBitacora());
		regresar.put("justificacion", getJustificacion());
		regresar.put("idUsuario", getIdUsuario());
		regresar.put("idApartadoEstatus", getIdApartadoEstatus());
		regresar.put("idApartado", getIdApartado());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getIdApartadoBitacora(), getJustificacion(), getIdUsuario(), getIdApartadoEstatus(), getIdApartado(), getRegistro()
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
    regresar.append("idApartadoBitacora~");
    regresar.append(getIdApartadoBitacora());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdApartadoBitacora());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomApartadosBitacoraDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdApartadoBitacora()!= null && getIdApartadoBitacora()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomApartadosBitacoraDto other = (TcJomApartadosBitacoraDto) obj;
    if (getIdApartadoBitacora() != other.idApartadoBitacora && (getIdApartadoBitacora() == null || !getIdApartadoBitacora().equals(other.idApartadoBitacora))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdApartadoBitacora() != null ? getIdApartadoBitacora().hashCode() : 0);
    return hash;
  }

}


