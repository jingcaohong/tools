package com.netlink.tools.client;

import java.io.InputStream;

/**
 * sftp 客户端
 *
 * @author fubencheng.
 * @version 0.0.1 2018-03-24 13:01 fubencheng.
 */
public interface SftpClient {

	/**
	 * 将本地绝对全路径的文件上传到指定的目录或者指定名称的文件
	 *
	 * @param src 源文件全路径
	 * @param dir 目标目录
	 * @param fileName 目标文件名
	 * @param mode 上传模式
	 */
	void uploadFile(String src, String dir, String fileName, int mode);

	/**
	 * 将输入流写到目标文件中，目标文件不能是目录
	 *
	 * @param inStream inStream
	 * @param dir dir
	 * @param fileName fileName
	 * @param mode mode
	 */
	void uploadFile(InputStream inStream, String dir, String fileName, int mode);

	/**
	 * 下载文件到本地目录或者文件
	 *
	 * @param remotePath remotePath
	 * @param fileName fileName
	 * @param dest dest
	 * @param mode mode
	 */
	void downloadFile(String remotePath, String fileName, String dest, int mode);

	/**
	 * 下载文件，返回输入流
	 *
	 * @param remotePath remotePath
	 * @param fileName fileName
	 * @return InputStream
	 */
	InputStream downloadFile(String remotePath, String fileName, int mode);

	/**
	 * 重命名文件
	 *
	 * @param remotePath remotePath
	 * @param originalName originalName
	 * @param newName newName
	 */
	void renameFile(String remotePath, String originalName, String newName);

}
