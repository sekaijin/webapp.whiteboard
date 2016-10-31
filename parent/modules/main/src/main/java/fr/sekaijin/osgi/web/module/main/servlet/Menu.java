package fr.sekaijin.osgi.web.module.main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import fr.sekaijin.osgi.web.main.api.IMenu;
import fr.sekaijin.osgi.web.main.api.MenuItem;
import fr.sekaijin.osgi.web.main.registrar.MenuItemRegistrar;

public class Menu extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log;

	public Menu() {
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONArray menu = new JSONArray();
		for (String key : MenuItemRegistrar.getMenus().keySet()) {
			IMenu module = MenuItemRegistrar.getMenus().get(key);
			JSONObject item = new JSONObject();
			JSONArray submenu = new JSONArray();
			menu.add(item);
			item.put("value", module.getModuleName());
			item.put("icon", module.getModuleIcon());
			item.put("submenu", submenu);
			for (MenuItem menuItem : module.getMenuItems()) {
				JSONObject subItem = new JSONObject();
				submenu.add(subItem);
				subItem.put("icon", menuItem.getIcon());
				subItem.put("value", menuItem.getLabel());
				subItem.put("href", key+"/"+menuItem.getPath());				
			}
		}
		 
		resp.getWriter().println(menu.toJSONString());
		log.info("Main - Menu - Servlet");
	}
}