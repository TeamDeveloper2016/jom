package mx.org.kaana.mantic.ventas.caja.reglas;

import java.util.HashMap;
import java.util.Map;
import mx.org.kaana.kajool.db.comun.hibernate.DaoFactory;
import mx.org.kaana.kajool.db.comun.sql.Entity;
import mx.org.kaana.kajool.procesos.acceso.beans.Sucursal;
import mx.org.kaana.libs.Constantes;
import mx.org.kaana.libs.formato.Fecha;
import mx.org.kaana.libs.formato.Numero;
import mx.org.kaana.libs.pagina.JsfBase;
import mx.org.kaana.libs.reflection.Methods;
import mx.org.kaana.mantic.compras.ordenes.beans.Articulo;
import mx.org.kaana.mantic.enums.ETiposContactos;
import mx.org.kaana.mantic.ventas.beans.TicketVenta;
import mx.org.kaana.mantic.ventas.caja.beans.Pago;
import mx.org.kaana.mantic.ventas.reglas.AdminTickets;

public class CreateTicket {

	private AdminTickets ticket;
	protected Pago pago;
	protected Sucursal principal;
	protected String tipo;
	
	public CreateTicket(Pago pago, String tipo) {
		this(null, pago, tipo);
	}
	
	public CreateTicket(AdminTickets ticket, Pago pago, String tipo) {
		this.ticket= ticket;
		this.pago  = pago;
		this.tipo  = tipo;
		init();
	} // CreateTicket
	
	protected void init(){		
		Sucursal matriz= null;		
		for(Sucursal sucursal: JsfBase.getAutentifica().getSucursales()){
			if(sucursal.isMatriz())
				matriz= sucursal;					
		} // for		
		this.principal= matriz!= null ? matriz : JsfBase.getAutentifica().getEmpresa();		
	} // init

	public Sucursal getPrincipal() {
		return principal;
	}
	
	public String toHtml() throws Exception{
		StringBuilder sb= new StringBuilder();
		sb.append(toHeader());
		sb.append(toBlackBar());
		//sb.append(toDomicilio());
		sb.append(toNoTicket());
		sb.append(toTipoTransaccion());
		sb.append(toFecha());
		sb.append(toTable());			
		sb.append(toHeaderTable());
		sb.append(toFinishTable());
		sb.append(toArticulos());
		sb.append(toPagos());
		sb.append(toFinishTable());
		sb.append(toVendedor());
		sb.append(toCajero());
		sb.append(toFooter());		
		return sb.toString();
	} // toHtml
	
