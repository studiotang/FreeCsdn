package com.free.blog.model.entity;

/**
 * 主页侧滑-ListItem
 * 
 * @author tangqi
 * @since 2015年8月13日下午11:46:39
 */
@SuppressWarnings("unused")
public class DrawerInfo extends BaseEntity {

	private static final long serialVersionUID = -887156398580286575L;

	private String name;
	private int resId;
	private String reserve;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
