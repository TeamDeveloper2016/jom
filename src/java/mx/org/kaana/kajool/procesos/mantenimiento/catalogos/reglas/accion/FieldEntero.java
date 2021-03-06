package mx.org.kaana.kajool.procesos.mantenimiento.catalogos.reglas.accion;

import java.util.List;
import javax.faces.component.UIComponentBase;
import javax.faces.convert.LongConverter;
import mx.org.kaana.libs.formato.Cadena;
import mx.org.kaana.kajool.procesos.mantenimiento.catalogos.reglas.criterios.ICriterio;

/**
 *@company KAANA
 *@project KAJOOL (Control system polls)
 *@date 22/09/2015
 *@time 03:47:27 PM
 *@author Team Developer 2016 <team.developer@kaana.org.mx>
 */

public class FieldEntero extends mx.org.kaana.kajool.procesos.mantenimiento.catalogos.reglas.criterios.FieldEntero implements ICriterio, IValue {

  private static final long serialVersionUID = 1590645133570505442L;

  public FieldEntero(String nombre, String titulo, Long size) {
    super(nombre, titulo, size, new Long(0));
  }

  @Override
  public List<UIComponentBase> create(String value) {
    return super.create(value);
  }

  @Override
  public Object getValue(){
    if(Cadena.isVacio(super.getValue()))
      return new Integer(0);
    else
      return super.getValue();
  }

}