	protected String toHeader() throws Exception{
		//String ticket= this.principal.getTicket()!= null ? this.principal.getTicket() : "";
		StringBuilder regresar= new StringBuilder("<div id=\"ticket\" style=\"width: 90px; max-width: 80px;\">");
		regresar.append("<table style=\"width: 290px;\"><tr>");
		//regresar.append("<td><img src=\"data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+CjxzdmcKICAgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIgogICB4bWxuczpjYz0iaHR0cDovL2NyZWF0aXZlY29tbW9ucy5vcmcvbnMjIgogICB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiCiAgIHhtbG5zOnN2Zz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiAgIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIKICAgeG1sbnM6c29kaXBvZGk9Imh0dHA6Ly9zb2RpcG9kaS5zb3VyY2Vmb3JnZS5uZXQvRFREL3NvZGlwb2RpLTAuZHRkIgogICB4bWxuczppbmtzY2FwZT0iaHR0cDovL3d3dy5pbmtzY2FwZS5vcmcvbmFtZXNwYWNlcy9pbmtzY2FwZSIKICAgdmVyc2lvbj0iMS4wIgogICB3aWR0aD0iMzAyLjAwMDAwMHB0IgogICBoZWlnaHQ9IjUwNC4wMDAwMDBwdCIKICAgdmlld0JveD0iMCAwIDMwMi4wMDAwMDAgNTA0LjAwMDAwMCIKICAgcHJlc2VydmVBc3BlY3RSYXRpbz0ieE1pZFlNaWQgbWVldCIKICAgaWQ9InN2ZzIiCiAgIGlua3NjYXBlOnZlcnNpb249IjAuOTEgcjEzNzI1IgogICBzb2RpcG9kaTpkb2NuYW1lPSJib25hbnphLnN2ZyI+CiAgPGRlZnMKICAgICBpZD0iZGVmczI0IiAvPgogIDxzb2RpcG9kaTpuYW1lZHZpZXcKICAgICBwYWdlY29sb3I9IiNmZmZmZmYiCiAgICAgYm9yZGVyY29sb3I9IiM2NjY2NjYiCiAgICAgYm9yZGVyb3BhY2l0eT0iMSIKICAgICBvYmplY3R0b2xlcmFuY2U9IjEwIgogICAgIGdyaWR0b2xlcmFuY2U9IjEwIgogICAgIGd1aWRldG9sZXJhbmNlPSIxMCIKICAgICBpbmtzY2FwZTpwYWdlb3BhY2l0eT0iMCIKICAgICBpbmtzY2FwZTpwYWdlc2hhZG93PSIyIgogICAgIGlua3NjYXBlOndpbmRvdy13aWR0aD0iMTAzMCIKICAgICBpbmtzY2FwZTp3aW5kb3ctaGVpZ2h0PSI2NjYiCiAgICAgaWQ9Im5hbWVkdmlldzIyIgogICAgIHNob3dncmlkPSJmYWxzZSIKICAgICBpbmtzY2FwZTp6b29tPSIwLjM3NDYwMzE3IgogICAgIGlua3NjYXBlOmN4PSIxODguNzUiCiAgICAgaW5rc2NhcGU6Y3k9IjMxNSIKICAgICBpbmtzY2FwZTp3aW5kb3cteD0iMTQ2MyIKICAgICBpbmtzY2FwZTp3aW5kb3cteT0iMjAyIgogICAgIGlua3NjYXBlOndpbmRvdy1tYXhpbWl6ZWQ9IjAiCiAgICAgaW5rc2NhcGU6Y3VycmVudC1sYXllcj0ic3ZnMiIgLz4KICA8bWV0YWRhdGEKICAgICBpZD0ibWV0YWRhdGE0Ij4KQ3JlYXRlZCBieSBwb3RyYWNlIDEuMTUsIHdyaXR0ZW4gYnkgUGV0ZXIgU2VsaW5nZXIgMjAwMS0yMDE3CjxyZGY6UkRGPgogIDxjYzpXb3JrCiAgICAgcmRmOmFib3V0PSIiPgogICAgPGRjOmZvcm1hdD5pbWFnZS9zdmcreG1sPC9kYzpmb3JtYXQ+CiAgICA8ZGM6dHlwZQogICAgICAgcmRmOnJlc291cmNlPSJodHRwOi8vcHVybC5vcmcvZGMvZGNtaXR5cGUvU3RpbGxJbWFnZSIgLz4KICA8L2NjOldvcms+CjwvcmRmOlJERj4KPC9tZXRhZGF0YT4KICA8cGF0aAogICAgIHN0eWxlPSJmaWxsOiNmNDAwMDA7c3Ryb2tlOm5vbmU7ZmlsbC1vcGFjaXR5OjEiCiAgICAgaW5rc2NhcGU6Y29ubmVjdG9yLWN1cnZhdHVyZT0iMCIKICAgICBpZD0icGF0aDgiCiAgICAgZD0ibSAxNDYuMywxMy42IGMgLTI3LjIsNC42IC01MC42LDIyIC02My4yLDQ3LjEgLTQuMSw4LjIgLTcuOCwyMS41IC04LjcsMzAuOCBsIC0wLjcsNy41IDg4LjMsMCA4OC4zLDAgLTAuNywtNy41IEMgMjQ4LjcsODIuMiAyNDUsNjguOSAyNDAuOSw2MC43IDIyOC40LDM1LjkgMjAxLjksMTYuNCAxNzYuNSwxMy40IGwgLTUuNSwtMC43IDAsMTUuNyAwLDE1LjYgLTguNSwwIC04LjUsMCAtMC4yLC0xNS44IC0wLjMsLTE1LjggLTcuMiwxLjIgeiBtIDI3LjYsMzggYyAyLjEsMS43IDMuMSwzLjUgMy4xLDUuMyAwLDUuMyAtNC4yLDguOCAtMTQuMywxMS44IGwgLTUuMSwxLjUgMy44LDEuMyBjIDUuNiwyIDksNS42IDguMiw4LjkgLTEuMyw1LjIgLTEzLjEsMTAuNiAtMjMuMSwxMC42IC0xMCwwIC0xMy43LC02LjggLTcuNiwtMTQgbCAyLjgsLTMuNCAtMi4xLC0xLjggYyAtMi4xLC0xLjggLTIuMSwtMS44IC0wLjIsLTMuMyAxLjEsLTAuOCAzLjIsLTEuNSA0LjgsLTEuNSAxLjgsMCA0LjQsLTEuNSA3LjgsLTQuNSA1LjIsLTQuNiA5LC01LjggOSwtMi44IDAsMC45IC0wLjgsMi41IC0xLjcsMy41IC0xLjcsMS45IC0xLjYsMS45IDEuMiwxLjIgMy43LC0wLjggOS41LC00LjIgOS41LC01LjUgMCwtMC41IC0xLjEsLTEuNCAtMi40LC0xLjkgLTMuNiwtMS4zIC0xMi40LDAuNCAtMTkuOCwzLjggLTguNSw0IC0xMS43LDQuNyAtMTIuNSwyLjcgLTIuNCwtNi41IDEyLjcsLTE0LjMgMjcuOSwtMTQuNCA2LjgsLTAuMSA4LDAuMiAxMC43LDIuNSB6IiAvPgogIDxwYXRoCiAgICAgc3R5bGU9ImZpbGw6IzAwMDBjNztzdHJva2U6bm9uZTtmaWxsLW9wYWNpdHk6MSIKICAgICBpbmtzY2FwZTpjb25uZWN0b3ItY3VydmF0dXJlPSIwIgogICAgIGlkPSJwYXRoMTAiCiAgICAgZD0ibSA0MS41LDg2LjkgYyAtMy41LDIuMSAtNy45LDcgLTEwLDExLjEgLTIsMy45IC00LjcsMjAuMiAtMTAsNjAgLTIuNSwxOSAtNC43LDM1LjUgLTUsMzYuNiAtMC40LDEuOCAxLjUsMy41IDEwLjgsMTAuMiAzMywyMy42IDUxLjUsMzIuNiA3Mi41LDM1LjQgbCA5LjQsMS4zIDE4LjIsMzUuNyBjIDEwLDE5LjcgMTguNSwzNiAxOC44LDM2LjMgMC4zLDAuNCA5LjMsLTMuNyAxOS45LC05LjEgbCAxOS40LC05LjYgMTIuNSwtMjIuNCBjIDYuOSwtMTIuMyAxMi44LC0yMi4zIDEzLjEsLTIyLjQgMC44LDAgMTcuOSwyNS43IDE3LjksMjYuOSAwLDAuNiAtMy45LDkuMyAtOC42LDE5LjUgbCAtOC43LDE4LjUgLTMuMSwtMi40IGMgLTEuNywtMS4zIC01LC0yLjkgLTcuNCwtMy42IC02LC0xLjcgLTEzLjgsMC43IC0xOC4yLDUuOCBsIC0zLjIsMy42IDAuNCwxMC42IGMgMS4xLDIzLjYgMTAuMSwzMi4yIDI1LjYsMjQuNSA4LjgsLTQuNCAxOS4xLC0xNS4yIDQyLjgsLTQ1LjIgMjUuNywtMzIuNCAyNCwtMjguNiAxOS44LC00NC4yIC0zLjYsLTEzLjIgLTEwLjEsLTI4LjggLTE5LjcsLTQ3IC05LjIsLTE3LjQgLTEzLjYsLTIzLjggLTE5LjQsLTI4IC01LjMsLTMuOSAtMTAuNSwtNC45IC0yNSwtNSBsIC0xMi4zLDAgMCwtNS4zIGMgMCwtMi44IDAuOSwtMTkuNiAyLC0zNy4yIDEuMSwtMTcuNiAyLC0zMy45IDIsLTM2LjMgbCAwLC00LjIgLTM0LjUsMCBjIC0yNS4zLDAgLTM0LjUsMC4zIC0zNC41LDEuMSAwLDIuNCA1LDcyLjYgNS41LDc3LjcgbCAwLjUsNS4zIC00LjYsLTIuNyBjIC04LjMsLTQuOSAtMjIsLTQuMiAtMzMuNSwxLjYgbCAtMy42LDEuOSAtMy43LC00LjUgYyAtNS43LC02LjggLTEyLjMsLTkuNyAtMjIuMSwtOS44IC00LjQsLTAuMSAtOS45LDAuMyAtMTIuMiwwLjcgbCAtNC4zLDAuOSAwLC02LjUgYyAwLC0zLjUgMC4zLC0xMy4yIDAuNywtMjEuNSBsIDAuNiwtMTUuMiA0LjEsMCBjIDkuMywwIDE5LjIsLTcuNyAyMC4zLC0xNS44IDAuOCwtNi4xIC0xLjMsLTEwLjggLTcuOSwtMTcuNCBDIDU1LjYsODUuNiA0OC4zLDgyLjcgNDEuNSw4Ni45IFogbSAxMDkuMywyNy42IGMgMy42LDQuMSA1LjIsOS4xIDUuMSwxNiBsIC0wLjEsNSAtMC44LC0zLjkgYyAtMS42LC04LjIgLTkuNiwtMTEuMiAtMTUuMSwtNS43IC0zLjksNCAtMy45LDguMiAwLDEyLjIgMy45LDMuOSA4LjgsNCAxMi42LDAuMSBsIDIuOCwtMi43IC0wLjgsMi41IGMgLTEuMiw0LjEgLTQuMiw3LjYgLTcuNSw5IC02LjgsMi44IC0xMy42LC00LjMgLTE0LjcsLTE1LjQgLTAuNywtNi43IDEsLTEyLjggNC44LC0xNy4xIDIuNSwtMi44IDMuOCwtMy41IDYuOSwtMy41IDMuMSwwIDQuNCwwLjcgNi44LDMuNSB6IG0gMzQuMSwwIGMgNy45LDkgNi4yLDI2LjkgLTMuMiwzMiAtMy40LDEuOCAtMy43LDEuOSAtNy4xLDAuMyAtMi4yLC0xLjEgLTQuMywtMy4yIC01LjYsLTUuNyAtMi44LC01LjUgLTIuNSwtNi41IDAuOSwtMyAzLjksMy44IDguOCw0IDEyLjUsMC4zIDMuNywtMy43IDMuNSwtOC42IC0wLjMsLTEyLjUgLTUuMywtNS4zIC0xMy4zLC0yLjYgLTE1LjEsNS4xIGwgLTAuOCwzLjUgLTAuMSwtNC41IGMgLTAuMSwtNi40IDEuNSwtMTEuNSA1LjEsLTE1LjUgMi40LC0yLjggMy43LC0zLjUgNi44LC0zLjUgMy4xLDAgNC40LDAuNyA2LjksMy41IHogbSAtNS45LDQ4LjQgYyAwLDEuMiAtNi40LDYuNyAtOS44LDguNSAtNy4xLDMuNiAtMTIuMiwyLjggLTE4LC0yLjcgLTcuMSwtNi44IC03LjIsLTYuNyAxMS4zLC02LjcgOS4xLDAgMTYuNSwwLjQgMTYuNSwwLjkgeiIgLz4KICA8cGF0aAogICAgIHN0eWxlPSJmaWxsOiMwMDAwYzc7c3Ryb2tlOm5vbmU7ZmlsbC1vcGFjaXR5OjEiCiAgICAgaW5rc2NhcGU6Y29ubmVjdG9yLWN1cnZhdHVyZT0iMCIKICAgICBpZD0icGF0aDEyIgogICAgIGQ9Im0gMTYyLjYsMzA5LjIgLTE1LjgsOC4xIDAuNywxMC42IGMgMC40LDUuOCAxLDExLjEgMS40LDExLjggMC40LDAuNyA1LjUsLTIuNCAxNC41LC05LjEgMTEsLTggMTQuMSwtMTAuOCAxNC43LC0xMy4xIDEsLTQuMyAxLjgsLTE2LjUgMSwtMTYuNSAtMC4zLDAgLTcuNywzLjcgLTE2LjUsOC4yIHoiIC8+CiAgPHBhdGgKICAgICBzdHlsZT0iZmlsbDojMDAwMGM3O3N0cm9rZTpub25lO2ZpbGwtb3BhY2l0eToxIgogICAgIGlua3NjYXBlOmNvbm5lY3Rvci1jdXJ2YXR1cmU9IjAiCiAgICAgaWQ9InBhdGgxNCIKICAgICBkPSJtIDE2Mi44LDMzNS41IC0xMy43LDEwLjUgMC42LDcuMiBjIDAuMyw0IDAuOSw3LjcgMS4zLDguMiAwLjUsMC40IDYuMywtMy4xIDEyLjksLTggbCAxMiwtOC44IDAuNiwtNS41IGMgMC4zLC0zLjEgMC44LC03LjUgMSwtOS45IDAuMywtMi4zIDAuMSwtNC4yIC0wLjMsLTQuMSAtMC40LDAgLTYuOSw0LjcgLTE0LjQsMTAuNCB6IiAvPgogIDxwYXRoCiAgICAgc3R5bGU9ImZpbGw6IzAwMDBjNztzdHJva2U6bm9uZTtmaWxsLW9wYWNpdHk6MSIKICAgICBpbmtzY2FwZTpjb25uZWN0b3ItY3VydmF0dXJlPSIwIgogICAgIGlkPSJwYXRoMTYiCiAgICAgZD0ibSAxNjIuOCwzNTguOCAtMTEuNyw4LjcgMC42LDcuNSBjIDAuOSwxMiAwLjQsMTEuOCAxMiwzLjMgbCAxMC4xLC03LjUgMC43LC03LjYgYyAwLjMsLTQuMyAwLjgsLTguOSAxLC0xMC41IDAuMywtMS41IDAuMSwtMi43IC0wLjMsLTIuNyAtMC40LDAgLTYsNCAtMTIuNCw4LjggeiIgLz4KICA8cGF0aAogICAgIHN0eWxlPSJmaWxsOiMwMDAwYzc7c3Ryb2tlOm5vbmU7ZmlsbC1vcGFjaXR5OjEiCiAgICAgaW5rc2NhcGU6Y29ubmVjdG9yLWN1cnZhdHVyZT0iMCIKICAgICBpZD0icGF0aDE4IgogICAgIGQ9Im0gMTYyLjcsMzgzLjYgYyAtOC44LDYuOCAtOS44LDcuOCAtOS4zLDEwLjIgMC4zLDEuNSAwLjgsNi42IDEuMiwxMS40IDAuMyw0LjkgMC45LDguOCAxLjEsOC44IDEuNSwwIDE1LjIsLTExLjggMTUuNiwtMTMuNSAwLjcsLTIuNiAyLjEsLTI0LjUgMS42LC0yNC40IC0wLjIsMCAtNC44LDMuNCAtMTAuMiw3LjUgeiIgLz4KICA8cGF0aAogICAgIHN0eWxlPSJmaWxsOiMwMDAwYzc7c3Ryb2tlOm5vbmU7ZmlsbC1vcGFjaXR5OjEiCiAgICAgaW5rc2NhcGU6Y29ubmVjdG9yLWN1cnZhdHVyZT0iMCIKICAgICBpZD0icGF0aDIwIgogICAgIGQ9Im0gMTYyLjcsNDEzLjYgYyAtMy43LDMgLTYuNyw2LjEgLTYuNyw2LjcgMCwzLjQgNi4yLDcwLjYgNi43LDcyLjcgMC45LDMuNiAxLDMuMSA0LjMsLTM4IDEuNiwtMjAuNiAzLjIsLTM5LjYgMy41LC00Mi4zIDAuMywtMi42IDAuMiwtNC43IC0wLjIsLTQuNyAtMC41LDAgLTMuOSwyLjUgLTcuNiw1LjYgeiIgLz4KPC9zdmc+Cg==\" alt=\"Logotipo\" width=\"35px\"/></td>");
		String image= toImage(); 
		regresar.append("<td><img src=\"").append(image).append("\" alt=\"Logotipo\" width=\"70px\"/></td>");
		regresar.append("<td><p style=\"text-align: center;align-content: center;font-family: sans-serif;font-size: 14px;font-weight: bold;\">");
		regresar.append(this.principal.getTitulo());
		//regresar.append("<br/>").append(ticket);
		regresar.append("</p>");
		regresar.append(toDomicilio());
		regresar.append("</td>");
		regresar.append("</tr></table>");		
		return regresar.toString();
	} // toEncabezado;
	
