package tools.base.model;

public class PageLimit {
	private int page = 1;// 页码
	private int limit = 10;// 条数
	private int row = 0;// 数据库限制行

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		row = (page - 1) * 10;
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getRow() {
		return row;
	}

}
