package br.com.dfdevforge.sisfin.constants;

import org.apache.struts.action.ActionMessages;

public final class Constants 
{
	// Others
	public static boolean isProducao = Boolean.TRUE;
	
	public static Integer count = 1;

	// Commands
	@Deprecated public static final String actShowInclForm = "actShowInclForm"; // Show module insertion form
	@Deprecated public static final String actShowMainPage = "actShowMainPage"; // Show module main page
	@Deprecated public static final String actShowEditForm = "actShowEditForm"; // Show module edition form
	@Deprecated public static final String actShowImpoForm = "actShowImpoForm"; // Show module edition form
	@Deprecated public static final String actExecInsert = "actExecInsert"; // Execute a insert process
	@Deprecated public static final String actExecUpdate = "actExecUpdate"; // Execute a update process
	@Deprecated public static final String actExecDelete = "actExecDelete"; // Execute a delete process
	@Deprecated public static final String actExecSearch = "actExecSearch"; // Execute a select process
	@Deprecated public static final String actExecLogoff = "actExecLogoff"; // Execute a select process

	// Paths
	public static String FULL_PATH;
	public static final String ROOT = "/sisfin"; // path to root directory
	public static final String JSP_PATH = ROOT + "/pages"; // path to .jsp files
	public static final String IMG_PATH = ROOT + "/images"; // path to images files
	public static final String JVS_PATH = ROOT + "/javascript"; // path to java script files
	public static final String CSS_PATH = ROOT + "/styles"; // path to CSS Styles files
	public static final String CVR_PATH = ROOT + "/covers"; // path to covers albuns files

	// Database configuration
	public static String getDatabaseUser() {return "sisfin";}
	public static String getDatabasePassword() {return "sisfin";}
	public static String getDatabaseDriver() {return "com.mysql.jdbc.Driver";}

	// Errors
	public static ActionMessages errors = new ActionMessages();

	// Tests
	public static String errorLog = "";
}