	protected String toBlackBar(){
		StringBuilder regresar= new StringBuilder();
		regresar.append("<p style=\"width: 290px;text-align: center;font-family: sans-serif;font-size: 13px;font-weight: bold;background: black;color: white\">CENTRO DE SERVICIO DEWALT Y B&amp;D</p>");
		return regresar.toString();
	} // toBlackBar
	
	private String toDomicilio() throws Exception{
		StringBuilder regresar= new StringBuilder();
		regresar.append("<p style=\"text-align: center;align-content: center;font-family: sans-serif;font-size: 12px;\">").append(toFindDomicilio()).append("</p>");		
		return regresar.toString();
	} // toDomicilio
	
	protected String toFindDomicilio() throws Exception{
		Entity domicilio         = null;
		String regresar          = null;
		Map<String, Object>params= null;
		try {
			params= new HashMap<>();
			params.put("idEmpresa", ((TicketVenta)this.ticket.getOrden()).getIdEmpresa());
			domicilio= (Entity) DaoFactory.getInstance().toEntity("VistaInformacionEmpresas", "datosEmpresa", params);
			regresar= domicilio.toString("empresaDireccion").concat(" C.P. ").concat(domicilio.toString("codigoPostal")).concat("<br> COLONIA. ").concat(domicilio.toString("colonia")).concat("<br> TEL.").concat(toTelefono());
		} // try
		finally{
			Methods.clean(params);
		} // finally
		return regresar;
	} // toFindDomicilio
	
	private String toTelefono() throws Exception{
		String regresar          = "";
		Map<String, Object>params= null;
		Entity telefono          = null;
		try {
			params= new HashMap<>();
			params.put(Constantes.SQL_CONDICION, "id_empresa=" + ((TicketVenta)this.ticket.getOrden()).getIdEmpresa() + " and id_tipo_contacto=" + ETiposContactos.TELEFONO.getKey());
			telefono= (Entity) DaoFactory.getInstance().toEntity("TrManticEmpresaTipoContactoDto", "row", params);
			if(telefono!= null)
				regresar= telefono.toString("valor");
		} // try
		catch (Exception e) {			
			throw e;
		} // catch		
		finally{
			Methods.clean(params);
		} // finally
		return regresar;
	} // toTelefono
	
	private String toNoTicket(){		
		StringBuilder	regresar= new StringBuilder();
		String descripcionTicket= this.tipo.equals("COTIZACIÓN") ? ((TicketVenta)this.ticket.getOrden()).getCotizacion(): ((TicketVenta)this.ticket.getOrden()).getTicket();
		regresar.append("<p style=\"width: 290px;text-align: center;align-content: center;font-family: sans-serif;font-size: 15px;font-weight: bold\">");
		regresar.append(this.tipo.equals("COTIZACIÓN") ? "CONSECUTIVO: " : "TICKET No: ");
		regresar.append(this.principal.getClave()).append("-").append(descripcionTicket).append("<br>");		
		return regresar.toString();
	} // toNoTicket
	
