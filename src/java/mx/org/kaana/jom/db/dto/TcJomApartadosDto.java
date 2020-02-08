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
@Table(name="tc_jom_apartados")
public class TcJomApartadosDto implements IBaseDto, Serializable {
		
  private static final long serialVersionUID=1L;
  @Column (name="id_cliente")
  private Long idCliente;
  @Column (name="id_usuario")
  private Long idUsuario;
  @Column (name="apartado_inicio")
  private Timestamp apartadoInicio;
  @Column (name="id_apartado_estatus")
  private Long idApartadoEstatus;
  @Column (name="observaciones")
  private String observaciones;
  @Column (name="apartado_fin")
  private Timestamp apartadoFin;
  @Column (name="id_articulo")
  private Long idArticulo;
  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column (name="id_apartado")
  private Long idApartado;
  @Column (name="registro")
  private Timestamp registro;

  public TcJomApartadosDto() {
    this(new Long(-1L));
  }

  public TcJomApartadosDto(Long key) {
    this(null, null, new Timestamp(Calendar.getInstance().getTimeInMillis()), null, null, new Timestamp(Calendar.getInstance().getTimeInMillis()), null, new Long(-1L));
    setKey(key);
  }

  public TcJomApartadosDto(Long idCliente, Long idUsuario, Timestamp apartadoInicio, Long idApartadoEstatus, String observaciones, Timestamp apartadoFin, Long idArticulo, Long idApartado) {
    setIdCliente(idCliente);
    setIdUsuario(idUsuario);
    setApartadoInicio(apartadoInicio);
    setIdApartadoEstatus(idApartadoEstatus);
    setObservaciones(observaciones);
    setApartadoFin(apartadoFin);
    setIdArticulo(idArticulo);
    setIdApartado(idApartado);
    setRegistro(new Timestamp(Calendar.getInstance().getTimeInMillis()));
  }
	
  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public Long getIdCliente() {
    return idCliente;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setApartadoInicio(Timestamp apartadoInicio) {
    this.apartadoInicio = apartadoInicio;
  }

  public Timestamp getApartadoInicio() {
    return apartadoInicio;
  }

  public void setIdApartadoEstatus(Long idApartadoEstatus) {
    this.idApartadoEstatus = idApartadoEstatus;
  }

  public Long getIdApartadoEstatus() {
    return idApartadoEstatus;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setApartadoFin(Timestamp apartadoFin) {
    this.apartadoFin = apartadoFin;
  }

  public Timestamp getApartadoFin() {
    return apartadoFin;
  }

  public void setIdArticulo(Long idArticulo) {
    this.idArticulo = idArticulo;
  }

  public Long getIdArticulo() {
    return idArticulo;
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
  	return getIdApartado();
  }

  @Override
  public void setKey(Long key) {
  	this.idApartado = key;
  }

  @Override
  public String toString() {
    StringBuilder regresar= new StringBuilder();
    regresar.append("[");
		regresar.append(getIdCliente());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdUsuario());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getApartadoInicio());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdApartadoEstatus());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getObservaciones());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getApartadoFin());
		regresar.append(Constantes.SEPARADOR);
		regresar.append(getIdArticulo());
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
		regresar.put("idCliente", getIdCliente());
		regresar.put("idUsuario", getIdUsuario());
		regresar.put("apartadoInicio", getApartadoInicio());
		regresar.put("idApartadoEstatus", getIdApartadoEstatus());
		regresar.put("observaciones", getObservaciones());
		regresar.put("apartadoFin", getApartadoFin());
		regresar.put("idArticulo", getIdArticulo());
		regresar.put("idApartado", getIdApartado());
		regresar.put("registro", getRegistro());
  	return regresar;
  }

  @Override
  public Object[] toArray() {
    Object[] regresar = new Object[]{
    getIdCliente(), getIdUsuario(), getApartadoInicio(), getIdApartadoEstatus(), getObservaciones(), getApartadoFin(), getIdArticulo(), getIdApartado(), getRegistro()
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
    regresar.append("idApartado~");
    regresar.append(getIdApartado());
    regresar.append("|");
    return regresar.toString();
  }

  @Override
  public String toKeys() {
    StringBuilder regresar= new StringBuilder();
    regresar.append(getIdApartado());
    return regresar.toString();
  }

  @Override
  public Class toHbmClass() {
    return TcJomApartadosDto.class;
  }

  @Override
  public boolean isValid() {
  	return getIdApartado()!= null && getIdApartado()!=-1L;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TcJomApartadosDto other = (TcJomApartadosDto) obj;
    if (getIdApartado() != other.idApartado && (getIdApartado() == null || !getIdApartado().equals(other.idApartado))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (getIdApartado() != null ? getIdApartado().hashCode() : 0);
    return hash;
  }

}


