package com.xyz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.domain.Pubboard;
import com.xyz.domain.User;
import com.xyz.service.PubboardService;
import com.xyz.service.UserService;
import com.xyz.service.UserTempService;
import com.xyz.service.UserVercodeService;
import com.xyz.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class LoginModuleController {

	@Autowired
	@Qualifier("pubboardService")
	private PubboardService pubboardService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("userVercodeService")
	private UserVercodeService userVercodeService;

	@Autowired
	@Qualifier("userTempService")
	private UserTempService userTempService;

	/**
	 * 获取登录界面的公示栏内容
	 * 
	 * @author 叶灬黎
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCurrentPub")
	public void getCurrentPub(HttpServletResponse response) throws Exception {

		Pubboard pubboard = pubboardService.getCurrentPubboard();

		JSONObject json = new JSONObject();
		json.put("pubboard", pubboard);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 登录
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginByPass")
	public void loginByPass(@RequestBody String harvest, HttpSession session, HttpServletResponse response)
			throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String loginname = (String) js.get("loginname");
		String password = (String) js.get("password");

		User user = userService.loginByPass(loginname, password);

		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("msg", "success");
			json.put("id", user.getId());
			session.setAttribute("user", user);
		} else {
			json.put("msg", "请核实登录名或密码");
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 按不同模块发送验证码
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendVerCode")
	public void sendVerCode(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String email = (String) js.get("email");
		String module = (String) js.get("module");

		JSONObject json = new JSONObject();

		if ("register".equals(module)) {
			if (!userService.verEmail(email)) {
				String code = Utils.getVerCode();
				String[] address = { email };
				if (Utils.sendMail("smtp.qq.com", "981378964@qq.com", "小叶子", "emzbcblhyghmbfga", address, "洛林博客注册验证码",
						"您的验证码为  " + code)) {
					userVercodeService.addTheCode(email, code);
					json.put("msg", "success");
				} else {
					json.put("msg", "请确认邮箱是否处于有效状态");
				}
			} else {
				json.put("msg", "该邮箱已经注册");
			}
		} else if ("getBack".equals(module)) {
			if (userService.verEmail(email)) {
				String code = Utils.getVerCode();
				String[] address = { email };
				if (Utils.sendMail("smtp.qq.com", "981378964@qq.com", "小叶子", "emzbcblhyghmbfga", address, "洛林博客找回密码验证码",
						"您的验证码为  " + code)) {
					userVercodeService.addTheCode(email, code);
					json.put("msg", "success");
				} else {
					json.put("msg", "请确认邮箱是否处于有效状态");
				}
			} else {
				json.put("msg", "该邮箱未注册");
			}
		} else {
			System.out.println("预留");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 注册的第一步验证，通过则进入第二阶段
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/TempRegister")
	public void TempRegister(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String phone = (String) js.get("phone");
		String email = (String) js.get("email");
		String code = (String) js.get("code");

		JSONObject json = new JSONObject();

		if (!userService.verPhone(phone)) {
			if (userVercodeService.getTheCode(email, code)) {
				boolean flag = userTempService.addTheUser(phone, email);
				json.put("msg", "success");
			} else {
				json.put("msg", "验证码输入错误");
			}
		} else {
			json.put("msg", "手机号已经注册");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 注册拦截器的请求，防止用户随意访问第二步和第三步的页面，通过拦截器结合临时表的数据判断进行拦截
	 * 
	 * @author 叶灬黎
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerInterceptor")
	public String registerInterceptor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String sid = request.getParameter("id");
		Long id = null;
		if (sid != null) {
			id = Long.parseLong(sid);
		}
		if (phone != null && email != null) {
			if (userTempService.getTheUser(phone, email)) {
				return "login/userRegister";
			} else {
				return "error";
			}
		} else if (id != null) {
			if (userService.verUser(id)) {
				return "login/nickRegister";
			} else {
				return "error";
			}

		}
		return "error";
	}

	/**
	 * 正式注册
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	public void register(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String phone = (String) js.get("phone");
		String email = (String) js.get("email");
		String username = (String) js.get("username");
		String password = (String) js.get("password");
		Long id = Utils.createComplexId();

		JSONObject json = new JSONObject();
		if (userService.register(id, phone, email, username, password)) {
			userVercodeService.cutTheCode(email);
			userTempService.cutTheUser(phone, email);
			json.put("msg", "success");
			json.put("id", id + "");
		} else {
			json.put("msg", "注册失败，用户名已经存在");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 注册完第一次修改昵称
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/firstChangeNick")
	public void firstChangeNick(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		Long id = Long.parseLong((String) js.get("id"));
		String nickname = (String) js.get("nickname");

		JSONObject json = new JSONObject();
		if (userService.firstChangeNick(id, nickname)) {
			json.put("msg", "success");
		} else {
			json.put("msg", "修改失败，昵称已经存在");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 找回密码先验证邮箱
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/verBackEmail")
	public void verBackEmail(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String email = (String) js.get("email");
		String code = (String) js.get("code");

		JSONObject json = new JSONObject();
		if (userVercodeService.getTheCode(email, code)) {
			json.put("msg", "success");
		} else {
			json.put("msg", "验证码输入错误");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 找回密码
	 * 
	 * @author 叶灬黎
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */	
	@RequestMapping(value = "/getBackPass")
	public void getBack(@RequestBody String harvest, HttpServletResponse response) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String email = (String) js.get("email");
		String password = (String) js.get("password");

		JSONObject json = new JSONObject();
		if (userService.getBack(email, password)) {
			userVercodeService.cutTheCode(email);
			json.put("msg", "success");
		} else {
			json.put("msg", "修改失败");
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

}
