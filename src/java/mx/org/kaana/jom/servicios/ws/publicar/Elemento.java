package mx.org.kaana.jom.servicios.ws.publicar;

import java.io.Serializable;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import mx.org.kaana.libs.formato.Error;
import mx.org.kaana.libs.json.Decoder;
import mx.org.kaana.mantic.ws.publicar.beans.Respuesta;

@Path("/Elemento")
public class Elemento implements Serializable {
  
  private static final long serialVersionUID = -7067112384914290020L;

  @GET //Conexion
  @Path("/carbono") 
  @Produces("application/json;charset=ISO-8859-1")
  public String carbono() throws Exception{
    String regresar            = null;
    try {   
			regresar= Decoder.toJson(new Respuesta("01", "Correcto"));	
    } // try
    catch (Exception e) {
      Error.mensaje(e);      
    } // catch
    return regresar;
  } // carbono
	
	@GET //Login
  @Path("/hidrogeno/{radioAtomico}/{electrones}/{densidad}")
  @Produces("application/json;charset=ISO-8859-1")
  public String hidrogeno(@PathParam("radioAtomico") String radioAtomico, @PathParam("electrones") String electrones, @PathParam("densidad") String densidad) throws Exception{
    String regresar            = null;
    try {     
			regresar= "{\"codigo\":\"01\",\"mensaje\":\"Longin correcto\",\"idUsuario\":\"1\"}";
    } // try
    catch (Exception e) {
      Error.mensaje(e);      
    } // catch
    return regresar;
  } // hidrogeno
	
	@GET //Registro inicial
  @Path("/litio/{radioAtomico}/{electrones}/{densidad}/{masa}/{electronegatividad}/{isotopos}/{protones}/{numeroAtommico}")
  @Produces("application/json;charset=ISO-8859-1")
  public String litio(@PathParam("radioAtomico") String radioAtomico, @PathParam("electrones") String electrones, @PathParam("densidad") String densidad,@PathParam("masa") String masa, @PathParam("electronegatividad") String electronegatividad, @PathParam("isotopos") String isotopos, @PathParam("protones") String protones, @PathParam("numeroAtommico") String numeroAtommico) throws Exception{
   //String nombre, String apeidoPat, String apeidoMat, Integer telCelular, Integer telCasa, String correo, String contrasenia , String idDispositivo
		String regresar            = null;
    try {     
			regresar= "{codigo:\"01\",\"mensaje\":\"Registro exitoso\",\"usuario\":[\"idUsuario\":\"1\",\"nombre\":\"Arturo\",\"apePaterno\":\"Osnaya\",\"apeMaterno\":\"Razo\",\"telCelular\":\"4490000000\",\"telCasa\":\"4499105000\",\"correo\":\"correo\"],\"vivienda\":[{\"idFraccionamiento\":\"1,\"nombreFraccionamiento\":\"Chapultepec\",\"idVivienda\":\"1\",\"calle\":\"Niños heroes\",\"numExterior\":\"6\",\"numInterior\":\"1\"}]}";
    } // try
    catch (Exception e) {
      Error.mensaje(e);      
    } // catch
    return regresar;
  } // litio
	
	@GET //Registro automatico
  @Path("/berilio/{radioAtomico}/{electrones}/{densidad}")
  @Produces("application/json;charset=ISO-8859-1")
  public String berilio(@PathParam("radioAtomico") String radioAtomico, @PathParam("electrones") String electrones, @PathParam("densidad") String densidad) throws Exception{
    String regresar            = null;
    try {     
			regresar= "{codigo:\"01\",\"mensaje\":\"Registro exitoso\",\"usuario\":[\"idUsuario\":\"1\",\"nombre\":\"Arturo\",\"apePaterno\":\"Osnaya\",\"apeMaterno\":\"Razo\",\"telCelular\":\"4490000000\",\"telCasa\":\"4499105000\",\"correo\":\"correo\"],\"vivienda\":[{\"idFraccionamiento\":\"1,\"nombreFraccionamiento\":\"Chapultepec\",\"idVivienda\":\"1\",\"calle\":\"Niños heroes\",\"numExterior\":\"6\",\"numInterior\":\"1\"}]}";
    } // try
    catch (Exception e) {
      Error.mensaje(e);      
    } // catch
    return regresar;
  } // berilio
}
