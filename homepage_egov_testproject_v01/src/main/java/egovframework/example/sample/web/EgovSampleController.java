package egovframework.example.sample.web;

import egovframework.example.sample.service.EgovFileMngUtil;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.LoginVO;
import egovframework.example.sample.service.SampleVO;
import groovyjarjarasm.asm.commons.Method;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class EgovSampleController {

	Logger logger=LoggerFactory.getLogger(EgovSampleController.class);
	
	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	//로그인없이 게시판 먼저 보여 줄때 
//	@GetMapping("/")
//	public String search(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
//		return this.list(sampleVO, model);
//	}
	
//  로그인 폼 먼저보여줄때
	@GetMapping("/")
	public String login(@ModelAttribute LoginVO loginVO, Model model) {
		logger.info("로그인화면");
		model.addAttribute("loginVO", loginVO);
		return "index";
	}
	
	@RequestMapping(value = "/sample/list",method = {RequestMethod.GET,RequestMethod.POST})
	@PostMapping("/sample/list")
	public String list(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
		sampleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		sampleVO.setPageSize(propertiesService.getInt("pageSize"));

		// pagination setting
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List
		model.addAttribute("resultList", sampleService.selectSampleList(sampleVO));
		// Count
		paginationInfo.setTotalRecordCount(sampleService.selectSampleListTotCnt(sampleVO));
		// Pagination
		model.addAttribute("paginationInfo", paginationInfo);

		return "board/egovSampleList";
	}

	@PostMapping("/sample/detail")
	public String detail(@ModelAttribute SampleVO sampleVO, @RequestParam String id, Model model) throws Exception {
		sampleVO.setId(id);
		SampleVO detail = this.sampleService.selectSample(sampleVO);
		model.addAttribute("sampleVO", detail);
		return "board/egovSampleRegister";
	}

	@GetMapping("/sample/add")
	public String form(@ModelAttribute SampleVO sampleVO) {
		return "board/egovSampleRegister";
	}

	@PostMapping("/sample/add")
	public String add(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult
			         ,final MultipartHttpServletRequest multiRequest) throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		System.out.println("file:"+files.get("file_1").getName());
		if (!files.isEmpty()) {
			fileUtil.parseFileInf(files, "BBS_", 0, "", "");
//			atchFileId = fileMngService.insertFileInfs(result);
		}
		this.sampleService.insertSample(sampleVO);
		return "redirect:/sample/list";
	}

	@PostMapping("/sample/update")
	public String update(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.updateSample(sampleVO);
		return "redirect:/sample/list";
	}

	@PostMapping("/sample/delete")
	public String delete(@ModelAttribute SampleVO sampleVO) throws Exception {
		this.sampleService.deleteSample(sampleVO);
		return "redirect:/sample/list";
	}

}
