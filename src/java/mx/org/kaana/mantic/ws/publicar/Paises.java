package mx.org.kaana.mantic.ws.publicar;

import java.io.Serializable;
import mx.org.kaana.libs.json.Decoder;
import mx.org.kaana.mantic.enums.ERespuesta;
import mx.org.kaana.mantic.ws.publicar.beans.Respuesta;
import mx.org.kaana.mantic.ws.publicar.reglas.Manager;

public class Paises implements Serializable{
	
	private static final long serialVersionUID = -6756978815958154700L;
	
	public String argentina() throws Exception{
		String regresar= null;
		Manager manager= null;
		try {
			manager= new Manager();
			regresar= manager.verificaConexion();
		} // try
		catch (Exception e) {			
			regresar= Decoder.toJson(new Respuesta(ERespuesta.ERROR.getCodigo(), e.getMessage()));			
		} // catch		
		return regresar;
	} // afganistan
	
	public String mexico() throws Exception{
		String regresar= null;
		Manager manager= null;
		try {
			manager= new Manager();
			regresar= manager.ultimoRespaldo();
		} // try
		catch (Exception e) {			
			regresar= Decoder.toJson(new Respuesta(ERespuesta.ERROR.getCodigo(), e.getMessage()));			
		} // catch		
		return regresar;
	} // albania
	//Login
	public String alemania(String correo, String contrasenia, String idDispositivo) throws Exception{ 
		return "{\"codigo\":\"01\",\"mensaje\":\"Longin correcto\",\"idUsuario\":\"1\"}";
	} // alemania
	
	//Registro incial
	public String austria(String nombre, String apeidoPat, String apeidoMat, Integer telCelular, Integer telCasa, String correo, String contrasenia , String idDispositivo) throws Exception{ 
		return "{codigo:\"01\",\"mensaje\":\"Registro exitoso\",\"usuario\":[\"idUsuario\":\"1\",\"nombre\":\"Arturo\",\"apePaterno\":\"Osnaya\",\"apeMaterno\":\"Razo\",\"telCelular\":\"4490000000\",\"telCasa\":\"4499105000\",\"correo\":\"correo\"],\"vivienda\":[{\"idFraccionamiento\":\"1,\"nombreFraccionamiento\":\"Chapultepec\",\"idVivienda\":\"1\",\"calle\":\"Niños heroes\",\"numExterior\":\"6\",\"numInterior\":\"1\"}]}";
	} // austria
	
	//Regstro automatico
	public String belgica(String correo, String contrasenia, String idDispositivo) throws Exception{ 
		return "{codigo:\"01\",\"mensaje\":\"Registro exitoso\",\"usuario\":[\"idUsuario\":\"1\",\"nombre\":\"Arturo\",\"apePaterno\":\"Osnaya\",\"apeMaterno\":\"Razo\",\"telCelular\":\"4490000000\",\"telCasa\":\"4499105000\",\"correo\":\"correo\"],\"vivienda\":[{\"idFraccionamiento\":\"1,\"nombreFraccionamiento\":\"Chapultepec\",\"idVivienda\":\"1\",\"calle\":\"Niños heroes\",\"numExterior\":\"6\",\"numInterior\":\"1\"}]}";
	} // belgica
}
