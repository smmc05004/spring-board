package com.sample.web.view;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.sample.board.vo.Board;

public class ExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Board> boards = (List<Board>) model.get("boards");
		
		Sheet sheet = workbook.createSheet("게시글");
		
		Row headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("번호");
		headRow.createCell(1).setCellValue("제목");
		headRow.createCell(2).setCellValue("작성자");
		headRow.createCell(3).setCellValue("날짜");
		headRow.createCell(4).setCellValue("추천수");
		
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-m-d"));
		
		int rowCount = 1;
		for(Board board : boards) {
			Row dataRow = sheet.createRow(rowCount++);
			dataRow.createCell(0, CellType.NUMERIC).setCellValue(board.getNo());
			dataRow.createCell(1).setCellValue(board.getTitle());
			dataRow.createCell(2).setCellValue(board.getWriter().getFullname());
			Cell cell = dataRow.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(board.getCreatedate());
			dataRow.createCell(4, CellType.NUMERIC).setCellValue(board.getLikes());
		}
		
		response.setHeader("Content-Disposition", "filename='boards.xlsx'");
		workbook.write(response.getOutputStream());
		
		
	}

}
