package com.sample.board.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sample.board.service.BoardService;
import com.sample.board.vo.Board;
import com.sample.board.web.form.BoardForm;
import com.sample.user.vo.User;
import com.sample.web.pagination.Pagination;
import com.sample.web.view.ExcelView;
import com.sample.web.view.FileDownloadView;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Value("${file.uplad.directory}")
	private String uploadDirectory;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	FileDownloadView fileDownloadView;

	@Autowired
	ExcelView excelView;
	
	@RequestMapping(value= {"/home.do", "/list.do"})
	public String list(Pagination pagination, Model model) {
		// System.out.println(pagination);
		
		int totalRows = boardService.getTotalRows(pagination);
		//System.out.println("행의 수: " + totalRows);
		pagination.setTotalRows(totalRows);
		//System.out.println("pagination: " + pagination);
		
		model.addAttribute("navi", pagination);
		
		List<Board> boards = boardService.getBoards(pagination);
		model.addAttribute("boards", boards);
		
		return "/board/home";
	}
	@RequestMapping("/form.do")
	public  String form() {
		return "/board/form";
	}
	@RequestMapping("/add.do")
	// 첨부파일 업로드 - multipart 요청 -> 스프링이 자체적으로 처리하지 않음
	// servlet-context.xml에서 설정 필요
	public String add(BoardForm boardForm, HttpSession session) throws IOException{
		// object type으로 얻어져 형변환 필요
		User user = (User) session.getAttribute("LOGIN_USER");
		if (user == null) {
			return "redirect:/user/login.do?error=deny";
		}
		Board board = new Board();
		board.setWriter(user);
		BeanUtils.copyProperties(boardForm, board);
		
		// 첨부파일을 특정 폴더에 저장하는 처리
		MultipartFile attachedfile = boardForm.getAttachedfile();
		if (!attachedfile.isEmpty()) {
			// attachedfile의 getOriginalFilename가 파일 이름 반환하는 메소드 // getName은 필드이름 반환 
			String filename = System.currentTimeMillis() + attachedfile.getOriginalFilename();
			board.setFilename(filename);
			
			FileCopyUtils.copy(attachedfile.getBytes(), new File(uploadDirectory, filename));
		}
		boardService.addNewBoard(board);
		
		return "redirect:home.do";
	}
	@RequestMapping("/detail.do")
	public String detail(@RequestParam("no") int boardNo, Model model) {
		
		Board board = boardService.getBoardDetail(boardNo);
		model.addAttribute("board", board);
		//model.addAttribute("likesPerson", boardService.getAllLikes(boardNo));
		//model.addAttribute("comments", boardService.getComments(boardNo));
		
		return "/board/detail";
	}
	@RequestMapping("/filedownload.do")
	public ModelAndView filedownload(@RequestParam("no") int boardNo) {
		
		ModelAndView mav = new ModelAndView();
		
		Board board = boardService.getBoardDetail(boardNo);
		
		mav.addObject("directory", uploadDirectory);
		mav.addObject("filename", board.getFilename());
		mav.setView(fileDownloadView);
		
		return mav;
	}
	
	@RequestMapping("/exceldownload.do")
	public ModelAndView exceldownload() {
		
		ModelAndView mav = new ModelAndView();
		
		Pagination pagination = new Pagination();
		mav.addObject("boards", boardService.getBoards(pagination));
		mav.setView(excelView);
		
		return mav;
	}
}