	protected String toTipoTransaccion(){
		StringBuilder regresar= new StringBuilder();
		regresar.append(this.tipo).append("<br>");		
		return regresar.toString();
	} // toTipoVenta
	
	protected String toFecha(){
		StringBuilder regresar= new StringBuilder();
		regresar.append("Fecha:").append(Fecha.formatear(Fecha.FECHA_HORA_CORTA, ((TicketVenta)this.ticket.getOrden()).getCobro()));
		if(this.tipo.equals("APARTADO")){
			regresar.append("<br>");		
			regresar.append("Vencimiento:").append(Fecha.formatear(Fecha.FECHA_HORA_CORTA, ((TicketVenta)this.ticket.getOrden()).getVigencia()));
		} // if
		regresar.append("</p>");		
		return regresar.toString();
	} // toFecha
	
	protected String toTable(){
		StringBuilder regresar= new StringBuilder();
		regresar.append("<table style=\"width: 290px;border-top: 1px solid black;border-collapse: collapse;\">");		
		return regresar.toString();
	} // toTable
	
	protected String toHeaderTable(){
		StringBuilder regresar= new StringBuilder();
		regresar.append("<thead>");
		regresar.append("<tr style=\"border-top: 1px solid black;border-collapse: collapse;\">");
		regresar.append("<th style=\"font-family: sans-serif;font-size: 12px;width: 80px; max-width: 80px;border-top: 1px solid black;border-collapse: collapse;text-align: left\">CONCEPTO</th>");
		regresar.append("<th style=\"font-family: sans-serif;font-size: 12px;width: 35px;max-width: 35px;word-break: break-all;border-top: 1px solid black;border-collapse: collapse;text-align: center\">CANT</th>");
		regresar.append("<th style=\"font-family: sans-serif;font-size: 12px;width: 35px;max-width: 35px;word-break: break-all;border-top: 1px solid black;border-collapse: collapse;\">NETO</th>");
		regresar.append("<th style=\"font-family: sans-serif;font-size: 12px;width: 55px;max-width: 55px;word-break: break-all;border-top: 1px solid black;border-collapse: collapse;\">IMPORTE</th>");
		regresar.append("</tr></thead>");		
		return regresar.toString();
	} // toHeaderTable
	
