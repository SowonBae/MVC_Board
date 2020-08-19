package board.vo;

public class PageInfo {
private int page;
private int maxPage;
private int startpage;
private int endPage;
private int listCount;



public PageInfo(int page, int maxPage, int startpage, int endPage, int listCount) {
	super();
	this.page = page;
	this.maxPage = maxPage;
	this.startpage = startpage;
	this.endPage = endPage;
	this.listCount = listCount;
}
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getMaxPage() {
	return maxPage;
}
public void setMaxPage(int maxPage) {
	this.maxPage = maxPage;
}
public int getStartpage() {
	return startpage;
}
public void setStartpage(int startpage) {
	this.startpage = startpage;
}
public int getEndPage() {
	return endPage;
}
public void setEndPage(int endPage) {
	this.endPage = endPage;
}
public int getListCount() {
	return listCount;
}
public void setListCount(int listCount) {
	this.listCount = listCount;
}


}
