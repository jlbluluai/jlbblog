package com.xyz.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.xyz.common.CommonElement;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 文件压缩工具类
 * 
 * @author xiaoyezi
 *
 */
public final class CompressUtils {

	private static Logger logger = Logger.getLogger(CompressUtils.class);

	/**
	 * 图片按指定要求压缩
	 * 
	 * @param fromPic
	 *            要压缩图片文件
	 * @param toPicName
	 *            压缩后图片的名称
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param style
	 *            种类，1按指定宽高，不按比例
	 * @return
	 * @throws Exception
	 */
	public static File compressPic(File fromPic, String toPicName, int width, int height, int style) throws Exception {
		logger.info(CommonElement.separator + "图片按指定要求压缩开始" + CommonElement.separator);
		logger.info("对图片" + fromPic.getName() + "按方式" + style + "压缩，宽高为(" + width + "," + height + ")");

		File toPic = new File(toPicName);

		// 检查
		if (!fromPic.exists()) {
			logger.error("请确保传入图片");
			throw new Exception("请确保传入图片");
		}
		if ("".equals(toPicName)) {
			logger.error("请确认输入目标图片名称");
			throw new Exception("请确认输入目标图片名称");
		}
		if (style == 1) {
			if (width <= 0 || height <= 0) {
				logger.error("请输入正确宽高");
				throw new Exception("请输入正确宽高");
			}
		}

		if (style == 1) {// 按指定宽高，不按比例
			Thumbnails.of(fromPic).size(width, height).keepAspectRatio(false).toFile(toPic);
		} else {
			logger.error("请输入准确的压缩方式");
			throw new Exception("请输入准确的压缩方式");
		}

		logger.info("压缩结果：图片名为" + toPic.getName() + "，宽高(" + width + "," + height + ")");
		logger.info(CommonElement.separator + "图片按指定要求压缩结束" + CommonElement.separator);

		return toPic;
	}

}
