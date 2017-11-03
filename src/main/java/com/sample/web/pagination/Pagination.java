package com.sample.web.pagination;

public class Pagination {

	// 한 화면에 5개씩 뿌리기
	private Integer rows = 5;
	private Integer pages = 5;
	// default값으로 1이 설정됨
	private Integer pageNo = 1;
	private String opt;
	private String keyword;
	
	private int totalRows;
	
	public int getBeginIndex() {
		return (pageNo - 1) * rows + 1;
	}
	public int getEndIndex() {
		return pageNo * rows;
	}
	
	// 페이징 처리에 필요한 연산 메소드
	public int getTotalPages() {
		return (int) Math.ceil(totalRows / (double) rows); 
	}
	public int getTotalBlocks() {
		return (int) Math.ceil(getTotalPages() / (double) pages);
	}
	public int getCurrentBlock() {
		return (int) Math.ceil(pageNo / (double) pages);
	}
	public int getBeginPage() {
		return (getCurrentBlock() - 1) * pages + 1;
	}
	public int getEndPage() {
		if (getCurrentBlock() == getTotalBlocks()) {
			return getTotalPages();
		}
		return getCurrentBlock() * pages;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	
	@Override
	public String toString() {
		return "Criteria [pageNo=" + pageNo + ", opt=" + opt + ", keyword=" + keyword + "]";
	}
	
}
