package acetest.controller;

import java.util.ArrayList;



import java.util.List;

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

import acetest.dao.CourseDAO;
import acetest.dao.StudentDAO;
import acetest.dto.CourseResponseDTO;
import acetest.dto.StudentRequestDTO;
import acetest.dto.StudentResponseDTO;
import acetest.model.CourseBean;
import acetest.model.StudentBean;

@Controller
public class StudentController {
	
	@Autowired
	StudentDAO studentDao;
	
	@Autowired
	private CourseDAO courseDao;

	@RequestMapping(value = "/setupSearchStudent", method = RequestMethod.GET)
	public String setupSearchStudent(ModelMap model) {
		ArrayList<CourseResponseDTO> couList=courseDao.selectAll();
		List<StudentResponseDTO> stuList=studentDao.selectAll();
		model.addAttribute("couList",couList);
		model.addAttribute("stuList",stuList);
		return "STU003";
	}

	@RequestMapping(value = "/setupRegisterStudent", method = RequestMethod.GET)
	public ModelAndView setupRegisterStudent(ModelMap model) {
		ArrayList<CourseResponseDTO> courseListRes = courseDao.selectAll();
		ArrayList<CourseBean> courseListBean = new ArrayList<CourseBean>();
		for (CourseResponseDTO courseRes : courseListRes) {
			CourseBean courseBean = new CourseBean();
			courseBean.setId(courseRes.getCid());
			courseBean.setName(courseRes.getName());
			courseListBean.add(courseBean);
		}
		StudentBean stuBean = new StudentBean();
		ArrayList<StudentResponseDTO> stuList = studentDao.selectAll();
		//StudentBean stuBean = new StudentBean();
		if (stuList == null) {
			stuList = new ArrayList<>();
		}
		if (stuList.size() == 0) {
			stuBean.setId("STU001");
		} else {
			//int tempId = Integer.parseInt(stuList.get(stuList.size() - 1).getSid().substring(3)) + 1;
			//String stuId = String.format("STU%03d", tempId);
			//stuBean.setId(stuId);
			stuBean.setAttend(courseListBean);

		}
		return new ModelAndView("STU001", "stuBean", stuBean);
	}

	@RequestMapping(value = "/registerStudent", method = RequestMethod.POST)
	public String registerStudent(@ModelAttribute("stuBean") @Validated StudentBean stuBean, BindingResult bs,
			ModelMap model) {
		if (bs.hasErrors()) {
			return "STU001";
		}
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setSid(stuBean.getId());
		dto.setName(stuBean.getName());
		dto.setDob(stuBean.getDob());
		dto.setGender(stuBean.getGender());
		dto.setPhone(stuBean.getPhone());
		dto.setEducation(stuBean.getEducation());

		int i = studentDao.insertStudent(dto);
		ArrayList<CourseResponseDTO> courseListRes = courseDao.selectAll();
		ArrayList<CourseBean> courseListBean = new ArrayList<CourseBean>();
		for (CourseResponseDTO courseRes : courseListRes) {
			CourseBean courseBean = new CourseBean();
			courseBean.setId(courseRes.getCid());
			courseBean.setName(courseRes.getName());
			courseListBean.add(courseBean);
		}
		if (i > 0) {
			model.addAttribute("courseList", courseListBean);
			model.addAttribute("msg", "Register Successful");
			studentDao.insertStudent_Course(stuBean.getId(), stuBean.getStuCourse());
			ArrayList<StudentResponseDTO> stuList = studentDao.selectAll();
			if (stuList == null) {
				stuList = new ArrayList<>();
			}
			if (stuList.size() == 0) {
				stuBean.setId("STU001");
			} else {
				int tempId = Integer.parseInt(stuList.get(stuList.size() - 1).getSid().substring(3)) + 1;
				String stuId = String.format("STU%03d", tempId);
				stuBean.setId(stuId);
			}
		} else {
			model.addAttribute("courseList", courseListBean);
			model.addAttribute("msg", "Insert Fail!!");
		}
		
		return "STU001";
	}

