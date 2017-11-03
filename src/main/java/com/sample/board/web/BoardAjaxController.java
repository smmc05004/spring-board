package com.sample.board.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sample.board.service.BoardService;
import com.sample.board.vo.Board;
import com.sample.board.vo.Comment;
import com.sample.board.web.form.CommentForm;
import com.sample.user.vo.User;

@Controller
@RequestMapping("/board")
public class BoardAjaxController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping("/addComment.do")
	@ResponseBody
	public Comment addComment(CommentForm commentForm, HttpSession session) {
		
		Comment comment = new Comment();
		comment.setContents(commentForm.getContents());
		
		Board board = new Board();
		board.setNo(commentForm.getBoardNo());
		comment.setBoard(board);
		
		User user = (User) session.getAttribute("LOGIN_USER");
		comment.setWriter(user);
		
		comment = boardService.addNewComment(comment);
		
		return comment;
	}
	@RequestMapping("/getComments.do")
	@ResponseBody
	public List<Comment> getComments(int boardNo) {
		List<Comment> comments = boardService.getComments(boardNo);
		return comments;
	}
	
	@RequestMapping("/delComment.do")
	@ResponseBody
	public Comment deleteComment(int commentNo) {
		Comment comment = boardService.removeComment(commentNo);
		return comment;
	}
}