	private String toArticulos(){				
		StringBuilder regresar= new StringBuilder();			
		for(Articulo articulo : this.ticket.getArticulos()){
			if(articulo.isValid()) {				
				regresar.append(toTable());
				regresar.append("<tbody>");
				regresar.append("<tr style=\"border-top: 1px solid black;border-collapse: collapse;\">");
				regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 80px; max-width: 80px;border-top: 1px solid black;border-collapse: collapse;\">").append(articulo.getNombre().length()> 35 ? articulo.getNombre().substring(0, 35) : articulo.getNombre()).append("</td>");
				regresar.append("</tr>");
				regresar.append("</tbody>");
				regresar.append(toFinishTable());
				regresar.append("<table style=\"width: 290px;\">");
				regresar.append("<tbody>");
				regresar.append("<tr>");
				regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 80px;max-width: 80px;\">").append("</td>");
				regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 35px;max-width: 35px;word-break: break-all;text-align: center\">").append(articulo.getCantidad()).append("</td>");
				regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 35px;max-width: 35px;word-break: break-all;text-align: right\">").append(Numero.formatear(Numero.NUMERO_CON_DECIMALES, articulo.getCosto())).append("</td>");
				regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 55px;max-width: 55px;word-break: break-all;padding-right: 10px;text-align: right\">").append(articulo.getImporte()).append("</td>");
				regresar.append("</tr>");
				regresar.append("</tbody>");
				regresar.append(toFinishTable());
			} // if
		} // for
		regresar.append(toTable());
		regresar.append("<tbody>");
		regresar.append("<tr style=\"height: 15px;border-top: 1px solid black;border-collapse: collapse;\"><td></td><td></td><td></td><td></td></tr>");			
		regresar.append("</tbody>");			
		return regresar.toString();
	} // toArticulos
	
	private String toPagos(){
		StringBuilder regresar= new StringBuilder();
		regresar.append("<table style=\"width: 290px;\">");
		regresar.append("<tbody>");
		regresar.append("<tr style=\"border-collapse: collapse;\">");						
		regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">SUBTOTAL:</td>");			
		regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right\">").append(this.ticket.getTotales().getSubTotalDosDecimales$()).append("</td>");			
		regresar.append("</tr>");			
		regresar.append("<tr style=\"border-collapse: collapse;\">");						
		regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">IVA:</td>");			
		regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right\">").append(this.ticket.getTotales().getIvaDosDecimales$()).append("</td>");			
		regresar.append("</tr>");			
		regresar.append("<tr style=\"border-collapse: collapse;\">");						
		regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">TOTAL:</td>");			
		regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">$").append(this.ticket.getTotales().getTotalDosDecimales$()).append("</td>");			
		regresar.append("</tr>");			
		regresar.append("<tr style=\"height: 15px;\"><td></td><td></td><td></td><td></td></tr>");	
		if(this.tipo.equals("APARTADO")){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");				
			regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">ABONO:</td>");			
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append("</td>");
			regresar.append("</tr>");
		} // if
		if(!this.tipo.equals("APARTADO") && this.pago.getAbono() > 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");				
			regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">ABONO:</td>");			
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getAbono()).append("</td>");
			regresar.append("</tr>");
		} // if
		if(this.pago.getEfectivo() > 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");				
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">EFECTIVO:</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getEfectivo()).append("</td>");
			regresar.append("</tr>");
		} // if
		if(this.pago.getDebito()> 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">DEBITO (REF ").append(this.pago.getReferenciaDebito()).append("):").append("</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getDebito()).append("</td>");
			regresar.append("</tr>");
		} // if
		if(this.pago.getCredito()> 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">CREDITO (REF ").append(this.pago.getReferenciaCredito()).append("):").append("</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getCredito()).append("</td>");
			regresar.append("</tr>");
		} // if
		if(this.pago.getTransferencia()> 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">TRANSFERENCIA (REF ").append(this.pago.getReferenciaTransferencia()).append("):").append("</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getTransferencia()).append("</td>");
			regresar.append("</tr>");
		} // if
		if(this.pago.getCheque()> 0){			
			regresar.append("<tr style=\"border-collapse: collapse;\">");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;\">CHEQUE (REF ").append(this.pago.getReferenciaCheque()).append("):").append("</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 12px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;\">").append(this.pago.getCheque()).append("</td>");
			regresar.append("</tr>");
		} // if
		regresar.append("<tr style=\"border-collapse: collapse;\">");
		regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">CAMBIO:</td>");
		regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">").append(this.pago.getCambio$()).append("</td>");
		regresar.append("</tr>");
		if(this.tipo.equals("APARTADO")){			
			regresar.append("<tr style=\"height: 15px;\"><td></td><td></td><td></td><td></td></tr>");	
			regresar.append("<tr style=\"border-collapse: collapse;\">");				
			regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 220px;max-width: 220px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">RESTANTE:</td>");
			regresar.append("<td style=\"font-family: sans-serif;font-size: 14px;width: 70px;max-width: 70px;word-break: break-all;border-collapse: collapse;text-align: right;font-weight: bold;\">$").append(Numero.formatear(Numero.NUMERO_CON_DECIMALES, this.pago.getDifEfectivo())).append("</td>");
			regresar.append("</tr>");
		} // if
		regresar.append("</tbody></table>");					
		return regresar.toString();
	} // toPagos
	
	protected String toFinishTable(){		
		return "</table>";
	} // toArticulos

	protected String toVendedor() throws Exception{
		StringBuilder regresar= new StringBuilder("<br/>");
		regresar.append("<p style=\"width: 290px;font-family: sans-serif;font-size: 13px;border-top: 1px solid black;border-collapse: collapse;\">");
		regresar.append("<br/><strong>VENDEDOR:</strong>").append(toUsuario()).append("<br/>");		
		return regresar.toString();
	} // toArticulos

	protected String toUsuario() throws Exception{
		String regresar          = null;
		Entity usuario           = null;
		Map<String, Object>params= null;
		try {
			params= new HashMap<>();
			params.put("idUsuario", ((TicketVenta)this.ticket.getOrden()).getIdUsuario());
			usuario= (Entity) DaoFactory.getInstance().toEntity("VistaUsuariosDto", "perfilUsuario", params);
			regresar= usuario.toString("nombreCompleto");
		} // try
		finally{
			Methods.clean(params);
		} // finally
		return regresar;
	} // toUsuario
	
	protected String toCajero(){
		StringBuilder regresar=  new StringBuilder();
		regresar.append("<strong>CAJERO:</strong>");
		regresar.append(JsfBase.getAutentifica().getPersona().getNombreCompleto());
		regresar.append("</p>");		
		return regresar.toString();
	} // toArticulos
	
	protected String toFooter(){
		StringBuilder regresar= new StringBuilder();
		String descripcion= this.tipo.equals("COTIZACIÓN") || this.tipo.equals("APARTADO") ? "GRACIAS POR SU PREFERENCIA" : "GRACIAS POR SU COMPRA";							
		regresar.append("<p style=\"width: 290px;text-align: center;align-content: center;font-family: sans-serif;font-size: 14px;border-top: 1px solid black;border-collapse: collapse;\">");				
		regresar.append("<br/>¡");
		regresar.append(descripcion);
		regresar.append("!</p>");
		regresar.append("<p style=\"width: 290px;text-align: center;align-content: center;font-family: sans-serif;font-size: 10px;\">");
		regresar.append("PARA CUALQUIER ACLARACION, MANTENER SU TICKET");
		regresar.append("</p>");
		if(!this.tipo.equals("COTIZACIÓN") && !this.tipo.equals("APARTADO")){			
			regresar.append("<p style=\"width: 290px;text-align: center;align-content: center;font-family: sans-serif;font-size: 10px;\">");
			regresar.append("PARA LA DESCARGA DE TUS ARCHIVOS FISCALES INGRESAR A LA SIGUIENTE PAGINA");
			regresar.append("<br/><br/>");
			regresar.append("http://www.app-jom.com");
			regresar.append("</p>");
		} // if
		//regresar.append("<svg id=\"barcode\"></svg>");
		regresar.append("</div>");		
		return regresar.toString();
	} // toFooter	
	
	private String toImage(){
		StringBuilder regresar= new StringBuilder("data:image/png;base64,");
		regresar.append("iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAgAElEQVR4nO2dC7Bkx1nff33OzNzn3r17d7Uv7Wp3LWktS0aIxcIGRbZBNjEGCidUKpXCY");
		regresar.append("DAQCpTINmASh1ScVAIJ4EcgVhWpVAg2jpM4hHJctiqyJVlCWC4JG0uyHtgYPa3Hvu/eva+5M3M69XX3mTlzus+87mv2Xv23Tt2dPn369Onv66+//vrrrx");
		regresar.append("W//XnWBFoBcunBSlNeSv/QOlNQrh7pT1UB6qAb8qMMukYcQSmGlXrr8eynaPd9KlemcpnNPSDRsNiAHxmF15fhrIaoY3v8X+AHvdQQ0nKa35H7zmY67wf+");
		regresar.append("c6AEH5fdQslL3NrYC/V/APqHgOuBSWABrR+hntwNfBo4M3ALCE0iBQ/U4FUxjCtY8XJlsRfY4aWGoPMEb1G8PV31Vp7DNmAA00AHQN8K+pfR9Rmv0RJ9Jb");
		regresar.append("rx91H8G5S6HZDrtFdUL5hQcKoBd1bhneNQb0BS+Ny8l1KElOAqJyo9iUC1oIQgolDi1oAZkvaC/tfo5FHQvwnMFI41NnkPif4giX4Mzb8Ednv5");
		regresar.append("ukGIvTOCbzfgwRUYiyBRbhhZxSUEVtlyVEYqDFjuFmaAadD/Ap08jNYfNITtD3tJ9L9F64eB3wCm+no6lav3rsDFBoxo24MLrzwxVfuVTSOra+nM70GuLc");
		regresar.append("cAahL0r6OTR9D6t6zoXwU0h0j071hG0O8DNd5TYUKXSQUXNXyhBqORbWkduSbPXSktRVIkAQkVZI7IlZdP7+PaQgwwgda/hk6+gda/B1zh5VgdjoH+iB1K");
		regresar.append("eA8w1rW0lAn+ug4P12HEEToJXKlkyD6bzRsS+WmPT9PTIaJPXOoMsAOt30+SPIHWHwKOejnWFlei9X9E68eB9wKdJYIMBTJ1vKcKVW3ZJkvoFN74npvFmv");
		regresar.append("/nCN5kGpcuU9BEbxsJMIXW/xytn0Tr312HHt8Nx9D6o2j9BJpfc9NJH0KfiQgWZChYgRiIla8LNHt6mpYyQf539g0pQxRJiN5wqTHANPDPHOH/PVpf7uXY");
		regresar.append("SGh9BJ18CJ08jta/XjinF3vA43V4vAGjofE6JXT6u0ASZAmeTSua2fSAS4UBZtD6A6bHJcl/QOuDXo5Nhb4CnfyeGxrEErezrTYyFFQU3L0iZic7FCTaF+");
		regresar.append("N5yeD18OxwkWMWb4jYGkPAbrT+TTfG/zZar06rX3fow2ZIEkaFDwC7zBuFQKMKlhP4c2cajDO91hu/A3P2IqngSYT+MKwMMG3G+CR5FK3/HbDPyzHcOOgY");
		regresar.append("9lEnEaZMB5Wh4OEafLtu1ccGnayE7eO/QYA5mnn7YJ4hNgRNAL+K1l83Y7w05KWNQ04iiEHqPSg1ZkzFYiCaM1+r2okSUPraRDy+8lf0u6drWBhAiWqkbz");
		regresar.append("MNlSQfRuv1ns5tLDTHSGT6mDxMhVuZSyrGTJxEy2YoaJvvZ2qW0IUhAkphKG/RNQQMMAL6nxgDS5L8Plpf5eXYStAcp6E/xph6hK/V3sXT9VFmlFUQyRAy");
		regresar.append("CQ0NeSUv3+Nz+XqUAJu0GqjGQP8iOrnNGFeaFd8mUFzDuP5jvlzVnE/g1SWYEiURWMl0Z+UYIe2mmgyX5FcFB2u/DWYANQH6H0Ny25YT8/2irGA2UXypCk");
		regresar.append("/U4doSHM8xgif2M4TO+70U8EU3bBQDiHInhH+PMZ68gtbUUAxD5xtwbx2eiOG6Clwdww7HCNUMZbMSIDszgJa3Up+SYL0ZYNL0eK1F1L9C+CKMu1VAGQ6+");
		regresar.append("tGQZ4TUpI0Sw4hRFoXrRgk8/hJeyxuy4sl4MsCND+I220196SI2BKSOcS+C+JXgyhleX7bUngmVtJUJe56NHY5B2hBep80LDJK01A4wZUS9zXvQx7+4woK");
		regresar.append("Gt355K/09rJW6z9dC0DmNOSxeJcP8yfLMGr9XwmlHYGcOShpXE+YX0wAFmuInskCPDzYPL8I1lc2utGKBitXrp8fq4d3eYMBY9Ro27QP0Vu8oLLCZT1Gvf");
		regresar.append("S6RuQevXDFVdjY7gGOGei/BEFV47BtdUYGcJFhOo6RwjBMoYcYT/yyo8vgRnE+u7uAYMIAucv2B6vGa4Gs+D+gKaP+Dyyud5oQ41EbklqDagUftj00jL/B");
		regresar.append("jwT0H/sPf4ZsIwQgxnanZB6bEKXD8Kx51ESBkhC3FAkV4vzzy0Ak8uwtmGHQJmoqbAGJQBYjQ/78b467y7QwX1BVAfAe70xGRD5x00PgdKrr8L+leHjhGc");
		regresar.append("4maI+kVhhGW4bgyOj8BUCRYaVhpMCLPU4SFRKJesTjGuYVfsWQ77ZYCyITymx1/j3R0qqC8CHwH1/9pq5VnYgrgT1J3A28zaBPqtoUybhjHXg0/V4MUV+E");
		regresar.append("YFrh2zw0NDn+W+BXhyGc41rGI5HdlpYkBN6JUBRqyoR+bxV3t3h4vwdwEfBXWHd6v/soR55Ho78D7Qb/GybBZUhhFOu6Hh2ao4ir6Fb1X/lEm1YgjfBd1y");
		regresar.append("COFvRfMYif7YcBNf3Q3qx0BJb10D4rfhDluuKf9u7+5mQjm3sx0xPFOF55Z+jhn1GBX1K45+HVHEAPLgr6D1Nxzhh3iRxhD+RwHpnZ8Pyrk1gSn38/Y98r");
		regresar.append("4hYoSmHSFyUkFfjU5uN17S6I6MkGeAETS3khjC345mmHv8PRnCr3WP74Y7MoxwT5e8m4mr0doygibICCkDiC7wS85x4WPmwaGF+nOIfgK4ZRMIn8cdth5S");
		regresar.append("H6nX0MIxgtnp9EtZ3U8Y4OeMB06i/3C4NXt1P0R/D9SbQH/Wu72pkPpIvUz97h+uumUg9DV01l83dDcMkOg/QvNaL/PQQN3retgbgc8Mbz0NPmPraep7r3");
		regresar.append("d3WCD0Fro7CbA4nLVsEl4CKAxZj++Kz9p6DzUjGLpHzjd1iKDuA3WpEj4Pxwjme+7z7m4uDN2LpoGbABk7lYyhbwZ1qRM+B/ke813tOsJ6zVj7wBAwgPoL");
		regresar.append("UD95iYzxq4XVEZT6SRr8hVnbr4ZNtBuFTWQA9WVLeG4G/mwousN6Q4w19UTiBvwZlehmbhx5kMNl6/8nu4Y2wSdhE7yC1VeADwP/x7u1VSFEleXapcRa66");
		regresar.append("6N4cqSLMueMY5yh2L42xqcrNt1fVn+jTZmiNhABlAPgfqwjcS1DXo7aY8XfVtbNyzj51eCXQpqCub0iPGouCKG/bF103qqBqfq1ktpZP0ZYQMYwBD+o8D/");
		regresar.append("3DaEx+nY0uOFiK+O4aqS9esTwl9MWnszZHl6XtvNokdLcDDDCKcblvri2LFOQ8M6MoCIekP4/+3d2soQJxNx3hTCS2+/MraEryu4oDPu27kNIPLcQo4RXm");
		regresar.append("zA03W77i/5ZaEnUmvaj9aBAdQDbj3+T71bWxkp4ccc4Y+VYHds0y86z6OiQKrZtLq2lwwNwggHIjhZhmcTeGHJ5hmL10wirCUDuB6vtlmPxxE+guOxJZoQ");
		regresar.append("Xoi4kPgbNztB5cqdTyBK4OgoXDsFTy/CkxfhxbVjhDVgAPWg6/H/y7u1VZEqd80eX4ajcYvw80l/YrrTNi95j/j6VTQcH4ejY/DUIvz1RXhpySqLo4Mzwi");
		regresar.append("oYQP2lI/z/2HZafUr4qypwVLxsnaifH3Aun2++0HZvYaw5DSVRKifh6AQ8vQDfvAgvL9mXDsAIAzCA+pp1tuRTfm23KJqET6xGfqXr8eJebXpojvBr1STZ");
		regresar.append("gBG4zSyGERp2c+k1wgjj8MyiZQSRCOUIKlHPdeiDAdTXHeE/6d3aqkgJb6ZzEVxVhiMxTLsev5BzMR6Y8EWck48FQIsRxLC00rAEf/UUHJmA5xbhr86fY6");
		regresar.append("E+Y5igB/TAAOoR51f/Cb+CWxQqM4+XnnZlBY7FsCtqGXYUa2e39UK/N2+0S4C2PQwZiXCxboeGG6ZhrvaveODsY4zEEnDju7135dCBTYTw0c+CugH09iF+");
		regresar.append("4rRvadijZbhpBL6nbHfpzrupHgUhVwa9UuRjBbWFgclFAsmHhpH6nlmB795V5djkfczWbkAZ+j3ifWMGAUOjjPHRT1vC83Hvia2IdH4uW6xEtMtU7vtH4E");
		regresar.append("TZhn6fd3b8tNFD8X5XcxWFf20jcj5iaC7ci3bDQika48Q0ZttbTX/c0tHQ82s5yplSolYodPVVUD8F6nXbbpyXJdkV1+PfID2+AtPKjvFLSYYKa9ntM1e+");
		regresar.append("NzcZLY0fSKv3e6Hl08sxwlwdjk7a4eBCLX3sk5auhr5fdU8auosO8ACoPwL1X73G2eqQxllytnZZqDmQUe76MeCsFvmziHp5oacvpgyiYbYG1+yEF5bhOw");
		regresar.append("uwo5wy0afc7E22973bMYC6yVc+tgnxZYu8jO3XV+zOmkHn8atFPtxLmqAy4j0hFx0kxwFZRVKGgqkyfM8MnFxypuW2qBLS2U2H76AEbmGYni9RDTSciOwU");
		regresar.append("by7T6wNSel2vLBGzcQETWkOAinKRQHMniGTDx8lvEf+Xj8GJGbhQLWTq7XZqmGV5EfvjskTrjnVbyiiCm4H8LDCo+BVk6lRnMSFfNQXPL1pJIIphLv/2kg");
		regresar.append("Cp2JeTPE6U7JLt8hCFKGwGiAxM+UJxgtukR8BoJGFkhOgndltJ0vD3xm8vBqg5z5wbKvavW1TzGnWzrhRtQ4Fqr2MT+SNjUp7JDAuiM4iRaN8YvG4PXKx5");
		regresar.append("TbJ9hoB0nixRuqXnz3eJrTMs0DmvYU/7d3/9zt1CVaKR7rQzgjPLMNoaCraHBFDOrHtEgisp63nDEPX8jlfuNJG2j1LF6dlLhoKREnzfPjsU1Fvcsj0YoJ");
		regresar.append("bAVARHSi0DS+EZfptxdWIAMsfDhQhO616RMiNDwXwdZkbhxGWwVG/eWv8hQBo/cREutXOGTOpQKUO0AfwnDSxD3/VlG8VwQRW206YhpP2Df5JYmpZ+VxAF");
		regresar.append("H6ddbMFrZuD5BThrFaC1Z4C64+jI9bSdI3aeLRpoSvB4DM5dgJWqOzVLVt3KUPKnKatCaua9wtn0l4rbZ1ORJ3zI1NtcDSxQAppnCxd8iDCOLB9PlOH1++");
		regresar.append("FzT5nk1TOAdrtd5M3isToZ2ZdNl6EiThOTENWtJBgZs4wwegUs1CARH/gzsLwE8wuwMA9RbD8mji1TFHJ6D5B3iueOrOGbaVCHBtpUpJXKVS7xwtgViH9a");
		regresar.append("o7nupA3KglfNWgllKFgVAzScMiFE3zUKu0asaB9NrNlRenNctnmXG24YaNjerqqwY8rawHfutJLhwkVYWYYLczAr0kGOVZmzjJCelFUue9UohFlIUdaJQ9");
		regresar.append("bK06jbm2Xs6Ql5Y09+VTBjrfLsQlnzcQck6azAnovdHwMo17DGNSqGw1M25Oj0uCWsjDESebPmaihpkfYY26Amvu6NVuUnxmB6CmZm7EcsLML8RViqwpnT");
		regresar.append("tixhDikrZQr5m/rJ62zDKNvbDzgP3eVOPWcIUEi8rIKYKn95iZD2+Kj5SFcYJug3WLQ09PkFW4nrZmCqAuMjUK9bsS7n5NcG3OkqHy7l1FvaKaMjsHPKph");
		regresar.append("2+3DLM+fNQq8PJU9BotIYMkTaR2z1TcX+V20RRdQGh695bhxdZs6/O6QieCjCgIq17jRUsxBECn7oIrzsC33UFnH8RFqpWqZB7pXXQ5oXAi4utOgiRDx2y");
		regresar.append("jXNgv/175iy89Lwdekbc8JBWJXEbMp5yJwrLMDC0TNBNsyfc+3t9tgM6U0560LL0vEW4/hA3/fQb4IaDdnWp3kXZWEukM4WlJVhebon/w4dherKdjbNOFB");
		regresar.append("Xnwv2skwBxQRtuOkLafc4kTHpkfH4VsGB1sKerkwQQ4p+Zt39/5vvhhkP2hORTizZtA+nvwSiSyjKDDD2BtjPQJrqxZYLnFBx239UoemCT0Ek90bm/+fQU");
		regresar.append("ni2hNxRLACH+kd3wCzdx0w2HTJJETzYNeKlBPHuNJNA0D4kYJh7IW/7y5wi2CYgCi1/bTGFQCWCUprol/g2HDfG/D/iy97ZLECIJ5Nje5zUcUnY4GLLwWB");
		regresar.append("6yvV/lZjspmpZByP2nJ7QYQMo5N29NtDLWv96e6vbQxn3u+iMdDp7HMoF8fS3coTYFITHuSYAUecLn0ntEiwFml+CyKfjx7+KW1xxguEJiryEqbmr4gobL");
		regresar.append("Izc78FpxcxCqRlPXUhmFMIMQ0/SBEit1eHkO3nQc/uHrII62LvFTlN0awYvaHk8du1nCZkmCvFUvm952gniggp5F0MvRESUzdXjnG+DmrX1sr4eSUwyFCS");
		regresar.append("QIw2YzAQGNP7/9LG8UImMISgYTASV+/gf40SO7TSD8bQcjCYCXNOzHrmFsyhQx330zxp3CvYFRKw8ZU3efDFxiuxI/RckxwcvAPnem4KbNDgLUyyuB3nAx");
		regresar.append("GOFTFBuCthNSJjgJ7NUbH0HZc/JQ7elkdICuY31/nFBsCNpuKDnF8KQYizYuUKNB0/CT2ySaNQy1bf4I+AAGp4rd8YoEyKLkjEWnNFyWLn97udYeRZ3WrG");
		regresar.append("sE7AIG+YcGWxR6hQHyKLmz+08r2LNRAfV7IFrRWoCnPxZxUxivMEAIMkUUC+EZYI9qMcF6TRHTKVzTEUi10hvZDSLad2zx/Am90jviFQYoQuzMxGc1zKRO");
		regresar.append("rgV51xr5OX2b2C9wCRuQO1fPAGZtPpJKT6BUpS1dRQlxfMFw7bAtwfaC2OkE54BdGzAchMy8wfE/gE73OmBwBjAuWOoQSfKPWFy6kUS/iUZjsnm/3khQuo");
		regresar.append("FuPIpO7iEu3UkUf2VVXr6bgdjNDs4pywRqA3SCdG7WthycWwvITx03QgcQr4DvKEP4Eaorv0ut+ovUkzGSRmszorlk7Krbyi3rm1HRzZRqH0TrOyhV3kdc");
		regresar.append("+RaqvLpBVd4j/oBRtDrX8V4QO8XwvIKdrL1OkBfnvQw1+dnAwGsBfUBmRt/R9QnOLX+JxvKNdo1aWyLEGZNCOm9VznBhduc0YOXi26lU3kjpwtuIl79sGG");
		regresar.append("fQRpR3yn6/6op1D1tvpEwwK17QrI8/QcjapzPOIZ0UkYGHgK+96CUWQSJFUq/fzoEDNxqTab0G1WXrnSubO9Ie6VVKtcKdVGuTzD5zBzumrkXzwsDGlnIM");
		regresar.append("s4vWT3C0sv5SgMyq4QUJLbOGdoJmJ8ivBeQ8fZqzgUzeVTuEnJr3EgtRjt9AFL2LyUnnpeIqtqsGs2fhzBnr6y89sqgewiDLjSlG6r9FXP5Z4+bdL5T78K");
		regresar.append("rbQbSRekU6O5C4vVkmWM1w0GnPQpFP4Jo5hLz5mJdYiEdPvZ/5ZTyiCRH2HoRSGV5yEkVlukeTSzMrWBcW38nbTnyAqbGXil5XCJE+3zoJjzwMOyeKcq0f");
		regresar.append("UvEvfWcydddaC4UgUEabBFgPh5BTPR8cOk49eYvT/ttfKi7ijRpM77E7emQDR7mSUWpyioqUUW/EvHjhrczXP9F7D9ZW1xBP4KdOQ7kUbLMNQbrZZF7Z3V");
		regresar.append("HKbUUbpD5FO4N0xhTsxQhqNUlbct9K4BNnvMQC3MhYPGXn/LkMitaulR07YXYuE/EyVHnxx4vhked+BBV/ouuGxvTDYtcLzi1YJtjhtqRtFlImkIAT42rw");
		regresar.append("4cCz7mUZQrkOFGXFgc80AzZDifGKl1iAyw2hsppv3golu4Qqo1Cp2K3fcTrJcJVNcpUus9tsGO2FayMnOWarVgrITqDNJH62XmkAaYk/kDRa8RA6wcyeMh");
		regresar.append("tfCVgACfgC6OyLQ3YAr4SOKPUhMnY05yUqJ2+ahJWPKtkPS2hFwPTWu91yZ6I1IyNdlDgnXqUM6fkr67QVbTWIXFyEpcjpJKoHSSDrDcuJtaEE7pHxCKKT");
		regresar.append("aM87hBRmDKLUufHbMNp8U+GSZOZ+ttzQSpZIi3I8weTOzj3ZMFXZbk+rzrXbG4YJ0hRi62DC7pbWNV+0ZyHS7NyZcZZqATtGru2y07+u5OrwzgD6kQCJ15");
		regresar.append("OLFiLylS58id5N0qigkxXvVvOtEmJmDBbrVvUedDfsesPMBGo28MWkTE0Xu9Q1rqD1no7NQ2AfoJe5iCa9oT81Oq/MFe1SIWO7DqGp3LCfam0fSfJ8WzV0");
		regresar.append("ZgtaI7KbPOMBvm7DoVrGIrMZ1anwSS5GgomAkuyjwf62cwC89kqPm8sa10K+43QoozOi9gWHLpfnfqTaidJctMjMWYOXW9duNKZJGgetPpPJaGztdRfNag");
		regresar.append("iDOnVD5NpBwrHUay2TbnopM5U9aL6fgAuYznV0M0QUSNlCmvRyiQToZ/XIi1eT/qdgGlMgrZprBSsy/nGckcqDJh5AmkcMSksrNgbBiFeLSwPSrhKjSGZZ");
		regresar.append("Y+PWVpK2hxC0unScmlvHSFWg/IipnYJtZlOqfZbgKdZ5mvSGPiVAHgEJkHJjEngu9Hth5d0msojEram58/Tna24Y8F54aSGVBPNLds2ktiyEt38XFt4dbI");
		regresar.append("98ewuBpUM0O2o3J9D+JMCAQ0AgMHHz5QGEykqfW15+M3V9gtFRO31c0eH58KWMxJ1IIt8n3ynfK99N1N4eSc6goy2JKI/45uZuNOmxQ0c+V3S4PIKnlclU");
		regresar.append("rmhve+iSsVDWFeYu/hcqJVsdGQr6dGoYepgwO24GI98p3ysxj0KBILPElWek94tZPfWo8ghfQJMe0Z8E8MKTBLguayTylJuAsmPW9RdPMHvhT6yTxxbdqh");
		regresar.append("C5oFXyncuLJ5rxEEPKX3rVnWV1ZNQyQ0gCFNKkh6v/UbYL17VVUPn5vU0Nrkwh/Olz7+T87F0odaNRjIbV4NMv5DtsOLsbOTt7l/lOonbi5JFd9ZOldxVn");
		regresar.append("pOpabwzp/SHlaaltlaAVs6+Rmnrbb3t2hGZBboVxYeEWqtWHGB//DOX4vxNFL6DUU0TRKZR3stJwwkoxhVJ7iaJXsbRyObXGT7G4+A47JYwDGnyuPQwxE6");
		regresar.append("v9T+x0toACrT/flAF+6oR+DEFJi/BFzzjVXSouxI/TD+pmxnSGEmkcEXUX5t6Bit5hopUsLZ1EzZ2hUZ9tLS4NKaSnL8/DyeVpdGMPmn1mqqsTWsNbxpRe");
		regresar.append("2B7KxkXcNeOmkPX2e/kmz6Zr5Z8K0QGlPkaBaotDC2ouRJdl3vFxmD2fiW2XY8+i5w0iN32SKaF5pQS532eYI+gAmu8RmcURXVTfwOJUEIH0vFTL1sMYeG");
		regresar.append("p2JVRlnWRTwufldEF7pAyz+zJ3WFSgp+e/t2UK7pcBAh8Zxoq3Bu1V3H3U2ISteN4E6r2ryKyZ3naTFLI9p6glOid3ztSlHk1TXjZLoB6pD0QUB4gbqljo");
		regresar.append("PVhH1z2X2XjKtVo4q7cG0PyGnj186HM18Hzzpc15em4p0miudRiftEywuGCnMU10YaCOKGgsLz1UZgGBe4q93638tayHrCiuWK3/wKHWzClkDvccQtLf0W");
		regresar.append("nvtR1QMostveGkXZcPfUgGogCOVGBqF1ycsxHDU/2t2RmKFJlMuflnvLw69zu9n52J5HqfZ2rtpR7ugUHr0faOgrypjK8728CRYxg/CYmYjsrlVe1/2xRJ");
		regresar.append("rVHJy/SBUjPGbnc8Q622gJYtYLm8iWMi5QZIcdfavRvOnrGmT/EQ0oHGyrWLR5BQnm7p+UDLbfDGy/A7PO08kKfXegTzB5hDer4Q/1VXw9S0O0yDgFEsPx");
		regresar.append("SlbS+KYvk0ycy3vdd1QOnlN84U323Hyf0PnH2GC7XrKOelRtJeGdHkRYztPwhPf9s6f6iA9uv1Gr/IJlQuvdOzuMYNpWWf6VoPvfp6hJCthznKpQpjY3Dk");
		regresar.append("VTC9y+oAheHfC15gjo5Tf4M2wfF7Rmn/V2d7z11NvkpJXed/bJ4hIhtxVLRY8RKWiN5i0cqHmfV6TeD/3Ro89GxRmlfvLvnS/4cYs596hCCOLqIvycxmz1");
		regresar.append("44dIVto+WVcPkFdG+910TC/oqNc9M7SpwtdsbxEKt7Qb0rKMK8SmsrCeTDxO/t1MtWq+906kdbry2wHRQ1sK8NF6OojPxLUhEcyh9K61aPxIXXNyt8Jdg5");
		regresar.append("A3v3wfS0WzCqBb47xwHe8JSpiOJLds9l7ygZY0uv0NxNw+z6LLVXwq+LQcMdKSOiTWYGJ190ZwBk9g22Wrqvinv5++kxXjCmXLq33artYS8liJxuZo+8kU");
		regresar.append("M2xmBi0h6VM7HDSgAJ1tm0lxRU3FMCvQ+VWdr9/ZqDS+i+OOZ5VPIFtH67dycIZUWdVGrvfjczmLVHwYiGm/YGlZEaKYoYvTk7KMjQZidvo0IruY3gebt6");
		regresar.append("qAVz0blU+phqEbdtdqRaawAyDZZrzC3qyMFZJbcQJMpyI+hn3yWN9j3kxuqqPg1c9LJ1QYlGXzqD4CPA24MVTueiUZ4o2hJcTLm798Guva2zboKaf0Fa9n");
		regresar.append("ichA5cEupMoTJzz+rAfNvLm/3GfObs9DEjXdLdVA13aFatwLSbVTijXJqnd2fqIecyRcl/GuRAjNIAkTvuJuE+Yt7UJESzcnmGUJnKu4Mj0yPmPOJlUZAW");
		regresar.append("ZfKHzBeetMj9LkrrVEZXFGRMGVs6eOQI3o2YbRXI5ymoqGn/+JNo/fggR+KUzCFL/ULzy9T14+IoHqxcNsK194EE1Oz+K9Af+qbq2qJrewSI7hE8D/dMo3");
		regresar.append("aBxmXvRQ22UKbMwU+D4OsLt/Ls4scYizo0cFF6t3ud7heN8QTSBynrEqqHSJnx0R+nNPW5wsc64QcVJd+o0x1yishD5dLt6Og4mtusYpZxVPAY2kvIfERe");
		regresar.append("E08fKWrUHsq9VOuhM/c61UM7KVIuvZ+Zqc+ZfRMD9uOB5IY5RcQcCqneYyKBJcl7W5pxD0zclqfooYKpWNZO4M2VuxTplbEG9QjOOroUWXi/Q1nZepiNJf");
		regresar.append("FvEMcfMjpcbfBDvFbnYWF3uL7PrBMkye9gvPgDCl7W9JnfWNosq9N0LptOqOU6l1VoRAmU0XM9OpRRVFZhPQLPF9Wjnsyye/xW0J8yR8KvEmvgeCdMoH+f");
		regresar.append("qPS9EH/W1LeRUWJSoqdXau/wfNsyH97V+TGX17tyz+hcPXSo3H7rEcjrXb3WI1Am7lPrbuex/fQ/QasbqJQ+ZaaWa7A7fm08L21FHkdFP0Gk3kqp9GmieM");
		regresar.append("6cMZzo1seHxj6du/LpnfKHpuIdEXpAtYYTHXjvhtXDMULWrV460kh8jpHyf4Po7wA/g+LZ4KniA2KNnezE/p/cxczoXdSSw+xSP8Sc/gHq+iaQoJLsbLOR");
		regresar.append("y38vNqyCM5aGlEYyVKIAAAEYSURBVFOtaBiKlqVNu/KzO2S8RsiJzVAwxbzkbloWVav84LQ8dd9Oy45a9WurRs71S7tj9YWq2llFqy59MvaZTUWyOvcciv");
		regresar.append("tZanyFQ5UvUi6d4puLMLb209j18bK09Xz+tn2Vj//BYu3j1vgTXYbSV6KSg2h2oxmjpkd57XhMI1a8rBQNoXxNEa0oE7E5URGRUiR122W0kisy/skqUdYO");
		regresar.append("Ib8jhdYK5caIJLJ5bNAeW4ZudnXnpKdcfuEAQ7EEZY4610RaI/8MxSTNmCETu4si1sZDWUsenRCjSYz3q0YZCmtieSbWJgBGpCUIhIZRUds1JRKOSp6G5m");
		regresar.append("+qDSpqGc0S6DPo5AVGdvwtSe0sdfEPSBm0SAytEsD/B7c1L01hOFWMAAAAAElFTkSuQmCC");
		return regresar.toString();
	} // toImage
}