package br.com.dfdevforge.sisfin.dwr;

import java.util.List;
import java.util.Map;

import br.com.cagece.core.bean.AbstractBean;

public class DwrReturn
{
	private List<? extends AbstractBean> btpResultList;
	public List<? extends AbstractBean> getBtpResultList() {return btpResultList;}
	public void setBtpResultList(List<? extends AbstractBean> btpResultList) {this.btpResultList = btpResultList;}

	private Map<String, List<? extends AbstractBean>> mapResult;
	public Map<String, List<? extends AbstractBean>> getMapResult() {return mapResult;}
	public void setMapResult(Map<String, List<? extends AbstractBean>> mapResult) {this.mapResult = mapResult;}

	private List<String> msgContainer;
	public List<String> getMsgContainer() {return msgContainer;}
	public void setMsgContainer(List<String> msgContainer) {this.msgContainer = msgContainer;}
}