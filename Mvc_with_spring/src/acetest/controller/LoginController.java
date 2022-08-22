

package acetest.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import acetest.dao.UserDAO;
import acetest.dto.UserRequestDTO;
import acetest.dto.UserResponseDTO;
import acetest.model.LoginBean;
import acetest.model.UserBean;

@Controller
public class LoginController {
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping(value = "/",method=RequestMethod.GET)
	public ModelAndView showLogin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("LGN001");
		modelAndView.addObject("login", new LoginBean());
		return modelAndView;
	}
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public String login(@ModelAttribute("login")  LoginBean login,ModelMap model) {
			if (login == null || login.getLoginName().equals("") || login.getLoginPassword().equals("")) {
				return "/LGN001";
			} else {
				ArrayList<UserResponseDTO> list=userDao.selectUser(login);
				if(list.size() > 0) {
					return "/MNU001";
				}else {
					return "/LGN001";
				}
			}	
	}
	
	@RequestMapping(value="/showuser" , method=RequestMethod.GET)
	public String displayView(ModelMap model) {
		ArrayList<UserResponseDTO> list=userDao.selectAll();
		model.addAttribute("list",list);
		return "USR003";
	}
	

	@RequestMapping(value="setupadduser" , method=RequestMethod.GET)
	public ModelAndView setupadduser() {
		ModelAndView modelAndView = new ModelAndView("USR001", "bean", new UserBean()); 
		return modelAndView;
	}
	@ModelAttribute("roleList")
	public Map<Integer, String> getCountryList() { 
		Map<Integer, String> roleList = new HashMap<Integer, String>(); 
		roleList.put(1, "Admin"); 
		roleList.put(2, "User"); 
		return roleList; 
		} 
	
	@RequestMapping(value="/adduser" , method=RequestMethod.POST)
	public String addbook(@ModelAttribute("bean") @Validated UserBean bean,
			BindingResult bs) {
		if (bs.hasErrors()) {
			return "USR001";
		}
		UserRequestDTO dto=new UserRequestDTO();
		dto.setUserEmail(bean.getUserEmail());
		dto.setUserPassword(bean.getUserPassword());
		dto.setUserId(bean.getUserId());
		dto.setUserName(bean.getUserName());
		dto.setUserRole(bean.getUserRole());
		userDao.insertData(dto);
		return "redirect:/showuser";
	}
	
	@RequestMapping(value="/deleteuser/{userId}",method=RequestMethod.GET)
	public String deleteBook(@PathVariable String userId) {
		UserRequestDTO dto=new UserRequestDTO();
		dto.setUserId(userId);
		userDao.deleteData(dto);
		return "redirect:/showuser";
	}
	

	@RequestMapping(value="/setupupdateuser/{userId}", method=RequestMethod.GET)
	public ModelAndView setupUpdatebook(@PathVariable String userId) {
		UserRequestDTO dto=new UserRequestDTO();
		dto.setUserId(userId);
		ModelAndView modelAndView = new ModelAndView("USR002", "bean", userDao.selectOne(dto)); 
		return modelAndView;
	}
	
	@RequestMapping(value="/updateuser" , method=RequestMethod.POST)
	public String updatebook(@ModelAttribute("bean") @Validated UserBean bean,
			BindingResult bs) {
		if (bs.hasErrors()) {
			return "updateBook";
		}
		UserRequestDTO dto=new UserRequestDTO();
		dto.setUserEmail(bean.getUserEmail());
		dto.setUserPassword(bean.getUserPassword());
		dto.setUserId(bean.getUserId());
		dto.setUserName(bean.getUserName());
		dto.setUserRole(bean.getUserRole());
		userDao.updateData(dto);
		return "redirect:/showuser";
	}
	
	@RequestMapping(value="/searchUser" , method=RequestMethod.GET)
	public String searchUser(@RequestParam("id") String uid, @RequestParam("name") String uname, ModelMap model) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setUserId(uid);
		dto.setUserName(uname);
		//List<UserResponseDTO> userResList = new ArrayList<UserResponseDTO>();
		ArrayList<UserBean> userBeanList = new ArrayList<UserBean>();
		if (userDao.searchData(dto).isEmpty()) {
			return "redirect:/showuser";
		}else {
			UserBean userBean = new UserBean();
			List<UserResponseDTO> list=userDao.searchData(dto);
			for (UserResponseDTO res:list) {
				userBean.setUserEmail(res.getUserEmail());
				userBean.setUserPassword(res.getUserPassword());
				userBean.setUserRole(res.getUserRole());
				userBean.setUserId(res.getUserId());
				userBean.setUserName(res.getUserName());
				userBeanList.add(userBean);
			}
		}
		model.addAttribute("list", userBeanList);
		return "USR003";
	}
	
}
