package br.com.dfdevforge.sisfin.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.dfdevforge.sisfin.behavior.TransferObject;

public abstract class BtpAbstract implements TransferObject, Serializable
{
	private static final long serialVersionUID = 2133296168715084996L;

	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	private Map<String, String> map = new HashMap<String, String>();
	public Map<String, String> getMap() {return map;}
	public void setMap(Map<String, String> map) {this.map = map;}
}