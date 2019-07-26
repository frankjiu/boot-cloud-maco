package com.utils;
/**
 * json 实体封装
 * @author mqy
 *
 */
public class JsonResult {
        /**
         * 状态
         */
	    private String status = null;
        /**
         * 结果集
         */
	    private Object result = null;
	    /**
	     * 成功与否
	     */
	    private boolean success=false;


		/**
	     * 消息
	     */
	    private String msg=null;
	    /**
	     * 成功与否 0是成功， 200失败
	     */
	    private int code=0;
	    /**
	     * 页码总数
	     */
	    private  int count=0;
        /**
         * 结果集
         */
	    private Object data=null;
	    
	    
	    
		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}
		
	    public JsonResult status(String status) {
	        this.status = status;
	        return this;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public Object getResult() {
	        return result;
	    }

	    public void setResult(Object result) {
	        this.result = result;
	    }
	    
	    public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	    
	
}