	@RequestMapping(value = "/seeMore/{id}", method = RequestMethod.GET)
	public ModelAndView seeMore(@PathVariable String id, ModelMap model) {
		StudentResponseDTO studentInfo = studentDao.selectOne(id);
		ArrayList<String> stuCourseIdList = studentDao.selectCourseIdList(id);
		ArrayList<CourseResponseDTO> courseListRes = courseDao.selectAll();
		ArrayList<CourseBean> courseListBean = new ArrayList<CourseBean>();
		for (CourseResponseDTO courseRes : courseListRes) {
			CourseBean courseBean = new CourseBean();
			courseBean.setId(courseRes.getCid());
			courseBean.setName(courseRes.getName());
			courseListBean.add(courseBean);
		}
		StudentBean stuBean = new StudentBean();
		stuBean.setId(id);
		stuBean.setName(studentInfo.getName());
		stuBean.setDob(studentInfo.getDob());
		stuBean.setGender(studentInfo.getGender());
		stuBean.setPhone(studentInfo.getPhone());
		stuBean.setEducation(studentInfo.getEducation());
		stuBean.setAttend(courseListBean);
		stuBean.setStuCourse(stuCourseIdList);
		return new ModelAndView("STU002", "stuBean", stuBean);
	}

	@RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
	public String updateStudent(@ModelAttribute("stuBean") @Validated StudentBean stuBean, BindingResult bs,
			ModelMap model) {
		if (bs.hasErrors()) {
			return "STU002";
		}
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setSid(stuBean.getId());
		dto.setName(stuBean.getName());
		dto.setDob(stuBean.getDob());
		dto.setGender(stuBean.getGender());
		dto.setPhone(stuBean.getPhone());
		dto.setEducation(stuBean.getEducation());
		dto.setCourse(stuBean.getStuCourse());
		int i = studentDao.updateStudent(dto);
		if (i > 0) {
			model.addAttribute("msg", " Update Successful!!!");
			studentDao.deleteStudent_Course(stuBean.getId());
			studentDao.insertStudent_Course(stuBean.getId(), stuBean.getStuCourse());
		} else {
			model.addAttribute("msg", "Update Fail!!");
		}
		CourseDAO courseDao = new CourseDAO();
		ArrayList<CourseResponseDTO> courseResList = courseDao.selectAll();
		ArrayList<CourseBean> courseBeanList = new ArrayList<CourseBean>();
		for (CourseResponseDTO course : courseResList) {
			CourseBean courseBean = new CourseBean();
			courseBean.setId(course.getCid());
			courseBean.setName(course.getName());
			courseBeanList.add(courseBean);
		}
		
		stuBean.setAttend(courseBeanList);
		return "redirect:/setupSearchStudent";
	}

	@RequestMapping(value = "/deleteStudent/{id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable String id, ModelMap model) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setSid(id);
		int i = studentDao.deleteStudent(id);
		if (i > 0) {
			model.addAttribute("msg", "Delete Successful");
		} else {
			model.addAttribute("msg", "Delete Fail");
		}
		return "redirect:/setupSearchStudent";
	}

	@RequestMapping(value = "/searchStudent", method = RequestMethod.GET)
	public String  searchStudent(@RequestParam("sid") String sid, @RequestParam("sname") String sname,
			 ModelMap model) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setSid(sid);
		dto.setName(sname);
		//List<UserResponseDTO> userResList = new ArrayList<UserResponseDTO>();
		ArrayList<StudentBean> stuBeanList = new ArrayList<StudentBean>();
		if (studentDao.searchData(dto).isEmpty()) {
			return "redirect:/setupSearchStudent";
		}else {
			StudentBean stuBean = new StudentBean();
			List<StudentResponseDTO> list=studentDao.searchData(dto);
			for (StudentResponseDTO res:list) {
				stuBean.setId(res.getSid());
				stuBean.setName(res.getName());
				//stuBean.setUserRole(res.getUserRole());
				//stuBean.setUserId(res.getUserId());
				//stuBean.setUserName(res.getUserName());
				stuBeanList.add(stuBean);
			}
		}
		model.addAttribute("list", stuBeanList);
		return "STU003";
	}

}
