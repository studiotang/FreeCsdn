package com.free.csdn.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

@SuppressLint("NewApi")
public class FileUtils {

	private static final double KB = 1024.0;
	private static final double MB = KB * KB;
	private static final double GB = KB * KB * KB;

	/** 获取文件后缀 */
	public static String getFileExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase(Locale.getDefault());
			}
		}
		return null;
	}

	/**
	 * 获取url文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlFileName(String url) {
		int slashIndex = url.lastIndexOf('/');
		int dotIndex = url.lastIndexOf('.');
		String filenameWithoutExtension;
		if (dotIndex == -1) {
			filenameWithoutExtension = url.substring(slashIndex + 1);
		} else {
			filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
		}
		return filenameWithoutExtension;
	}

	/**
	 * 获取url后缀
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlExtension(String url) {
		if (!TextUtils.isEmpty(url)) {
			int i = url.lastIndexOf('.');
			if (i > 0 && i < url.length() - 1) {
				return url.substring(i + 1).toLowerCase(Locale.getDefault());
			}
		}
		return "";
	}

	/**
	 * 获取文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 格式化大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatSize(long size) {
		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + " KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + " MB";
		else
			fileSize = String.format("%.1f", size / GB) + " GB";

		return fileSize;
	}

	/** 如果不存在就创建 */
	public static boolean createIfNoExists(String path) {
		File file = new File(path);
		boolean mk = false;
		if (!file.exists()) {
			mk = file.mkdirs();
		}
		return mk;
	}

	/**
	 * 获取SD卡路径
	 * 
	 * @param c
	 * @return
	 */
	public static String getSdCardPath(Context c) {
		// ToastUtils.showToast(paths.toString());
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}

		return "";
	}

	/**
	 * SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean getSdAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取文件mime类型
	 */
	public static String getFileMimeType(File file) {
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(file));
		if (type == null)
			return "*/*";
		return type;
	}

	/**
	 * 获取下载存储地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getDownloadSavePath(Context context) {
		return context.getExternalFilesDir(null).getAbsolutePath();
	}

	/**
	 * 获取Sd卡图片资源
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getSdcardImage(String path) {
		File mFile = new File(path);
		// 若该文件存在
		if (mFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			return bitmap;
		}

		return null;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @param size
	 * @return
	 */
	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 计算目录大小
	 * 
	 * @param f
	 * @return 大小
	 * @throws Exception
	 */
	public static long getFileSize(File f) {
		if (f == null || !f.exists()) {
			return 0;
		}
		long size = 0;
		File flist[] = f.listFiles();
		for (File file : flist) {
			if (file.isDirectory()) {
				size = size + getFileSize(file);
			} else {
				size = size + file.length();
			}
		}
		return size;
	}

	public static String getFileContent(Context context, String file) {
		String content = "";
		try {
			// 把数据从文件中读入内存
			InputStream is = context.getResources().getAssets().open(file);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int i = is.read(buffer, 0, buffer.length);
			while (i > 0) {
				bs.write(buffer, 0, i);
				i = is.read(buffer, 0, buffer.length);
			}
			content = new String(bs.toByteArray(), Charset.forName("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return content;
	}

	/**
	 * 获取缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getExternalCacheDir(Context context) {
		String path = null;
		if (getSdAvailable()) {
			File file = context.getExternalCacheDir();
			if (file != null) {
				path = file.getAbsolutePath();
			} else {
				path = context.getCacheDir().getAbsolutePath();
			}
		} else {
			path = context.getCacheDir().getAbsolutePath();
		}
		return path;
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static boolean delete(File file) {
		if (file != null && file.isFile()) {
			file.delete();
			return false;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return true;
			}
			for (File f : childFile) {
				delete(f);
			}
			file.delete();
		}
		return true;
	}

	public static String getFileName(String str) {
		// 去除url中的符号作为文件名返回
		str = str.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
		System.out.println("filename = " + str);
		return str + ".png";
	}

	/**
	 * 保存文件到SD卡中
	 * 
	 * @param filename
	 *            文件名
	 * @param inputStream
	 *            输入流
	 */
	public static void writeSDCard(String filePath, String filename, InputStream inputStream) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + filename);
			byte[] buffer = new byte[512];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);// 写入缓冲区
			}
			fileOutputStream.flush();// 写入文件
			fileOutputStream.close();// 关闭文件输出流
			inputStream.close();
			System.out.println("save success");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("save fail");
		}
	}

	public static boolean writeSDCard(String filePath, String fileName, Bitmap bmp) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			InputStream is = bitmap2InputStream(bmp);

			FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + getFileName(fileName));
			byte[] buffer = new byte[512];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Bitmap转换为byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Bitmap转换成InputStream
	 * 
	 * @param bm
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}
}
