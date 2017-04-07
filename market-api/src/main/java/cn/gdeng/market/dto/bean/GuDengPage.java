package cn.gdeng.market.dto.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页实体类 
 * @author wjguo
 *
 * datetime:2016年9月30日 下午6:20:01
 */
public class GuDengPage<T> implements Serializable {

	private static final long serialVersionUID = 3594414284603482161L;

	/**当前页*/
    private int currentPage = 1;
    
    /**每页显示记录数*/
    private int pageSize = 10;
    
    /**总记录数*/
    private int total = 0;
    
    /**记录集合*/
    private List<T> recordList;
    
    /**总页数*/
    private int pageCount;
    
    /**偏移数*/
    private int offset;
    
    /**上一页*/
    private int prePage;
    
    /**下一页*/
    private int nextPage;
    
    /**是否有上一页*/
    private boolean hasPrePage;
    
    /**是否有下一页*/
    private boolean hasNextPage;
    
    /**查询条件*/
    private Map<String, Object> paraMap;
    
    /**
     * 默认的空参构造数
     *
     */
    public GuDengPage() {
        
    }
    
    /**
     * 构造函数,计算总页数、是否有上一页、下一页等.
     * @param currentPage    当前页
     * @param pageSize        每页显示记录数
     * @param recordCount   总记录数
     * @param recordList    记录集合
     */
    public GuDengPage(int currentPage,int pageSize) {
        this.currentPage = currentPage;
        if(currentPage < 1) {
            this.currentPage = 1;
        }
        
        this.pageSize = pageSize;
        
        //偏移量
        this.offset = (this.currentPage - 1)*pageSize;
    }
    
    /**
     * 根据总记录数初始化总页数，下一页， 上一页等参数
     * @param recordCount
     */
    public void initiatePage(int recordCount){
    	//上一页等于当前页减一
        this.prePage = this.currentPage - 1;
        if(this.prePage < 1) {
            this.hasPrePage = false;//没有上一页
            this.prePage = 1;
        }else {
            this.hasPrePage = true;//有上一页
        }
        
        //计算总页数
        this.pageCount = (int)Math.ceil(recordCount / (double)pageSize);
        if(this.currentPage > this.pageCount) {
            this.currentPage = this.pageCount;
        }
        
        //下一页等于当前页加一
        this.nextPage = this.currentPage + 1;
        if(this.nextPage > this.pageCount) {
            this.hasNextPage = false;//没有下一页
            this.nextPage = this.pageCount;
        }else {
            this.hasNextPage = true;//有下一页
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
	public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.setParaMap(null);
        //初始化页面
        this.initiatePage(total);
    }

    public List<?> getRecordList() {
        return recordList;
    }

    public GuDengPage<T> setRecordList(List<T> recordList) {
        this.recordList = recordList;
        this.setParaMap(null);
        return this;
    }

	public Map<String, Object> getParaMap() {
		if(paraMap == null){
			if(this.getPageSize() > 0){
				paraMap = new HashMap<>();
				paraMap.put("startRow", this.getOffset());
				paraMap.put("endRow", this.getPageSize());
			}
		}
		return paraMap;
	}

	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}

}
