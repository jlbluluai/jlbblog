package com.xyz.dto;

/*门户统计信息*/
public class PortalStatistic {

	/* 用户数量 */
	private Long userCount;

	/* 博主数量 */
	private Long bloggerCount;

	/* 博客数量 */
	private Long blogCount;

	/* 评论数量 */
	private Long commentCount;

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public Long getBloggerCount() {
		return bloggerCount;
	}

	public void setBloggerCount(Long bloggerCount) {
		this.bloggerCount = bloggerCount;
	}

	public Long getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Long blogCount) {
		this.blogCount = blogCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

}
